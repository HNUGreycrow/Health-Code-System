package org.software.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.software.code.client.HealthCodeClient;
import org.software.code.client.PlaceCodeClient;
import org.software.code.client.UserClient;
import org.software.code.common.result.Result;
import org.software.code.dto.AddNucleicAcidTestRecordByIDDto;
import org.software.code.dto.AddNucleicAcidTestRecordDto;
import org.software.code.dto.NotificationMessageDto;
import org.software.code.dto.NucleicAcidTestRecordDto;
import org.software.code.dto.NucleicAcidTestRecordInputDto;
import org.software.code.dto.TranscodingEventsDto;
import org.software.code.entity.NucleicAcidTest;
import org.software.code.entity.TubeInfo;
import org.software.code.kafaka.NotificationProducer;
import org.software.code.mapper.NucleicAcidTestMapper;
import org.software.code.mapper.TubeInfoMapper;
import org.software.code.service.NucleicAcidsService;
import org.software.code.service.notification.CommunityNotificationHandler;
import org.software.code.service.notification.EpidemicPreventionNotificationHandler;
import org.software.code.service.notification.NotificationChain;
import org.software.code.service.notification.SmsNotificationHandler;
import org.software.code.service.strategy.RiskCalculationContext;
import org.software.code.vo.NucleicAcidTestInfoVo;
import org.software.code.vo.NucleicAcidTestResultVo;
import org.software.code.vo.PositiveInfoVo;
import org.software.code.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service // 标记该类为Spring的服务组件
public class NucleicAcidsServiceImpl implements NucleicAcidsService {

    @Resource // 注入核酸检测Mapper，用于操作核酸检测相关数据库表
    private NucleicAcidTestMapper nucleicAcidTestMapper;

    @Resource // 注入试管信息Mapper，用于操作试管信息相关数据库表
    private  TubeInfoMapper  tubeInfoMapper;

    @Resource // 注入健康码客户端，用于处理健康码相关业务
    HealthCodeClient healthCodeClient;

    @Resource // 注入场所码客户端，用于处理场所码相关业务
    PlaceCodeClient placeCodeClient;

    @Resource // 注入用户客户端，用于处理用户相关业务
    UserClient userClient;

    @Resource // 注入通知生产者，用于发送通知消息
    private NotificationProducer notificationProducer;

    @Resource // 注入风险计算上下文，用于风险计算相关业务
    private RiskCalculationContext riskCalculationContext;

    /**
     * 添加核酸检测记录
     * @param nucleicAcidTestRecordDto 核酸检测记录的数据传输对象，包含核酸检测相关信息
     */
    @Override
    public void addNucleicAcidTestRecord(NucleicAcidTestRecordDto nucleicAcidTestRecordDto) {
        // 创建核酸检测实体对象
        NucleicAcidTest nucleicAcidTest = new NucleicAcidTest();
        // 将传入的DTO对象的属性复制到核酸检测实体对象中
        BeanUtils.copyProperties(nucleicAcidTestRecordDto, nucleicAcidTest);

        // 创建并插入 TubeInfo 对象
        TubeInfo tubeInfo = TubeInfo.builder()
                .tubeid(nucleicAcidTestRecordDto.getTubeid()) // 设置试管ID
                .kind(nucleicAcidTestRecordDto.getKind()) // 设置试管类型
                .testingOrganization(nucleicAcidTestRecordDto.getTesting_organization()) // 设置检测机构
                .build();
        // 将试管信息插入数据库
        tubeInfoMapper.insert(tubeInfo);

        // 如果为单管类型，将该 uid 三天内的其他核酸检测记录标记为已复检状态
        if (tubeInfo.getKind() == 0) {
            // 计算三天前的时间
            LocalDateTime threeDayAgo = LocalDateTime.now().minusDays(3);

            // 使用 LambdaUpdateWrapper 构建更新条件
            LambdaUpdateWrapper<NucleicAcidTest> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(NucleicAcidTest::getReTest, true) // 设置复检状态为 true
                    .eq(NucleicAcidTest::getUid, nucleicAcidTest.getUid()) // 筛选相同 uid 的记录
                    .ge(NucleicAcidTest::getCreatedAt, threeDayAgo) // 筛选三天内的记录
                    .eq(NucleicAcidTest::getReTest, false); // 筛选未复检的记录

            // 调用 MyBatis-Plus 的 update 方法进行更新
            nucleicAcidTestMapper.update(null, updateWrapper);
        }

        // 插入核酸检测记录到数据库
        nucleicAcidTestMapper.insert(nucleicAcidTest);

//        AreaCodeDto areaCodeDto = new AreaCodeDto();
//        BeanUtil.copyProperties(nucleicAcidTestRecordDto, areaCodeDto);
//        Result<?> result = userClient.getAreaCodeID(areaCodeDto);
//        AreaCodeVo areaCodeVo = new AreaCodeVo();
//        BeanUtil.copyProperties(result.getData(), areaCodeVo);
    }

