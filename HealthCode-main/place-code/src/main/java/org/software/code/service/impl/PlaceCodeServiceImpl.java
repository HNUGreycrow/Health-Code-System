package org.software.code.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.client.UserClient;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.dto.AddPlaceInputDto;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.CreatePlaceCodeRequestDto;
import org.software.code.entity.PlaceInfo;
import org.software.code.entity.PlaceMapping;
import org.software.code.mapper.PlaceInfoMapper;
import org.software.code.mapper.PlaceMappingMapper;
import org.software.code.service.PlaceCodeService;
import org.software.code.vo.AreaCodeVo;
import org.software.code.vo.GetPlaceVo;
import org.software.code.vo.PlaceCodeInfoVo;
import org.software.code.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PlaceCodeServiceImpl 是 PlaceCodeService 接口的实现类，
 * 负责处理场所码相关的具体业务逻辑，如场所添加、查询、场所码状态更新等操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class PlaceCodeServiceImpl implements PlaceCodeService {
    // 日志记录器，用于记录业务操作中的关键信息和错误信息
    private static final Logger logger = LogManager.getLogger(PlaceCodeServiceImpl.class);

    // 注入 PlaceInfo 表的 Mapper，用于操作 PlaceInfo 实体对应的数据库表
    @Resource
    private PlaceInfoMapper placeInfoMapper;

    // 注入 PlaceMapping 表的 Mapper，用于操作 PlaceMapping 实体对应的数据库表
    @Resource
    private PlaceMappingMapper placeMappingMapper;

    // 注入 UserClient，用于调用用户服务的远程接口
    @Resource
    UserClient userClient;

    /**
     * 添加一个新的场所。
     *
     * @param placeDto 包含场所信息的输入数据传输对象。
     * @return 新添加场所的唯一标识。
     */
    @Override
    public Long addPlace(AddPlaceInputDto placeDto) {
        // 创建 PlaceInfo 对象并复制输入数据
        PlaceInfo placeInfo = new PlaceInfo();
        BeanUtils.copyProperties(placeDto, placeInfo);

        // 调用用户服务获取地址编码对应的 ID
        Result<?> areaCode = userClient.getAreaCodeID(
                new AreaCodeDto(placeDto.getDistrict(), placeDto.getStreet(), placeDto.getCommunity()));
        ObjectMapper objectMapper = new ObjectMapper();
        AreaCodeVo areaCodeVo = objectMapper.convertValue(areaCode.getData(), AreaCodeVo.class);

        // 设置场所的地址编码 ID 和地址
        placeInfo.setAreaId(areaCodeVo.getId());
        placeInfo.setAddress(placeInfo.getAddress());
        // 默认开启场所状态
        placeInfo.setStatus(true);
        // 生成唯一的场所 ID
        long snowflakePid = IdUtil.getSnowflake().nextId();
        placeInfo.setPid(snowflakePid);
        // 将场所信息插入数据库
        placeInfoMapper.insert(placeInfo);
        return placeInfo.getPid();
    }

    /**
     * 获取所有场所的信息。
     *
     * @return 包含所有场所信息的列表，每个场所信息封装在 GetPlaceVo 对象中。
     */
    @Override
    public List<GetPlaceVo> getPlaces() {
        // 查询所有场所信息
        List<PlaceInfo> placeInfoList = placeInfoMapper.selectList(new QueryWrapper<>());
        return placeInfoList.stream()
                .map(placeInfo -> {
                    // 创建 GetPlaceVo 对象并复制场所信息
                    GetPlaceVo getPlaceVo = new GetPlaceVo();
                    BeanUtils.copyProperties(placeInfo, getPlaceVo);
                    // 调用用户服务获取地址编码信息
                    Result<?> areaCodeResult = userClient.getAreaCodeByID(placeInfo.getAreaId());
                    ObjectMapper objectMapper = new ObjectMapper();
                    AreaCodeVo areaCodeVo = objectMapper.convertValue(areaCodeResult.getData(), AreaCodeVo.class);
                    // 调用用户服务获取用户信息
                    Result<?> result = userClient.getUserByUID(placeInfo.getUid());
                    UserInfoVo userInfo = objectMapper.convertValue(result.getData(), UserInfoVo.class);
                    // 设置场所管理员的身份证和手机号
                    getPlaceVo.setIdentityCard(userInfo.getIdentityCard());
                    getPlaceVo.setPhoneNumber(userInfo.getPhoneNumber());
                    return getPlaceVo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据场所 ID 和时间范围获取相关记录。
     *
     * @param pid 场所的唯一标识。
     * @param startTime 记录查询的开始时间。
     * @param endTime 记录查询的结束时间。
     * @return 符合条件的记录 ID 列表。
     */
    @Override
    public List<Long> getRecordByPid(long pid, Date startTime, Date endTime) {
        // 构建查询条件
        LambdaQueryWrapper<PlaceMapping> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .select(PlaceMapping::getUid) // 查询 uid 字段
                .eq(PlaceMapping::getPid, pid)
                .between(PlaceMapping::getTime, startTime, endTime);
        // 执行查询并将结果转换为 Long 类型列表
        return placeMappingMapper.selectObjs(lambdaQueryWrapper).stream()
                .map(obj -> (Long) obj)
                .collect(Collectors.toList());
    }

    /**
     * 更改场所码的状态。
     *
     * @param pid 场所的唯一标识。
     * @param status 要设置的场所码状态。
     */
    @Override
    public void oppositePlaceCode(long pid, boolean status) {
        // 创建 PlaceInfo 对象并设置场所 ID 和状态
        PlaceInfo placeInfo = new PlaceInfo();
        placeInfo.setPid(pid);
        placeInfo.setStatus(status);
        // 更新场所信息
        placeInfoMapper.updateById(placeInfo);
    }

    /**
     * 处理扫描场所码的操作。
     *
     * @param uid 扫描场所码的用户的唯一标识。
     * @param pid 被扫描场所的唯一标识。
     */
    @Override
    public void scanPlaceCode(long uid, long pid) {
        // 创建 PlaceMapping 对象并设置用户 ID、场所 ID 和扫描时间
        PlaceMapping placeMapping = new PlaceMapping();
        placeMapping.setPid(pid);
        placeMapping.setUid(uid);
        placeMapping.setTime(new Date());
        // 将扫描记录插入数据库
        placeMappingMapper.insert(placeMapping);
    }

    /**
     * 根据用户列表和时间范围获取相关场所的信息。
     *
     * @param uidList 用户唯一标识的列表。
     * @param startTime 查询的开始时间。
     * @param endTime 查询的结束时间。
     * @return 符合条件的场所 ID 列表。
     */
    @Override
    public List<Long> getPlacesByUserList(List<Long> uidList, Date startTime, Date endTime) {
        // 构建查询条件
        LambdaQueryWrapper<PlaceMapping> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PlaceMapping::getUid, uidList)
                .between(PlaceMapping::getTime, startTime, endTime)
                .select(PlaceMapping::getPid);
        // 执行查询并将结果转换为 Long 类型列表
        return placeMappingMapper.selectObjs(queryWrapper).stream()
                .map(obj -> (Long) obj)
                .collect(Collectors.toList());
    }

    /**
     * 创建一个新的场所码。
     *
     * @param request 包含创建场所码所需信息的请求数据传输对象。
     */
    @Override
    public void createPlaceCode(CreatePlaceCodeRequestDto request) {
        // 调用用户服务根据身份证号码获取用户信息
        Result<?> result = userClient.getUserByID(request.getIdentity_card());
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);

        // 创建 PlaceInfo 对象并设置场所 ID、用户 ID 和场所名称
        PlaceInfo placeInfo = new PlaceInfo();
        long snowflakePid = IdUtil.getSnowflake().nextId();
        placeInfo.setPid(snowflakePid);
        placeInfo.setUid(userInfoVo.getUid());
        placeInfo.setPlaceName(request.getName());

        // 调用用户服务获取地址编码对应的 ID
        Result<?> areaCode = userClient.getAreaCodeID(
                new AreaCodeDto(request.getDistrict_id(), request.getStreet_id(), request.getCommunity_id()));
        AreaCodeVo areaCodeVo = objectMapper.convertValue(areaCode.getData(), AreaCodeVo.class);

        // 设置场所的地址编码 ID 和地址
        placeInfo.setAreaId(areaCodeVo.getId());
        placeInfo.setAddress(request.getAddress());
        // 默认开启场所状态
        placeInfo.setStatus(true);
        // 将场所信息插入数据库
        placeInfoMapper.insert(placeInfo);
    }

    /**
     * 获取所有场所码的详细信息。
     *
     * @return 包含所有场所码详细信息的列表，每个场所码信息封装在 PlaceCodeInfoVo 对象中。
     */
    @Override
    public List<PlaceCodeInfoVo> getPlaceInfoList() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 查询所有场所信息
        List<PlaceInfo> placeInfoList = placeInfoMapper.selectList(new QueryWrapper<>());
        return placeInfoList.stream()
                .map(placeInfo -> {
                    // 创建 PlaceCodeInfoVo 对象并复制场所信息
                    PlaceCodeInfoVo placeCodeInfoVo = new PlaceCodeInfoVo();
                    BeanUtils.copyProperties(placeInfo, placeCodeInfoVo);
                    // 调用用户服务获取地址编码信息
                    Result<?> areaCodeResult = userClient.getAreaCodeByID(placeInfo.getAreaId());
                    AreaCodeVo areaCodeVo = objectMapper.convertValue(areaCodeResult.getData(), AreaCodeVo.class);
                    // 设置地址编码信息
                    placeCodeInfoVo.setCommunityId(areaCodeVo.getCommunity());
                    placeCodeInfoVo.setDistrictId(areaCodeVo.getDistrict());
                    placeCodeInfoVo.setStreetId(areaCodeVo.getStreet());
                    // 调用用户服务获取用户信息
                    Result<?> userResult = userClient.getUserByUID(placeInfo.getUid());
                    UserInfoVo userInfoVo = objectMapper.convertValue(userResult.getData(), UserInfoVo.class);
                    // 设置场所管理员的身份证信息
                    placeCodeInfoVo.setIdentityCard(userInfoVo.getIdentityCard());
                    return placeCodeInfoVo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 反转指定场所码的状态。
     *
     * @param pid 场所的唯一标识。
     */
    @Override
    public void placeCodeOpposite(Long pid) {
        // 根据场所 ID 查询场所信息
        PlaceInfo placeInfo = placeInfoMapper.selectById(pid);
        if (placeInfo == null) {
            // 记录错误日志并抛出业务异常
            logger.error("Place code not found for PID: {}", pid);
            throw new BusinessException(ExceptionEnum.PLACE_CODE_NOT_FIND);
        }
        // 获取当前场所状态
        Boolean status = placeInfo.getStatus();
        // 创建 PlaceInfo 对象并设置场所 ID 和反转后的状态
        PlaceInfo updatePlaceInfo = new PlaceInfo();
        updatePlaceInfo.setPid(pid);
        updatePlaceInfo.setStatus(!status);
        // 更新场所信息
        placeInfoMapper.updateById(updatePlaceInfo);
    }

    /**
     * 获取所有场所的唯一标识。
     *
     * @return 包含所有场所唯一标识的列表。
     */
    @Override
    public List<Long> getAllPids() {
        // 构建查询条件，只查询场所 ID
        LambdaQueryWrapper<PlaceInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(PlaceInfo::getPid);
        // 执行查询并将结果转换为 Long 类型列表
        return placeInfoMapper.selectObjs(lambdaQueryWrapper).stream()
                .map(obj -> (Long) obj)
                .collect(Collectors.toList());
    }
}