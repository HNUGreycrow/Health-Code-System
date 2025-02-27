package org.software.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.util.JWTUtil;
import org.software.code.common.util.RedisUtil;
import org.software.code.common.util.WeChatUtil;
import org.software.code.dto.*;
import org.software.code.entity.*;
import org.software.code.kafka.KafkaConsumer;
import org.software.code.kafka.KafkaProducer;
import org.software.code.mapper.*;
import org.software.code.service.UserService;
import org.software.code.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl 类实现了 UserService 接口，负责处理与用户相关的具体业务逻辑。
 * 该类整合了多个数据访问层的 Mapper 来操作数据库，使用布隆过滤器优化用户登录判断，
 * 并利用 Kafka 进行消息的生产和消费，同时结合 JWT 进行用户认证和授权。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class UserServiceImpl implements UserService {

    // 日志记录器，用于记录程序运行中的关键信息和错误信息
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    // 注入 AreaCode 表的 Mapper，用于对 AreaCode 实体进行数据库操作
    @Resource
    private AreaCodeMapper areaCodeMapper;
    // 注入 UserInfo 表的 Mapper，用于对 UserInfo 实体进行数据库操作
    @Resource
    private UserInfoMapper userInfoMapper;
    // 注入 UidMapping 表的 Mapper，用于对 UidMapping 实体进行数据库操作
    @Resource
    private UidMappingMapper userMappingMapper;
    // 注入 HealthCodeManager 表的 Mapper，用于对 HealthCodeManager 实体进行数据库操作
    @Resource
    private HealthCodeManagerMapper healthCodeManagerMapper;
    // 注入 NucleicAcidTestPersonnel 表的 Mapper，用于对 NucleicAcidTestPersonnel 实体进行数据库操作
    @Resource
    private NucleicAcidTestPersonnelMapper nucleicAcidTestPersonnelMapper;
    // 注入 Redis 工具类，用于与 Redis 缓存进行交互
    @Resource
    private RedisUtil redisUtil;
    // 注入 Kafka 生产者，用于向 Kafka 主题发送消息
    @Resource
    private KafkaProducer kafkaProducer;
    // 注入 Kafka 消费者，用于从 Kafka 主题接收消息
    @Resource
    private KafkaConsumer kafkaConsumer;

    // 用于对用户密码进行加密和解密的编码器
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // 布隆过滤器，用于快速判断一个元素是否可能存在于集合中，用于优化用户登录时的判断
    private BloomFilter<CharSequence> bloomFilter;

    // 布隆过滤器数据持久化的文件名
    private static final String BLOOM_FILTER_FILE = "bloomfilter.data";

    /**
     * 初始化方法，在 Bean 初始化完成后自动调用。
     * 尝试从文件中加载布隆过滤器，如果文件不存在则创建一个新的布隆过滤器。
     */
    @PostConstruct
    public void init() {
        bloomFilter = getInitBloomFilter();
        logger.info("Bloom filter initialized.");
    }

    /**
     * 销毁方法，在 Bean 销毁前自动调用。
     * 将布隆过滤器的数据保存到文件中，以便下次启动时恢复。
     */
    @PreDestroy
    public void shutdown() {
        saveBloomFilter();
        logger.info("Bloom filter saved.");
    }

    /**
     * 获取初始化后的布隆过滤器。
     * 优先从文件中加载布隆过滤器，如果文件不存在则创建一个新的布隆过滤器。
     *
     * @return 初始化后的布隆过滤器
     */
    private BloomFilter<CharSequence> getInitBloomFilter() {
        try {
            File file = new File(BLOOM_FILTER_FILE);
            if (file.exists()) {
                // 从文件中读取布隆过滤器数据
                InputStream is = Files.newInputStream(file.toPath());
                bloomFilter = BloomFilter.readFrom(is, Funnels.stringFunnel(Charset.defaultCharset()));
                logger.info("Bloom filter loaded from file.");
            }
        } catch (IOException e) {
            // 记录加载布隆过滤器失败的错误信息
            logger.error("Failed to load Bloom filter: {}", e.getMessage());
        }
        if (bloomFilter == null) {
            try {
                // 创建一个新的布隆过滤器
                bloomFilter = BloomFilter.create(
                    Funnels.stringFunnel(StandardCharsets.UTF_8),
                    1000000,
                    0.01);
                logger.info("New Bloom filter created.");
            } catch (Exception e) {
                // 记录创建布隆过滤器失败的错误信息
                logger.error("Failed to create Bloom filter: {}", e.getMessage());
            }
        }
        return bloomFilter;
    }

    /**
     * 将布隆过滤器的数据保存到文件中。
     */
    private void saveBloomFilter() {
        try (OutputStream os = Files.newOutputStream(Paths.get(BLOOM_FILTER_FILE))) {
            // 将布隆过滤器数据写入文件
            bloomFilter.writeTo(os);
            logger.info("Bloom filter saved to file.");
        } catch (IOException e) {
            // 记录保存布隆过滤器失败的错误信息
            logger.error("Failed to save Bloom filter: {}", e.getMessage());
        }
    }

    /**
     * 根据用户的 UID 获取用户信息。
     *
     * @param uid 用户的唯一标识
     * @return 包含用户信息的视图对象
     * @throws BusinessException 如果未找到对应的用户信息，抛出业务异常
     */
    @Override
    public UserInfoVo getUserByUID(long uid) {
        // 根据 UID 从数据库中查询用户信息
        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo == null) {
            // 记录未找到用户信息的错误日志
            logger.error("User not found for UID: {}", uid);
            throw new BusinessException(ExceptionEnum.UID_NOT_FIND);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        // 将 UserInfo 实体的属性复制到 UserInfoVo 视图对象中
        BeanUtils.copyProperties(userInfo, userInfoVo);
        // 获取用户所在区域的编码信息
        AreaCodeVo areaCodeVo = getAreaCode(userInfo.getAreaId());
        // 将区域编码信息复制到 UserInfoVo 视图对象中
        BeanUtils.copyProperties(areaCodeVo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 根据用户的身份证号码获取用户信息。
     *
     * @param identity_card 用户的身份证号码
     * @return 包含用户信息的视图对象
     * @throws BusinessException 如果未找到对应的用户信息，抛出业务异常
     */
    @Override
    public UserInfoVo getUserByID(String identity_card) {
        // 构建查询条件，根据身份证号码查询用户信息
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getIdentityCard, identity_card);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo == null) {
            // 记录未找到用户信息的错误日志
            logger.error("User not found for identity card: {}", identity_card);
            throw new BusinessException(ExceptionEnum.ID_NOT_FIND);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        // 将 UserInfo 实体的属性复制到 UserInfoVo 视图对象中
        BeanUtils.copyProperties(userInfo, userInfoVo);
        // 获取用户所在区域的编码信息
        AreaCodeVo areaCodeVo = getAreaCode(userInfo.getAreaId());
        // 将区域编码信息复制到 UserInfoVo 视图对象中
        BeanUtils.copyProperties(areaCodeVo, userInfoVo);
        return userInfoVo;
    }

    /**
     * 根据微信的 openID 获取对应的用户 UID。
     *
     * @param openID 微信的 openID
     * @return 用户的 UID，如果未找到则返回 -1
     */
    private long getInLogin(String openID) {
        // 构建查询条件，根据 openID 查询 UidMapping 表
        LambdaQueryWrapper<UidMapping> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UidMapping::getOpenid, openID);
        // 执行查询操作
        UidMapping uidMapping = userMappingMapper.selectOne(queryWrapper);
        // 根据查询结果返回 UID，如果未找到则返回 -1
        return uidMapping != null ? uidMapping.getUid() : -1;
    }

    /**
     * 为新的微信 openID 创建用户登录记录，并生成新的 UID。
     *
     * @param openID 微信的 openID
     * @return 新生成的用户 UID，如果创建失败则返回 -1
     */
    private long addInLogin(String openID) {
        LambdaQueryWrapper<UidMapping> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UidMapping::getOpenid, openID);
        UidMapping existMapping = userMappingMapper.selectOne(lambdaQueryWrapper);
        // 用户映射已存在
        if (existMapping != null) {
            return existMapping.getUid();
        }
        long uid;
        try {
            // 生成一个新的雪花算法 ID 作为 UID
            uid = IdUtil.getSnowflake().nextId();
            UidMapping uidMapping = new UidMapping();
            uidMapping.setUid(uid);
            uidMapping.setOpenid(openID);
            // 将 UidMapping 记录插入到数据库中
            int insertResult = userMappingMapper.insert(uidMapping);
            if (insertResult <= 0) {
                throw new RuntimeException("Failed to insert UidMapping record.");
            }
        } catch (Exception e) {
            // 记录插入 UidMapping 记录失败的错误信息
            e.printStackTrace();
            logger.error("Error during user login: {}", e.getMessage());
            uid = -1;
        }
        return uid;
    }

    /**
     * 用户登录方法，处理用户使用微信小程序登录的业务逻辑。
     * 通过传入的微信登录 code 获取用户的 openID，结合布隆过滤器判断用户是否为新用户，
     *
     * @param code 微信小程序登录时返回的 code，用于获取用户的 openID
     * @return JWT Token
     */
    @Override
    public UserLoginVo userLogin(String code) {
        // 调用微信工具类的方法，根据传入的 code 获取用户的微信 openID
        String openID = WeChatUtil.getOpenIDFromWX(code);

        // 检查布隆过滤器是否已经初始化，如果未初始化则调用方法进行初始化
        if (bloomFilter == null) {
            getInitBloomFilter();
        }

        // 初始化用户 UID 为 -1，表示尚未获取到有效的 UID
        long uid = -1;

        // 使用布隆过滤器判断该 openID 是否可能是新用户
        if (bloomFilter != null && !bloomFilter.mightContain(openID)) {
            // 如果布隆过滤器判断该 openID 可能不存在于已有记录中，调用方法为该 openID 创建新的登录记录并获取 UID
            uid = addInLogin(openID);
        }

        // 如果上一步未能获取到有效的 UID，则尝试从数据库中查询该 openID 对应的 UID
        if (uid == -1) {
            uid = getInLogin(openID);
            // 如果数据库中也未查询到对应的 UID，则再次调用方法为该 openID 创建新的登录记录并获取 UID
            if (uid == -1) {
                uid = addInLogin(openID);
            }
        }

        // 构建 Lambda 查询条件，根据用户 UID 查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUid, uid);
        // 执行查询操作，从数据库中获取用户信息
        UserInfo userInfo = userInfoMapper.selectOne(lambdaQueryWrapper);
        // 默认用户名称
        String userName = "微信用户";
        if (userInfo != null) {
            userName = userInfo.getName();
        }
        UserLoginVo userLoginVo = new UserLoginVo();
        // 设置用户登录结果视图对象中的用户名
        userLoginVo.setUserName(userName);
        // 生成 JWT Token 并设置到用户登录结果视图对象中，Token 有效期为 3600000 毫秒
        userLoginVo.setToken(JWTUtil.generateJWToken(uid, 3600000));

        return userLoginVo;
    }

    /**
     * 核酸检测用户登录方法。
     *
     * @param nucleicAcidsLoginDto 包含核酸检测用户登录信息的数据传输对象
     * @return 登录成功后生成的 JWT Token
     * @throws BusinessException 如果用户不存在、用户状态不可登录或密码错误，抛出业务异常
     */
    @Override
    public String nucleicAcidTestUserLogin(NucleicAcidsLoginDto nucleicAcidsLoginDto) {
        // 获取用户的身份证号码
        String identityCard = nucleicAcidsLoginDto.getIdentityCard();
        // 构建查询条件，根据身份证号码查询核酸检测用户信息
        LambdaQueryWrapper<NucleicAcidTestPersonnel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NucleicAcidTestPersonnel::getIdentityCard, identityCard);
        NucleicAcidTestPersonnel user = nucleicAcidTestPersonnelMapper.selectOne(queryWrapper);
        if (user == null) {
            // 记录用户不存在的错误日志
            logger.error("User not found for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        if (!user.getStatus()) {
            // 记录用户状态不可登录的错误日志
            logger.error("User is inactive for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        if (!passwordEncoder.matches(nucleicAcidsLoginDto.getPassword(), user.getPasswordHash())) {
            // 记录密码错误的错误日志
            logger.error("Password mismatch for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        // 生成 JWT Token
        String token = JWTUtil.generateJWToken(user.getTid(), 3600000);
        return token;
    }

    /**
     * 获取所有核酸检测人员的信息，并将其转换为适合前端展示的视图对象列表。
     *
     * @return 包含所有核酸检测人员视图对象的列表
     */
    @Override
    public List<NucleicAcidTestPersonnelVo> getNucleicAcidTestUser() {
        // 创建一个查询包装器，用于构建查询条件。这里不添加额外条件，意味着查询所有核酸检测人员记录
        QueryWrapper<NucleicAcidTestPersonnel> queryWrapper = new QueryWrapper<>();
        // 调用核酸检测人员数据访问层的 selectList 方法，执行查询操作，获取所有核酸检测人员的实体对象列表
        List<NucleicAcidTestPersonnel> nucleicAcidTestPersonnelList = nucleicAcidTestPersonnelMapper.selectList(queryWrapper);
        // 使用 Java 8 的 Stream API 对查询结果列表进行处理
        return nucleicAcidTestPersonnelList.stream()
            .map(userDao -> {
                // 构建前端所需的视图对象
                NucleicAcidTestPersonnelVo userVo = new NucleicAcidTestPersonnelVo();
                // 将实体对象的属性复制到视图对象中，方便前端展示
                BeanUtils.copyProperties(userDao, userVo);
                return userVo;
            })
            // 将处理后的元素收集到一个新的列表中
            .collect(Collectors.toList());
    }

    /**
     * 获取所有健康码管理人员的信息，并将其转换为适合前端展示的视图对象列表。
     *
     * @return 包含所有健康码管理人员视图对象的列表
     */
    @Override
    public List<HealthCodeManagerVo> getManagerUser() {
        // 创建一个查询包装器，用于构建查询条件。这里不添加额外条件，意味着查询所有健康码管理人员记录
        QueryWrapper<HealthCodeManager> queryWrapper = new QueryWrapper<>();
        // 调用健康码管理人员数据访问层的 selectList 方法，执行查询操作，获取所有健康码管理人员的实体对象列表
        List<HealthCodeManager> healthCodeManagerList = healthCodeManagerMapper.selectList(queryWrapper);
        // 使用 Java 8 的 Stream API 对查询结果列表进行处理
        return healthCodeManagerList.stream()
            .map(userDao -> {
                // 构建前端所需的视图对象
                HealthCodeManagerVo userVo = new HealthCodeManagerVo();
                // 将实体对象的属性复制到视图对象中，方便前端展示
                BeanUtils.copyProperties(userDao, userVo);
                return userVo;
            })
            // 将处理后的元素收集到一个新的列表中
            .collect(Collectors.toList());
    }

    /**
     * 创建新的核酸检测人员记录。在创建前会检查身份证号是否已存在，若存在则抛出业务异常。
     *
     * @param createNucleicAcidDto 包含新核酸检测人员信息的数据传输对象
     * @throws BusinessException 若身份证号已存在，抛出该异常
     */
    @Override
    public void newNucleicAcidTestUser(CreateNucleicAcidDto createNucleicAcidDto) {
        // 从数据传输对象中获取身份证号
        String identityCard = createNucleicAcidDto.getIdentityCard();
        // 创建一个 Lambda 查询包装器，用于构建根据身份证号查询核酸检测人员的条件
        LambdaQueryWrapper<NucleicAcidTestPersonnel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NucleicAcidTestPersonnel::getIdentityCard, identityCard);
        // 调用核酸检测人员数据访问层的 selectOne 方法，根据身份证号查询是否存在对应的人员记录
        NucleicAcidTestPersonnel existingUser = nucleicAcidTestPersonnelMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            // 若查询到记录，说明身份证号已存在，记录错误日志并抛出业务异常
            logger.error("Duplicate identity card found: {}", identityCard);
            throw new BusinessException(ExceptionEnum.ID_EXIST);
        }
        // 创建新的核酸检测人员实体对象
        NucleicAcidTestPersonnel newUser = new NucleicAcidTestPersonnel();
        // 设置新人员的身份证号
        newUser.setIdentityCard(identityCard);
        // 对传入的密码进行加密处理
        String password_hash = passwordEncoder.encode(createNucleicAcidDto.getPassword());
        // 设置新人员的加密密码
        newUser.setPasswordHash(password_hash);
        // 设置新人员的姓名
        newUser.setName(createNucleicAcidDto.getName());
        // 设置新人员的状态为可用
        newUser.setStatus(true);
        // 生成一个唯一的人员 ID
        long tid = IdUtil.getSnowflake().nextId();
        // 设置新人员的 ID
        newUser.setTid(tid);
        // 调用核酸检测人员数据访问层的 insert 方法，将新人员记录插入数据库
        nucleicAcidTestPersonnelMapper.insert(newUser);
    }

    /**
     * 创建新的健康码管理人员记录。在创建前会检查身份证号是否已存在，若存在则抛出业务异常。
     *
     * @param createManageDto 包含新健康码管理人员信息的数据传输对象
     * @throws BusinessException 若身份证号已存在，抛出该异常
     */
    @Override
    public void newMangerUser(CreateManageDto createManageDto) {
        // 从数据传输对象中获取身份证号
        String identityCard = createManageDto.getIdentityCard();
        // 创建一个查询包装器，用于构建根据身份证号查询健康码管理人员的条件
        QueryWrapper<HealthCodeManager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity_card", identityCard);
        // 调用健康码管理人员数据访问层的 selectOne 方法，根据身份证号查询是否存在对应的人员记录
        HealthCodeManager existingUser = healthCodeManagerMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            // 若查询到记录，说明身份证号已存在，记录错误日志并抛出业务异常
            logger.error("Duplicate identity card found: {}", identityCard);
            throw new BusinessException(ExceptionEnum.ID_EXIST);
        }
        // 创建新的健康码管理人员实体对象
        HealthCodeManager newUser = new HealthCodeManager();
        // 设置新人员的身份证号
        newUser.setIdentityCard(identityCard);
        // 对传入的密码进行加密处理
        String password_hash = passwordEncoder.encode(createManageDto.getPassword());
        // 设置新人员的加密密码
        newUser.setPasswordHash(password_hash);
        // 设置新人员的姓名
        newUser.setName(createManageDto.getName());
        // 设置新人员的状态为可用
        newUser.setStatus(true);
        // 生成一个唯一的人员 ID
        long mid = IdUtil.getSnowflake().nextId();
        // 设置新人员的 ID
        newUser.setMid(mid);
        // 调用健康码管理人员数据访问层的 insert 方法，将新人员记录插入数据库
        healthCodeManagerMapper.insert(newUser);
    }

    /**
     * 健康码管理人员登录方法。根据传入的登录信息进行身份验证，验证通过则生成并返回 JWT Token。
     *
     * @param managerLoginDto 包含健康码管理人员登录信息的数据传输对象
     * @return 登录成功后生成的 JWT Token
     * @throws BusinessException 若用户不存在、用户状态不可用或密码不匹配，抛出该异常
     */
    @Override
    public String managerLogin(ManagerLoginDto managerLoginDto) {
        // 从数据传输对象中获取身份证号
        String identityCard = managerLoginDto.getIdentityCard();
        // 创建一个 Lambda 查询包装器，用于构建根据身份证号查询健康码管理人员的条件
        LambdaQueryWrapper<HealthCodeManager> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HealthCodeManager::getIdentityCard, identityCard);
        // 调用健康码管理人员数据访问层的 selectOne 方法，根据身份证号查询对应的人员记录
        HealthCodeManager userDao = healthCodeManagerMapper.selectOne(lambdaQueryWrapper);
        if (userDao == null) {
            // 若未查询到记录，说明用户不存在，记录错误日志并抛出业务异常
            logger.error("Manager not found for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        if (!userDao.getStatus()) {
            // 若查询到的用户状态不可用，记录错误日志并抛出业务异常
            logger.error("Manager is inactive for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        if (!passwordEncoder.matches(managerLoginDto.getPassword(), userDao.getPasswordHash())) {
            // 若传入的密码与数据库中存储的加密密码不匹配，记录错误日志并抛出业务异常
            logger.error("Password mismatch for identity card: {}", identityCard);
            throw new BusinessException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
        // 验证通过，生成 JWT Token，Token 有效期为 3600000 毫秒
        String token = JWTUtil.generateJWToken(userDao.getMid(), 3600000);
        return token;
    }

    /**
     * 修改用户信息。根据传入的用户信息数据传输对象，先检查身份证号对应的用户是否存在，
     * 若不存在则创建新用户，若存在则检查 UID 是否匹配，匹配则更新用户信息。
     *
     * @param userInfoDto 包含用户信息的数据传输对象
     * @throws BusinessException 若身份证号对应的 UID 与传入的 UID 不匹配，抛出该异常
     */
    @Override
    public void modifyUserInfo(UserInfoDto userInfoDto) {
        Long uid = userInfoDto.getUid();
        String identityCard = userInfoDto.getIdentityCard();

        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getIdentityCard, identityCard);
        // 调用用户信息数据访问层的 selectOne 方法，根据身份证号查询对应的用户记录
        UserInfo userInfo = userInfoMapper.selectOne(lambdaQueryWrapper);

        if (userInfo == null) {
            // 若未查询到记录，说明用户不存在，调用方法创建新用户记录
            createAndInsertNewUserInfo(userInfoDto);
        } else {
            if (userInfo.getUid() != uid) {
                // 若查询到的用户 UID 与传入的 UID 不匹配，记录错误日志并抛出业务异常
                logger.error("UID mismatch for identity card: {}, expected UID: {}, found UID: {}", identityCard, uid, userInfo.getUid());
                throw new BusinessException(ExceptionEnum.ID_EXIST);
            }
            // 更新用户的姓名
            userInfo.setName(userInfoDto.getName());
            // 更新用户的电话号码
            userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
            // 根据传入的区域信息获取地址编码 ID，若地址编码不存在则添加新记录
            AreaCodeVo areaCodeVo = getAreaCode(new AreaCodeDto(userInfoDto.getDistrict(),
                userInfoDto.getStreet(), userInfoDto.getCommunity()));
            // 设置用户的新地址 ID
            userInfo.setAreaId(areaCodeVo.getId());
            // 设置用户的地址
            userInfo.setAddress(userInfoDto.getAddress());
            // 调用用户信息数据访问层的 updateById 方法，根据用户 ID 更新用户信息
            userInfoMapper.updateById(userInfo);
        }
    }

    /**
     * 根据传入的用户信息数据传输对象（UserInfoDto）创建一个新的用户信息实体（UserInfo），
     * 并根据其中的区域信息获取或创建对应的区域代码（AreaCode）记录，
     * 最后将新的用户信息插入到数据库中。
     *
     * @param userInfoDto 包含用户详细信息的 UserInfoDto 对象
     */
    private void createAndInsertNewUserInfo(UserInfoDto userInfoDto) {
        Integer district = userInfoDto.getDistrict();
        Integer street = userInfoDto.getStreet();
        Long community = userInfoDto.getCommunity();

        UserInfo userInfo= new UserInfo();
        userInfo.setUid(userInfoDto.getUid());
        userInfo.setIdentityCard(userInfoDto.getIdentityCard());
        userInfo.setName(userInfoDto.getName());
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());

        // 查询地址编码(不存在则会添加记录)
        AreaCodeVo areaCodeVo = getAreaCode(new AreaCodeDto(district, street, community));
        // 设置地址编码对应的id
        userInfo.setAreaId(areaCodeVo.getId());

        userInfo.setAddress(userInfoDto.getAddress());
        userInfoMapper.insert(userInfo);
    }

    /**
     * 更新核酸检测人员的状态。
     * @param statusNucleicAcidTestUserDto 包含核酸检测人员状态信息的 DTO
     */
    @Override
    public void statusNucleicAcidTestUser(StatusNucleicAcidTestUserDto statusNucleicAcidTestUserDto) {
        // 创建核酸检测人员实体对象
        NucleicAcidTestPersonnel nucleicAcidTestPersonnel = new NucleicAcidTestPersonnel();
        // 设置状态
        nucleicAcidTestPersonnel.setStatus(statusNucleicAcidTestUserDto.getStatus());
        // 根据实体对象的主键更新状态
        nucleicAcidTestPersonnelMapper.updateById(nucleicAcidTestPersonnel);
    }

    /**
     * 更新健康码管理人员的状态。
     * @param statusManagerDto 包含健康码管理人员状态信息的 DTO
     */
    @Override
    public void statusManager(StatusManagerDto statusManagerDto) {
        // 创建健康码管理人员实体对象
        HealthCodeManager healthCodeManager = new HealthCodeManager();
        // 设置状态
        healthCodeManager.setStatus(statusManagerDto.getStatus());
        // 根据实体对象的主键更新状态
        healthCodeManagerMapper.updateById(healthCodeManager);
    }

    /**
     * 添加或更新用户信息，会检查身份证号和手机号是否重复。
     * @param userInfoDto 包含用户信息的 DTO
     * @throws BusinessException 若身份证号或手机号已存在，抛出该异常
     */
    @Override
    public void addUserInfo(UserInfoDto userInfoDto) {
        Long uid = userInfoDto.getUid();
        String phoneNumber = userInfoDto.getPhoneNumber();
        String identityCard = userInfoDto.getIdentityCard();

        // 根据身份证号查询用户信息
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getIdentityCard, identityCard);
        UserInfo userInfo_id = userInfoMapper.selectOne(lambdaQueryWrapper);
        if (userInfo_id != null) {
            // 若身份证号已存在，记录错误日志并抛出异常
            logger.error("Duplicate identity card found: {}", identityCard);
            throw new BusinessException(ExceptionEnum.ID_EXIST);
        }

        // 清除查询条件，根据手机号查询用户信息
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(UserInfo::getPhoneNumber, phoneNumber);
        UserInfo userInfo_phone = userInfoMapper.selectOne(lambdaQueryWrapper);
        if (userInfo_phone != null) {
            // 若手机号已存在，记录错误日志并抛出异常
            logger.error("Duplicate phone number found: {}", phoneNumber);
            throw new BusinessException(ExceptionEnum.PHONE_EXIST);
        }

        UserInfo userInfo = userInfoMapper.selectById(uid);
        if (userInfo == null) {
            // 若用户不存在，创建并插入新用户信息
            createAndInsertNewUserInfo(userInfoDto);
        } else {
            // 若用户存在，更新用户信息
            userInfo.setUid(uid);
            userInfo.setIdentityCard(identityCard);
            userInfo.setName(userInfoDto.getName());
            userInfo.setPhoneNumber(phoneNumber);
            // 获取地址编码，不存在则添加记录
            AreaCodeVo areaCodeVo = getAreaCode(new AreaCodeDto(userInfoDto.getDistrict(),
                userInfoDto.getStreet(), userInfoDto.getCommunity()));
            // 设置新的地址编码 ID
            userInfo.setAreaId(areaCodeVo.getId());
            userInfo.setAddress(userInfoDto.getAddress());
            // 更新用户信息到数据库
            userInfoMapper.updateById(userInfo);
        }
    }

    /**
     * 根据 UID 修改用户信息。
     * @param uid 用户的唯一标识
     * @param userModifyDto 包含用户修改信息的 DTO
     * @throws BusinessException 若用户不存在，抛出该异常
     */
    @Override
    public void userModify(long uid, UserModifyDto userModifyDto) {
        // 构建查询条件
        LambdaQueryWrapper<UserInfo> userQueryWrapper = Wrappers.lambdaQuery();
        userQueryWrapper.eq(UserInfo::getUid, uid);
        // 根据条件查询用户信息
        UserInfo userInfo = userInfoMapper.selectOne(userQueryWrapper);

        if (userInfo != null) {
            // 若用户存在，构建地址编码 DTO 并获取地址编码信息
            AreaCodeDto areaCodeDto = AreaCodeDto.builder()
                .district(userModifyDto.getDistrictId())
                .street(userModifyDto.getStreetId())
                .community(userModifyDto.getCommunityId())
                .build();
            AreaCodeVo areaCodeVo = getAreaCode(areaCodeDto);

            // 设置新的地址编码 ID
            userInfo.setAreaId(areaCodeVo.getId());

            // 更新用户信息
            userInfo.setName(userModifyDto.getName());
            userInfo.setPhoneNumber(userModifyDto.getPhoneNumber());
            userInfo.setAddress(userModifyDto.getAddress());

            // 更新用户信息到数据库
            userInfoMapper.updateById(userInfo);
        } else {
            // 若用户不存在，记录错误日志并抛出异常
            logger.error("User not found for UID: {}", uid);
            throw new BusinessException(ExceptionEnum.UID_NOT_FIND);
        }
    }

    /**
     * 反转核酸检测人员的状态。
     * @param tid 核酸检测人员的唯一标识
     * @throws BusinessException 若核酸检测人员不存在，抛出该异常
     */
    @Override
    public void nucleicAcidOpposite(long tid) {
        // 构建查询条件
        LambdaQueryWrapper<NucleicAcidTestPersonnel> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NucleicAcidTestPersonnel::getTid, tid);
        // 根据条件查询核酸检测人员信息
        NucleicAcidTestPersonnel nucleicAcidTestPersonnel = nucleicAcidTestPersonnelMapper.selectOne(queryWrapper);

        if (nucleicAcidTestPersonnel == null) {
            // 若核酸检测人员不存在，记录错误日志并抛出异常
            logger.error("Nucleic acid test user not found for TID: {}", tid);
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_USER_NOT_FIND);
        }

        // 获取当前状态并取反
        Boolean status = nucleicAcidTestPersonnel.getStatus();
        Boolean newStatus = !status;

        // 构建更新条件
        LambdaUpdateWrapper<NucleicAcidTestPersonnel> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(NucleicAcidTestPersonnel::getStatus, newStatus)
            .eq(NucleicAcidTestPersonnel::getTid, tid);

        // 执行更新操作
        nucleicAcidTestPersonnelMapper.update(null, updateWrapper);
    }

    /**
     * 反转健康码管理人员的状态。
     * @param mid 健康码管理人员的唯一标识
     * @throws BusinessException 若健康码管理人员不存在，抛出该异常
     */
    @Override
    public void manageOpposite(long mid) {
        // 构建查询条件
        LambdaQueryWrapper<HealthCodeManager> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(HealthCodeManager::getMid, mid);
        // 根据条件查询健康码管理人员信息
        HealthCodeManager healthCodeManager = healthCodeManagerMapper.selectOne(queryWrapper);
        if (healthCodeManager == null) {
            // 若健康码管理人员不存在，抛出异常
            throw new BusinessException(ExceptionEnum.MANAGER_USER_NOT_FIND);
        }

        // 获取当前状态并取反
        Boolean status = healthCodeManager.getStatus();
        Boolean newStatus = !status;

        // 构建更新条件
        LambdaUpdateWrapper<HealthCodeManager> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(HealthCodeManager::getStatus, newStatus)
            .eq(HealthCodeManager::getMid, mid);

        // 执行更新操作
        healthCodeManagerMapper.update(null, updateWrapper);
    }

    /**
     * 测试用的用户登录方法，模拟根据微信登录码生成 openID 进行登录逻辑。
     *
     * @param code 模拟的微信登录码
     * @return 生成的 JWT Token
     */
    @Override
    public String userLogin_test(String code) {
        // 模拟生成 openID
        String openID = "openid-" + code;
        // 确保布隆过滤器已初始化
        if (bloomFilter == null) {
            getInitBloomFilter();
        }
        long uid = -1;
        // 若布隆过滤器判断 openID 可能不存在
        if (bloomFilter != null && !bloomFilter.mightContain(openID)) {
            // 尝试添加登录记录获取 UID
            uid = addInLogin(openID);
            if (uid == -1) {
                // 若添加失败，尝试从已有记录获取 UID
                uid = getInLogin(openID);
            }
            // 将 openID 加入布隆过滤器
            bloomFilter.put(openID);
            // 生成 JWT Token
            return JWTUtil.generateJWToken(uid, 3600000);
        }
        // 若布隆过滤器判断 openID 可能存在，尝试从已有记录获取 UID
        uid = getInLogin(openID);
        if (uid == -1) {
            // 若获取失败，添加登录记录获取 UID
            uid = addInLogin(openID);
        }
        // 生成 JWT Token
        return JWTUtil.generateJWToken(uid, 3600000);
    }

    /**
     * 根据 UID 删除用户信息。
     *
     * @param uid 用户的唯一标识
     */
    @Override
    public void deleteUserInfo(long uid) {
        // 构建查询条件，检查用户信息是否存在
        LambdaQueryWrapper<UserInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserInfo::getUid, uid);
        Long count = userInfoMapper.selectCount(queryWrapper);

        if (count > 0) {
            // 若存在，删除用户信息
            userInfoMapper.deleteById(uid);
        } else {
            // 若不存在，记录警告日志
            logger.warn("Attempted to delete non-existent user info for UID: {}", uid);
        }
    }

    /**
     * 根据 ID 获取区域编码信息。
     *
     * @param id 区域编码的 ID
     * @return 区域编码视图对象，若不存在则返回 null
     */
    @Override
    public AreaCodeVo getAreaCode(long id) {
        // 根据 ID 查询区域编码信息
        AreaCode areaCode = areaCodeMapper.selectById(id);
        if (areaCode == null) {
            return null;
        }
        AreaCodeVo areaCodeVo = new AreaCodeVo();
        BeanUtil.copyProperties(areaCode, areaCodeVo);
        return areaCodeVo;
    }

    /**
     * 根据区域编码 DTO 获取区域编码信息，若不存在则插入新记录。
     *
     * @param areaCodeDto 包含区域信息的 DTO
     * @return 区域编码视图对象
     */
    @Override
    public AreaCodeVo getAreaCode(AreaCodeDto areaCodeDto) {
        // 构建查询条件，检查区域编码是否存在
        LambdaQueryWrapper<AreaCode> areaCodeQueryWrapper = Wrappers.lambdaQuery();
        areaCodeQueryWrapper
            .eq(AreaCode::getDistrict, areaCodeDto.getDistrict())
            .eq(AreaCode::getStreet, areaCodeDto.getStreet())
            .eq(AreaCode::getCommunity, areaCodeDto.getCommunity());
        AreaCode existingAreaCode = areaCodeMapper.selectOne(areaCodeQueryWrapper);

        AreaCodeVo areaCodeVo = new AreaCodeVo();

        if (existingAreaCode != null) {
            // 若存在，复制属性到视图对象
            BeanUtil.copyProperties(existingAreaCode, areaCodeVo);
        } else {
            // 若不存在，插入新记录
            AreaCode newAreaCode = AreaCode.builder().build();
            BeanUtil.copyProperties(areaCodeDto, newAreaCode);
            // 插入新记录，插入后 newAreaCode 会包含新的自增 ID
            areaCodeMapper.insert(newAreaCode);
            // 复制属性到视图对象
            BeanUtil.copyProperties(newAreaCode, areaCodeVo);
        }
        return areaCodeVo;
    }

    /**
     * 根据区域编码 DTO 获取区域编码列表。
     *
     * @param areaCodeDto 包含区域信息的 DTO
     * @return 区域编码视图对象列表
     */
    @Override
    public List<AreaCodeVo> getAreaCodeList(AreaCodeDto areaCodeDto) {
        // 空指针检查
        if (areaCodeDto == null) {
            return new ArrayList<AreaCodeVo>();
        }

        // 构建查询条件
        LambdaQueryWrapper<AreaCode> areaCodeQueryWrapper = Wrappers.lambdaQuery();
        if (areaCodeDto.getDistrict() != null) {
            areaCodeQueryWrapper.eq(AreaCode::getDistrict, areaCodeDto.getDistrict());
        }
        if (areaCodeDto.getStreet() != null) {
            areaCodeQueryWrapper.eq(AreaCode::getStreet, areaCodeDto.getStreet());
        }
        if (areaCodeDto.getCommunity() != null) {
            areaCodeQueryWrapper.eq(AreaCode::getCommunity, areaCodeDto.getCommunity());
        }

        // 执行查询，获取区域编码列表
        List<AreaCode> records = areaCodeMapper.selectList(areaCodeQueryWrapper);

        // 将区域编码列表转换为区域编码视图对象列表
        return records.stream()
            .map(areaCode -> {
                AreaCodeVo areaCodeVo = new AreaCodeVo();
                // 复制属性到视图对象
                BeanUtils.copyProperties(areaCode, areaCodeVo);
                return areaCodeVo;
            })
            .collect(Collectors.toList());
    }
}