    /**
     * 根据试管ID（tubeid）获取对应的用户ID（uids）列表
     *
     * @param tubeid 试管的唯一标识ID
     * @return 与该试管ID关联的用户ID列表
     */
    public List<Long> getUidsByTubeid(Long tubeid) {
        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        // 指定查询的字段为 NucleicAcidTest 实体类中的 uid 字段
        queryWrapper.select(NucleicAcidTest::getUid)
                // 添加筛选条件，筛选出试管ID等于传入的 tubeid 的记录
                .eq(NucleicAcidTest::getTubeid, tubeid);

        // 调用 MyBatis-Plus 提供的 selectObjs 方法，根据构建好的查询条件进行查询
        // 该方法会返回一个包含查询结果的列表，列表中的元素为 Object 类型，这里会自动转换为 Long 类型
        return nucleicAcidTestMapper.selectObjs(queryWrapper);
    }

    /**
     * 根据用户ID（uid）和试管ID（tubeid）查找核酸检测记录
     *
     * @param uid    用户的唯一标识ID
     * @param tubeid 试管的唯一标识ID
     * @return 符合条件的核酸检测记录，如果没有找到则返回 null
     */
    public NucleicAcidTest findTestRecordsByUidAndTubeid(Long uid, Long tubeid) {
        // 创建一个 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        // 添加筛选条件，筛选出用户ID等于传入的 uid 的记录
        queryWrapper.eq(NucleicAcidTest::getUid, uid)
                // 继续添加筛选条件，筛选出试管ID等于传入的 tubeid 的记录
                .eq(NucleicAcidTest::getTubeid, tubeid)
                // 使用 last 方法添加原生 SQL 语句，限制查询结果为 1 条记录
                .last("LIMIT 1");

        // 调用 MyBatis-Plus 提供的 selectOne 方法，根据构建好的查询条件进行查询
        // 该方法会返回符合条件的第一条记录，如果没有符合条件的记录则返回 null
        return nucleicAcidTestMapper.selectOne(queryWrapper);
    }

