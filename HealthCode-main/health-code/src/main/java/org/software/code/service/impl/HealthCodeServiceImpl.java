package org.software.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.client.UserClient;
import org.software.code.common.consts.FSMConst;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.*;
import org.software.code.entity.AppealLog;
import org.software.code.entity.HealthCode;
import org.software.code.mapper.AppealLogMapper;
import org.software.code.mapper.HealthCodeMapper;
import org.software.code.service.HealthCodeService;
import org.software.code.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 健康码服务实现类，实现了 {@link HealthCodeService} 接口中定义的各种健康码相关业务操作方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class HealthCodeServiceImpl implements HealthCodeService {
    private static final Logger logger = LogManager.getLogger(HealthCodeServiceImpl.class);

    /**
     * 注入状态机服务，用于处理健康码的状态转换。
     */
    @Resource
    private StateMachineService<FSMConst.HealthCodeColor, FSMConst.HealthCodeEvent> stateMachineService;

    /**
     * 注入用户客户端，用于与用户相关的服务进行交互。
     */
    @Resource
    private UserClient userClient;

    /**
     * 注入健康码映射器，用于操作健康码相关的数据库表。
     */
    @Resource
    private HealthCodeMapper healthCodeMapper;

    @Resource
    private AppealLogMapper appealLogMapper;

    /**
     * 根据用户 ID 查询对应的健康码信息。
     *
     * <p>该方法使用 MyBatis-Plus 的 LambdaQueryWrapper 构建查询条件，根据传入的用户 ID 精确匹配数据库中的健康码记录。
     * 如果在数据库中找到了对应的健康码记录，则返回该健康码对象；如果未找到，则记录错误日志并抛出业务异常。</p>
     *
     * @param uid 用户的唯一标识符，用于在数据库中查找对应的健康码信息。
     * @return 与传入用户 ID 对应的健康码对象。
     * @throws BusinessException 当数据库中未找到与传入用户 ID 对应的健康码记录时抛出该异常，
     *                           异常信息由 {@link ExceptionEnum#HEALTH_CODE_NOT_FIND} 定义。
     */
    private HealthCode findHealthCodeByUid(long uid) {
        LambdaQueryWrapper<HealthCode> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HealthCode::getUid, uid);
        HealthCode healthCode = healthCodeMapper.selectOne(lambdaQueryWrapper);
        if (healthCode == null) {
            logger.error("Health code not found for UID: {}", uid);
            throw new BusinessException(ExceptionEnum.HEALTH_CODE_NOT_FIND);
        }
        return healthCode;
    }

    /**
     * 根据用户唯一标识申请健康码。
     *
     * @param uid 用户唯一标识
     */
    @Override
    public void applyHealthCode(long uid) {
        // 根据uid查询健康码
        HealthCode healthCode = findHealthCodeByUid(uid);
    }

    /**
     * 根据用户唯一标识获取健康码二维码信息。
     *
     * @param uid 用户唯一标识
     * @return 健康码二维码信息对象
     */
    @Override
    public HealthQRCodeVo getHealthCode(long uid) {
        // 根据uid查询健康码
        HealthCode healthCode = findHealthCodeByUid(uid);

        HealthQRCodeVo healthQRCodeVo = new HealthQRCodeVo();
        healthQRCodeVo.setStatus(healthCode.getColor());
        healthQRCodeVo.setQrcode_token(JWTUtil.generateJWToken(uid, 60000));
        return healthQRCodeVo;
    }

    /**
     * 根据用户 ID（uid）获取对应的健康码信息，利用状态机处理健康码事件，更新健康码的颜色状态，并将更新后的颜色信息保存到数据库中
     *
     * @param uid 用户唯一标识
     * @param event 健康码事件
     */
    @Override
    public void transcodingHealthCodeEvents(long uid, FSMConst.HealthCodeEvent event) {
        // 根据uid查询健康码
        HealthCode healthCode = findHealthCodeByUid(uid);

        String stateMachineId = String.valueOf(healthCode.getUid()); // 将用户 ID 转换为字符串，作为状态机的唯一标识。
        // 根据状态机 ID 获取对应的状态机实例。状态机的泛型参数
        StateMachine<FSMConst.HealthCodeColor, FSMConst.HealthCodeEvent> stateMachine = stateMachineService.acquireStateMachine(stateMachineId);
        stateMachine.stopReactively().block(); // 停止状态机的响应式处理，并阻塞直到停止操作完成。
        stateMachine.getStateMachineAccessor() // 获取状态机的访问器，并对所有区域执行重置操作。
                // 为了确保状态机的状态与数据库中存储的健康码颜色状态保持一致，从而保证状态机在处理后续事件时能够基于正确的初始状态进行状态转换。
                .doWithAllRegions(access -> access.resetStateMachineReactively(
                        new DefaultStateMachineContext<>(FSMConst.HealthCodeColor.values()[healthCode.getColor()], null, null, null)).block());
        // 使用 DefaultStateMachineContext 对象重置状态机的状态，将状态机的初始状态设置为从数据库中获取的健康码颜色状态
        stateMachine.startReactively().block(); // 启动状态机的响应式处理，并阻塞直到启动操作完成
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build())).blockFirst();

        FSMConst.HealthCodeColor newColor = stateMachine.getState().getId(); // 向状态机发送一个事件消息，触发状态转换。
        int color = newColor.ordinal(); // 获取新颜色状态在枚举中的索引值。
        healthCode.setColor(color);

        // 更新健康码颜色
        HealthCode updateHealthCode = new HealthCode();
        updateHealthCode.setUid(uid);
        updateHealthCode.setColor(color);
        healthCodeMapper.updateById(updateHealthCode);

        stateMachine.stopReactively().block();
        // 调用 stateMachineService 的 releaseStateMachine 方法，释放状态机资源。
        stateMachineService.releaseStateMachine(stateMachineId);
    }

    /**
     * 根据用户唯一标识和申请码数据传输对象进行健康码申请操作。
     *
     * @param uid 用户唯一标识
     * @param applyCodeDto 申请码数据传输对象
     */
    @Override
    public void applyCode(long uid, ApplyCodeDto applyCodeDto) {
        UserInfoRequestDto userInfoRequestDto = new UserInfoRequestDto();
        // 根据属性名进行匹配，复制对应值
        BeanUtils.copyProperties(applyCodeDto, userInfoRequestDto);
        userInfoRequestDto.setUid(uid);

        userClient.addUserInfo(userInfoRequestDto);
        HealthCode healthCode = new HealthCode();
        healthCode.setUid(uid);
        healthCode.setColor(0);
        healthCodeMapper.insert(healthCode);
    }

    /**
     * 根据用户唯一标识获取健康码信息。
     *
     * @param uid 用户唯一标识
     * @return 健康码信息对象
     */
    @Override
    public GetCodeVo getCode(long uid) {
        // 根据uid查询健康码
        HealthCode healthCode = findHealthCodeByUid(uid);

        Result<?> result = userClient.getUserByUID(uid);
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);

        GetCodeVo getCodeVo = new GetCodeVo();
        getCodeVo.setToken(JWTUtil.generateJWToken(uid, 60000));
        getCodeVo.setStatus(healthCode.getColor());
        getCodeVo.setName(userInfoVo.getName());
        return getCodeVo;
    }

    /**
     * 根据身份证号获取健康码详细信息。
     *
     * @param identityCard 身份证号
     * @return 健康码详细信息对象
     */
    @Override
    public HealthCodeInfoVo getHealthCodeInfo(String identityCard) {
        Result<?> result = userClient.getUserByID(identityCard);
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);

        // 根据uid查询健康码
        long uid = userInfoVo.getUid();
        HealthCode healthCode = findHealthCodeByUid(uid);

        HealthCodeInfoVo healthCodeInfoVo = new HealthCodeInfoVo();
        healthCodeInfoVo.setUid(uid);
        healthCodeInfoVo.setName(userInfoVo.getName());
        healthCodeInfoVo.setStatus(healthCode.getColor());
        healthCodeInfoVo.setIdentityCard(identityCard);
        return healthCodeInfoVo;
    }

    /**
     * 创建申诉记录
     * @param uid 用户ID
     * @param createAppealDto 申诉信息DTO
     */
    @Override
    public void createAppeal(long uid, CreateAppealDto createAppealDto) {
        // 查询用户信息
//        Result<?> user = userClient.getUserByUID(uid);
//        ObjectMapper objectMapper = new ObjectMapper();
//        UserInfoVo userInfoVo = objectMapper.convertValue(user.getData(), UserInfoVo.class);

        // 创建申诉记录
        AppealLog appealLog = new AppealLog();
        // 设置申诉记录的用户 ID
        appealLog.setUid(uid);
        // 设置申诉原因
        appealLog.setAppealReason(createAppealDto.getAppealReason());
        // 设置申诉材料
        appealLog.setAppealMaterials(createAppealDto.getAppealMaterials());
        // 设置申诉状态为未处理（0 表示未处理）
        appealLog.setAppealStatus(0);
        // 将申诉记录插入数据库
        appealLogMapper.insert(appealLog);
    }

    /**
     * 获取申诉记录列表
     * @return 申诉记录视图对象列表
     */
    @Override
    public List<AppealLogVo> getAppealList() {
        // 查询所有申诉记录
        List<AppealLog> appealLogList = appealLogMapper.selectList(new LambdaQueryWrapper<>());
        // 将查询到的申诉记录列表转换为申诉记录视图对象列表
        return appealLogList.stream().map(appealLog -> {
            AppealLogVo appealLogVo = new AppealLogVo();
            // 将申诉记录的属性复制到申诉记录视图对象中
            BeanUtils.copyProperties(appealLog, appealLogVo);

            // 根据申诉记录中的用户 ID 查询用户信息
            // 查询用户信息
            Result<?> user = userClient.getUserByUID(appealLog.getUid());
            HealthCode userHealthCode = healthCodeMapper.selectById(appealLog.getUid());

            // 将用户信息复制到用户信息视图对象中
            ObjectMapper objectMapper = new ObjectMapper();
            UserInfoVo userInfoVo = objectMapper.convertValue(user.getData(), UserInfoVo.class);

            // 设置用户id
            appealLogVo.setUid(userInfoVo.getUid());
            // 设置申诉记录视图对象中的用户姓名
            appealLogVo.setUserName(userInfoVo.getName());
            // 设置申诉记录视图对象中的用户身份证号
            appealLogVo.setIdentityCard(userInfoVo.getIdentityCard());
            // 设置当前健康码颜色
            appealLogVo.setHealthCodeColor(userHealthCode.getColor());
            return appealLogVo;
        }).collect(Collectors.toList());
    }

    /**
     * 更新申诉记录
     * @param updateAppealDto 更新申诉记录的数据传输对象
     */
    @Override
    public void updateAppeal(UpdateAppealDto updateAppealDto) {
        // 创建一个新的申诉记录对象
        AppealLog appealLog = new AppealLog();
        // 将更新申诉记录的数据传输对象的属性复制到申诉记录对象中
        BeanUtils.copyProperties(updateAppealDto, appealLog);
        // 调用申诉记录映射器的 updateById 方法更新数据库中的申诉记录，并返回受影响的行数
        int row = appealLogMapper.updateById(appealLog);
        // 若更新失败则抛出异常
        if (row <= 0) {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_UPDATE_FAIL);
        }
    }

    /**
     * 根据申诉记录 ID 删除申诉记录
     * @param appealId 申诉记录的 ID
     */
    @Override
    public void deleteAppealById(Integer appealId) {
        // 调用申诉记录映射器的 deleteById 方法，根据传入的申诉记录 ID 删除数据库中的申诉记录，并返回受影响的行数
        int row = appealLogMapper.deleteById(appealId);
        // 如果受影响的行数小于等于 0，说明删除失败，抛出业务异常
        if (row <= 0) {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_DELETE_FAIL);
        }
    }

    /**
     * 审核申诉记录，并根据审核结果更新申诉状态和健康码颜色
     * @param reviewAppealDto 审核申诉的数据传输对象
     */
    @Override
    public void reviewAppeal(ReviewAppealDto reviewAppealDto) {
        // 创建一个新的申诉记录对象
        AppealLog appealLog = new AppealLog();
        // 设置申诉记录的 ID，从审核申诉的数据传输对象中获取
        appealLog.setAppealId(reviewAppealDto.getAppealId());
        // 设置申诉记录的状态为已处理，状态值为 1 表示已处理
        appealLog.setAppealStatus(1); // 表示已处理
        // 调用申诉记录映射器的 updateById 方法，根据申诉记录的 ID 更新数据库中的申诉记录状态
        appealLogMapper.updateById(appealLog);

        // 获取转码事件（0：转绿码，1：转黄码，2：转红码）
        FSMConst.HealthCodeEvent healthCodeEvent;
        // 根据审核申诉的数据传输对象中的健康码变更事件类型进行判断
        switch (reviewAppealDto.getHealthCodeChangeEvent()) {
            case 0:
                // 如果事件类型为 0，将转码事件设置为强制转绿码
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_GREEN;
                break;
            case 1:
                // 如果事件类型为 1，将转码事件设置为强制转黄码
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_YELLOW;
                break;
            case 2:
                // 如果事件类型为 2，将转码事件设置为强制转红码
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_RED;
                break;
            default:
                // 如果事件类型不是 0、1、2 中的任何一个，抛出业务异常，表示健康码事件无效
                throw new BusinessException(ExceptionEnum.HEALTH_CODE_EVENT_INVALID);
        }

        // 查询提出申诉用户的 uid，根据审核申诉的数据传输对象中的申诉记录 ID 查询对应的申诉记录
        appealLog = appealLogMapper.selectById(reviewAppealDto.getAppealId());
        // 调用转码函数，根据查询到的用户 ID 和转码事件更新健康码的颜色状态
        transcodingHealthCodeEvents(appealLog.getUid(), healthCodeEvent);
    }
}