    /**
     * 批量录入核酸检测记录
     * @param testRecords 核酸检测记录输入列表
     */
    @Override
    public void enterNucleicAcidTestRecordList(List<NucleicAcidTestRecordInputDto> testRecords) {
        for (NucleicAcidTestRecordInputDto input : testRecords) {
            // 构建 TubeInfo 更新条件
            LambdaUpdateWrapper<TubeInfo> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(TubeInfo::getResult, input.getResult())
                    .set(TubeInfo::getTestingOrganization, input.getTesting_organization())
                    .eq(TubeInfo::getTubeid, input.getTubeid())
                    .eq(TubeInfo::getKind, input.getKind());
            // 更新 TubeInfo 表检测结果信息
            tubeInfoMapper.update(null, updateWrapper);

            // 获取该试管关联的用户 ID 列表
            List<Long> uids = getUidsByTubeid(input.getTubeid());

            // 混管且阳性，相关用户转黄码
            if (input.getKind() != 0 && input.getResult() == 1) {
                for (Long uid : uids) {
                    TranscodingEventsDto transcodingEventsDto = new TranscodingEventsDto(uid, 1);
                    healthCodeClient.transcodingHealthCodeEvents(transcodingEventsDto);
                }
                // 构建核酸检测记录更新条件
                LambdaUpdateWrapper<NucleicAcidTest> nucleicAcidTestLambdaUpdateWrapper = Wrappers.lambdaUpdate();
                nucleicAcidTestLambdaUpdateWrapper.set(NucleicAcidTest::getReTest, false)
                        .eq(NucleicAcidTest::getTubeid, input.getTubeid());
                // 更新核酸检测记录
                nucleicAcidTestMapper.update(null, nucleicAcidTestLambdaUpdateWrapper);
            }
            // 单管且阳性，发送通知并相关用户转红码
            else if (input.getKind() == 0 && input.getResult() == 1) {
                for (Long uid : uids) {
                    // 获取用户核酸检测记录
                    NucleicAcidTest nucleicAcidTest = findTestRecordsByUidAndTubeid(uid, input.getTubeid());
                    NotificationMessageDto message = new NotificationMessageDto();
                    // 获取用户信息
                    Result<?> result = userClient.getUserByUID(uid);
                    ObjectMapper objectMapper = new ObjectMapper();
                    UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);
                    // 设置通知消息信息
                    message.setName(userInfoVo.getName());
                    message.setIdentity_card(userInfoVo.getIdentityCard());
                    message.setPhone(userInfoVo.getPhoneNumber());
                    message.setType("POSITIVE");
                    // 发送通知消息
                    notificationProducer.sendNotification("notification-topic", message);
                    TranscodingEventsDto transcodingEventsDto = new TranscodingEventsDto(uid, 2);
                    // 执行转码操作
                    healthCodeClient.transcodingHealthCodeEvents(transcodingEventsDto);
                }
            }
            // 阴性，相关用户转绿码
            else if (input.getResult() == 0) {
                for (Long uid : uids) {
                    TranscodingEventsDto transcodingEventsDto = new TranscodingEventsDto(uid, 0);
                    healthCodeClient.transcodingHealthCodeEvents(transcodingEventsDto);
                }
            }
        }
    }

    /**
     * 根据用户 ID 获取该用户的最后一条核酸检测记录
     * @param uid 用户 ID
     * @return 核酸检测结果视图对象，若没有记录则返回 null
     */
    @Override
    public NucleicAcidTestResultVo getLastNucleicAcidTestRecordByUID(long uid) {
        // 使用 LambdaQueryWrapper 构建查询条件，筛选出指定用户 ID 的记录，并按创建时间降序排列，取第一条记录
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NucleicAcidTest::getUid, uid)
                .orderByDesc(NucleicAcidTest::getCreatedAt)
                .last("LIMIT 1");

        // 调用 MyBatis-Plus 的 selectOne 方法进行查询，获取符合条件的核酸检测记录
        NucleicAcidTest nucleicAcidTest = nucleicAcidTestMapper.selectOne(queryWrapper);
        // 若未查询到记录，则返回 null
        if (nucleicAcidTest == null) {
            return null;
        }

        // 根据查询到的核酸检测记录，获取对应的核酸检测结果视图对象
        return getNucleicAcidTestResultVoByTubeid(nucleicAcidTest);
    }

    /**
     * 根据核酸检测记录的试管 ID 获取对应的核酸检测结果视图对象
     * @param nucleicAcidTest 核酸检测记录实体
     * @return 核酸检测结果视图对象
     */
    private NucleicAcidTestResultVo getNucleicAcidTestResultVoByTubeid(NucleicAcidTest nucleicAcidTest) {
        // 创建 TubeInfo 的查询条件包装器
        LambdaQueryWrapper<TubeInfo> tubeInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 设置查询条件，筛选出试管 ID 与传入核酸检测记录的试管 ID 相同的记录
        tubeInfoLambdaQueryWrapper.eq(TubeInfo::getTubeid, nucleicAcidTest.getTubeid());
        // 执行查询，获取符合条件的 TubeInfo 记录
        TubeInfo tubeInfo = tubeInfoMapper.selectOne(tubeInfoLambdaQueryWrapper);

        // 创建核酸检测结果视图对象
        NucleicAcidTestResultVo resultDto = new NucleicAcidTestResultVo();
        // 将 TubeInfo 对象的属性复制到核酸检测结果视图对象中
        BeanUtils.copyProperties(tubeInfo, resultDto);
        // 返回核酸检测结果视图对象
        return resultDto;
    }

    /**
     * 根据用户 ID 获取该用户最近 14 天内的核酸检测记录列表
     * @param uid 用户 ID
     * @return 核酸检测结果视图对象列表
     */
    @Override
    public List<NucleicAcidTestResultVo> getNucleicAcidTestRecordByUID(long uid) {

        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();

        // 计算 14 天前的时间
        LocalDateTime fourteenDaysAgo = LocalDateTime.now().minusDays(14);
        // 将 LocalDateTime 转换为 Date 类型，用于后续查询
        Date fourteenDaysAgoDate = Date.from(fourteenDaysAgo.atZone(ZoneId.systemDefault()).toInstant());

        // 设置查询条件：用户 ID 等于传入的 uid 且创建时间大于等于 14 天前
        queryWrapper.eq(NucleicAcidTest::getUid, uid)
                .ge(NucleicAcidTest::getCreatedAt, fourteenDaysAgoDate);

        // 执行查询，获取符合条件的核酸检测记录列表
        List<NucleicAcidTest> records = nucleicAcidTestMapper.selectList(queryWrapper);
        // 将查询到的核酸检测记录列表转换为核酸检测结果视图对象列表并返回
        return records.stream()
                .map(this::getNucleicAcidTestResultVoByTubeid)
                .collect(Collectors.toList());
    }

    /**
     * 根据开始时间和结束时间获取核酸检测信息视图对象
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 核酸检测信息视图对象
     */
    @Override
    public NucleicAcidTestInfoVo getNucleicAcidTestInfoByTime(Date startTime, Date endTime) {
        // 将 Date 类型的开始时间转换为 LocalDateTime 类型
        LocalDateTime start = convertDateToLocalDateTime(startTime);
        // 将 Date 类型的结束时间转换为 LocalDateTime 类型
        LocalDateTime end = convertDateToLocalDateTime(endTime);

        // 调用方法统计指定时间内的核酸检测总记录数
        long record = countNucleicAcidTestsByTime(start, end);
        // 调用方法统计指定时间内未检测的核酸检测记录数
        long uncheck = countUncheckedNucleicAcidTests(start, end);
        // 调用方法统计指定时间内单管阳性的核酸检测记录数
        long onePositive = countSingleTubePositiveNucleicAcidTests(start, end);
        // 调用方法统计指定时间内非单管阳性的核酸检测记录数
        long positive = countNonSingleTubePositiveNucleicAcidTests(start, end);

        // 创建核酸检测信息视图对象
        NucleicAcidTestInfoVo infoDto = new NucleicAcidTestInfoVo();
        // 设置总记录数
        infoDto.setRecord(record);
        // 设置未检测记录数
        infoDto.setUncheck(uncheck);
        // 设置单管阳性记录数
        infoDto.setOnePositive(onePositive);
        // 设置非单管阳性记录数
        infoDto.setPositive(positive);
        // 返回核酸检测信息视图对象
        return infoDto;
    }

    /**
     * 统计指定时间范围内的核酸检测记录数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 核酸检测记录数量
     */
    private long countNucleicAcidTestsByTime(LocalDateTime startTime, LocalDateTime endTime) {
        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        addTimeRangeCondition(queryWrapper, NucleicAcidTest::getCreatedAt, startTime, endTime);
        // 执行查询并返回记录数量
        return nucleicAcidTestMapper.selectCount(queryWrapper);
    }

    /**
     * 统计指定时间范围内未检测的核酸检测记录数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 未检测的核酸检测记录数量
     */
    private long countUncheckedNucleicAcidTests(LocalDateTime startTime, LocalDateTime endTime) {
        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.and(wrapper -> wrapper.eq(TubeInfo::getResult, 2).or().isNull(TubeInfo::getResult));
        addTimeRangeCondition(queryWrapper, TubeInfo::getCreatedAt, startTime, endTime);
        // 获取符合条件的管 ID 列表
        List<Long> tubeids = getTubeIds(queryWrapper);
        // 根据管 ID 列表统计核酸检测记录数量
        return countNucleicAcidTestsByTubeIds(tubeids);
    }

    /**
     * 统计指定时间范围内单管阳性的核酸检测记录数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 单管阳性的核酸检测记录数量
     */
    private long countSingleTubePositiveNucleicAcidTests(LocalDateTime startTime, LocalDateTime endTime) {
        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TubeInfo::getKind, 0)
                .eq(TubeInfo::getResult, 1);
        addTimeRangeCondition(queryWrapper, TubeInfo::getCreatedAt, startTime, endTime);
        // 获取符合条件的管 ID 列表
        List<Long> tubeids = getTubeIds(queryWrapper);
        // 根据管 ID 列表统计核酸检测记录数量
        return countNucleicAcidTestsByTubeIds(tubeids);
    }

    /**
     * 统计指定时间范围内非单管阳性的核酸检测记录数量
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 非单管阳性的核酸检测记录数量
     */
    private long countNonSingleTubePositiveNucleicAcidTests(LocalDateTime startTime, LocalDateTime endTime) {
        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.ne(TubeInfo::getKind, 0)
                .eq(TubeInfo::getResult, 1);
        addTimeRangeCondition(queryWrapper, TubeInfo::getCreatedAt, startTime, endTime);
        // 获取符合条件的管 ID 列表
        List<Long> tubeids = getTubeIds(queryWrapper);
        // 根据管 ID 列表统计核酸检测记录数量
        return countNucleicAcidTestsByTubeIds(tubeids);
    }

    /**
     * 添加时间范围条件到查询包装器
     * @param queryWrapper 查询包装器
     * @param timeColumn 时间字段
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param <T> 实体类型
     */
    private <T> void addTimeRangeCondition(LambdaQueryWrapper<T> queryWrapper, SFunction<T, ?> timeColumn, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null) {
            queryWrapper.ge(timeColumn, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(timeColumn, endTime.with(LocalTime.MAX));
        }
    }

    /**
     * 根据查询条件获取管 ID 列表
     * @param queryWrapper 查询条件
     * @return 管 ID 列表
     */
    private List<Long> getTubeIds(LambdaQueryWrapper<TubeInfo> queryWrapper) {
        // 执行查询并将结果转换为管 ID 列表
        return tubeInfoMapper.selectList(queryWrapper).stream()
                .map(TubeInfo::getTubeid)
                .collect(Collectors.toList());
    }

    /**
     * 根据管 ID 列表统计核酸检测记录数量
     * @param tubeids 管 ID 列表
     * @return 核酸检测记录数量
     */
    private long countNucleicAcidTestsByTubeIds(List<Long> tubeids) {
        // 如果管 ID 列表为空，直接返回 0
        if (tubeids.isEmpty()) {
            return 0;
        }
        // 创建 LambdaQueryWrapper 用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        // 设置查询条件：管 ID 在给定的管 ID 列表中
        queryWrapper.in(NucleicAcidTest::getTubeid, tubeids);
        // 执行查询并返回记录数量
        return nucleicAcidTestMapper.selectCount(queryWrapper);
    }

    /**
     * 将 Date 类型转换为 LocalDateTime 类型
     * @param date Date 类型的日期
     * @return LocalDateTime 类型的日期
     */
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 根据指定的开始时间和结束时间获取阳性信息列表。
     * 该方法首先查询在指定时间范围内结果为阳性的 TubeInfo 记录，
     * 然后根据这些 TubeInfo 的 tubeid 查询对应的 NucleicAcidTest 记录，
     * 最后将这些记录转换为 PositiveInfoVo 列表返回。
     * 如果没有符合条件的 TubeInfo 记录，则返回 null。
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 包含阳性信息的 PositiveInfoVo 列表，如果没有符合条件的记录则返回 null
     */
    @Override
    public List<PositiveInfoVo> getPositiveInfoByTime(Date startTime, Date endTime) {
        // 使用 LambdaQueryWrapper 构建查询条件，筛选出结果为阳性且创建时间在指定范围内的 TubeInfo 记录
        LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TubeInfo::getResult, 1);

        addTimeRangeCondition(queryWrapper, TubeInfo::getCreatedAt,
                convertDateToLocalDateTime(startTime), convertDateToLocalDateTime(endTime));

        // 调用 MyBatis-Plus 的 selectList 方法进行查询，获取符合条件的 TubeInfo 列表
        List<TubeInfo> tubeInfos =  tubeInfoMapper.selectList(queryWrapper);
        // 提取 TubeInfo 列表中的 tubeid 到一个新的列表中
        List<Long> tubeids = tubeInfos.stream().map(TubeInfo::getTubeid).collect(Collectors.toList());

        // 如果 tubeids 列表为空，说明没有符合条件的记录，直接返回 null
        if (tubeids.isEmpty()) {
            return null;
        }
        // 构建新的 LambdaQueryWrapper，用于查询 NucleicAcidTest 表
        LambdaQueryWrapper<NucleicAcidTest> nucleicAcidTestLambdaQueryWrapper = Wrappers.lambdaQuery();
        // 设置查询条件，筛选出 tubeid 在 tubeids 列表中的 NucleicAcidTest 记录
        nucleicAcidTestLambdaQueryWrapper.in(NucleicAcidTest::getTubeid, tubeids);
        // 调用 MyBatis-Plus 的 selectList 方法进行查询，获取符合条件的 NucleicAcidTest 列表
        List<NucleicAcidTest> nucleicAcidTests = nucleicAcidTestMapper.selectList(nucleicAcidTestLambdaQueryWrapper);

        // 将 NucleicAcidTest 列表转换为 PositiveInfoVo 列表
        return nucleicAcidTests.stream()
                .map(record -> {
                    // 创建 PositiveInfoVo 对象
                    PositiveInfoVo infoVo = new PositiveInfoVo();
                    // 将 NucleicAcidTest 对象的属性复制到 PositiveInfoVo 对象中
                    BeanUtil.copyProperties(record, infoVo);

                    // 查找对应的 TubeInfo 对象
                    tubeInfos.stream()
                            .filter(tubeInfo -> tubeInfo.getTubeid().equals(infoVo.getTubeid()))
                            .findFirst()
                            .ifPresent(tubeInfo -> {
                                // 可以根据需要将 TubeInfo 的其他属性复制到 infoVo 中
                                BeanUtil.copyProperties(tubeInfo, infoVo);
                            });

                    // 调用 userClient 的 getUserByUID 方法，根据 uid 获取用户信息
                    Result<?> result = userClient.getUserByUID(record.getUid());
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 将获取到的用户信息转换为 UserInfoVo 对象
                    UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);
                    // 将 UserInfoVo 对象的属性复制到 PositiveInfoVo 对象中
                    BeanUtil.copyProperties(userInfoVo, infoVo);

                    return infoVo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取需要通知复检的核酸检测记录，并发送通知。
     * 该方法会查询三天内未进行复检的核酸检测记录，
     * 然后构建通知链，依次添加短信通知处理器、社区通知处理器和疫情防控通知处理器。
     * 对于每一条查询到的核酸检测记录，获取对应的用户信息并构建通知消息，
     * 最后通过通知链执行通知操作。
     */
    @Override
    public void getNoticeReTestRecords() {
        // 计算三天前的日期
        Date threeDaysAgo = new Date(System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000);

        // 使用 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<NucleicAcidTest> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NucleicAcidTest::getReTest, false)
                .ge(NucleicAcidTest::getCreatedAt, threeDaysAgo);
        // 调用 MyBatis-Plus 的 selectList 方法进行查询
        List<NucleicAcidTest> nucleicAcidTests = nucleicAcidTestMapper.selectList(queryWrapper);

        // 构建通知链，依次添加短信通知处理器、社区通知处理器和疫情防控通知处理器
        NotificationChain notificationChain = new NotificationChain()
                .addHandler(new SmsNotificationHandler(notificationProducer))
                .addHandler(new CommunityNotificationHandler(notificationProducer))
                .addHandler(new EpidemicPreventionNotificationHandler(notificationProducer));

        for (NucleicAcidTest record : nucleicAcidTests) {
            // 根据核酸检测记录的 uid 获取用户信息
            Result<?> result = userClient.getUserByUID(record.getUid());
            ObjectMapper objectMapper = new ObjectMapper();
            UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);

            // 构建通知消息
            NotificationMessageDto message = new NotificationMessageDto();
            message.setName(userInfoVo.getName());
            message.setIdentity_card(userInfoVo.getIdentityCard());
            message.setPhone(userInfoVo.getPhoneNumber());
            // 通过通知链执行通知操作
            notificationChain.execute(message);
        }
    }

    /**
     * 自动修改场所风险等级的方法。
     * 该方法会计算出一天前的日期，并将当前日期和一天前的日期格式化为指定格式的字符串。
     * 接着获取所有场所码，对于每个场所码，获取前一天到过该场所的人员列表并去重，统计总人数。
     * 然后构建查询条件，查询出前一天单管阳性的检测管信息，根据这些检测管信息进一步查询出阳性人员的 uid 列表。
     * 最后统计出阳性人员数量，调用风险计算上下文计算该场所的风险等级，并设置场所的风险等级。
     */
    @Override
    public void autoModify() {
        ObjectMapper objectMapper = new ObjectMapper();
        Date oneDayAgo = new Date(System.currentTimeMillis() - (long) 24 * 60 * 60 * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oneDayAgoFormatted = dateFormat.format(oneDayAgo);
        Date now = new Date();
        String nowFormatted = dateFormat.format(now);
        //获取所有场所码
        List<Long> pids = objectMapper.convertValue(placeCodeClient.getAllPids().getData(), List.class);
        //对于每个场所
        for (Long pid : pids) {
            // 获取前一天到过该场所的人员
            List<Long> uids = objectMapper.convertValue(placeCodeClient.getRecordByPid(pid, oneDayAgoFormatted, nowFormatted).getData(), List.class);
            uids = uids.stream().distinct().collect(Collectors.toList()); //去重
            int totalPersons = uids.size();  // 获取总人数

            // 使用 LambdaQueryWrapper 构建查询条件
            LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(TubeInfo::getKind, 0)
                    .eq(TubeInfo::getResult, 1)
                    .apply("DATE(created_at) = {0}", oneDayAgo);
            // 调用 MyBatis-Plus 的 selectList 方法进行查询
            List<Long> tubeids =  getTubeIds(queryWrapper);


            LambdaQueryWrapper<NucleicAcidTest> queryWrapper2 = Wrappers.lambdaQuery();
            queryWrapper2.in(NucleicAcidTest::getTubeid, tubeids);
            // 获取阳性人数
            List<Long> positiveUids = nucleicAcidTestMapper.selectList(queryWrapper2)
                    .stream()
                    .map(NucleicAcidTest::getUid)
                    .collect(Collectors.toList());

            Set<Long> positivePersons = uids.stream()
                    .filter(positiveUids::contains)
                    .collect(Collectors.toSet());

            int positiveCount = positivePersons.size();
            String risk = riskCalculationContext.calculateRiskLevel(totalPersons, positiveCount);
            placeCodeClient.setPlaceRisk(pid, risk);
        }
    }


    /**
     * 根据令牌添加核酸检测记录
     * @param tid 测试ID
     * @param uid 用户ID
     * @param acidTestRecordDto 核酸检测记录数据传输对象
     */
    @Override
    public void addNucleicAcidTestRecordByToken(long tid, long uid, AddNucleicAcidTestRecordDto acidTestRecordDto) {
        // 获取核酸检测类型
        int kind = acidTestRecordDto.getKind();
        // 获取试管ID
        Long tubeid = acidTestRecordDto.getTubeid();
        // 获取检测地址
        String testAddress = acidTestRecordDto.getTest_address();

        // 如果为单管类型，将该uid三天内的其他核酸检测记录标记为已复检状态，否则不处理
        if (kind == 0) {
            // 计算三天前的日期
            Date oneDayAgo = new Date(System.currentTimeMillis() - 3L * 24 * 60 * 60 * 1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String threeDayAgoFormatted = dateFormat.format(oneDayAgo);

            // 使用 LambdaUpdateWrapper 构建更新条件
            LambdaUpdateWrapper<NucleicAcidTest> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(NucleicAcidTest::getReTest, true)
                    .eq(NucleicAcidTest::getUid, uid)
                    .ge(NucleicAcidTest::getCreatedAt, threeDayAgoFormatted)
                    .eq(NucleicAcidTest::getReTest, false);
            // 调用 MyBatis-Plus 的 update 方法进行更新
            nucleicAcidTestMapper.update(null, updateWrapper);
        }

        // 构建核酸检测记录实体
        NucleicAcidTest nucleicAcidTest = NucleicAcidTest.builder()
                .uid(uid)
                .tid(tid)
                .tubeid(tubeid)
                .testAddress(testAddress)
                .reTest(0)
                .build();
        nucleicAcidTestMapper.insert(nucleicAcidTest); // 插入核酸检测记录

        // 构建查询条件，检查是否存在符合条件的试管信息
        LambdaQueryWrapper<TubeInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(TubeInfo::getTubeid, tubeid)
                .eq(TubeInfo::getKind, kind);
        // 执行查询，判断是否存在符合条件的数据
        boolean exists = tubeInfoMapper.selectCount(queryWrapper) > 0;

        if (!exists) {
            // 构建试管信息实体
            TubeInfo tubeInfo = TubeInfo.builder()
                    .tubeid(tubeid)
                    .kind(kind)
                    .result(2) // 未出
                    .testingOrganization(testAddress) // 设置检测机构
                    .build();
            // 插入试管信息
            tubeInfoMapper.insert(tubeInfo);
        }
    }

    /**
     * 根据ID添加核酸检测记录
     * @param tid 测试ID
     * @param acidTestRecordByIDDto 根据ID添加核酸检测记录的数据传输对象
     */
    @Override
    public void addNucleicAcidTestRecordByID(long tid, AddNucleicAcidTestRecordByIDDto acidTestRecordByIDDto) {
        // 通过用户客户端根据身份证号获取用户信息
        Result<?> result = userClient.getUserByID(acidTestRecordByIDDto.getIdentity_card());
        // 创建ObjectMapper对象，用于JSON数据处理
        ObjectMapper objectMapper = new ObjectMapper();
        // 将获取到的用户信息结果数据转换为UserInfoVo对象
        UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);
        // 获取用户的UID
        Long uid = userInfoVo.getUid();
        // 构造需要传递的参数，创建AddNucleicAcidTestRecordDto对象
        AddNucleicAcidTestRecordDto acidTestRecordDto = new AddNucleicAcidTestRecordDto();
        // 根据属性名进行匹配，将acidTestRecordByIDDto对象的属性值复制到acidTestRecordDto对象中
        BeanUtils.copyProperties(acidTestRecordByIDDto, acidTestRecordDto);

        // 调用根据令牌添加核酸检测记录的方法
        addNucleicAcidTestRecordByToken(tid, uid, acidTestRecordDto);
    }


    /**
     * 将 Date 对象转换为 LocalDateTime 对象
     * @param date 要转换的 Date 对象
     * @return 转换后的 LocalDateTime 对象
     */
    private LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        // 将 Date 对象转换为 Instant 对象
        Instant instant = date.toInstant();
        // 获取系统默认的时区
        ZoneId zoneId = ZoneId.systemDefault();
        // 将 Instant 对象转换为 ZonedDateTime 对象
        // 并从 ZonedDateTime 对象中提取出 LocalDateTime 对象
        return instant.atZone(zoneId).toLocalDateTime();
    }
}
