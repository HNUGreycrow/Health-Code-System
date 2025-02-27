package org.software.code.controller;

import org.software.code.common.consts.FSMConst;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.dto.TranscodingEventsDto;
import org.software.code.dto.UidInputDto;
import org.software.code.service.HealthCodeService;
import org.software.code.vo.HealthQRCodeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
// 该注解用于开启Spring的校验机制，对请求参数进行校验
@Validated
// 该注解用于将该类标记为一个RESTful风格的控制器，处理HTTP请求并返回JSON数据
@RestController
// 该注解用于指定该控制器处理的请求路径的基础部分，即所有请求路径都以"/health-code"开头
@RequestMapping("/health-code")
public class HealthCodeInternalController {
    // 该注解用于自动注入HealthCodeService实例，实现依赖注入
    @Resource
    private HealthCodeService healthCodeService;

    /**
     * 申请健康码的接口
     * @param request 包含用户唯一标识的请求体，使用UidInputDto封装
     * @return 成功申请健康码的结果
     */
    @PostMapping("/applyHealthCode")
    public Result<?> applyHealthCode(@Valid @RequestBody UidInputDto request) {
        // 从请求体中获取用户唯一标识
        long uid = request.getUid();
        // 调用健康码服务的申请健康码方法
        healthCodeService.applyHealthCode(uid);
        // 返回成功结果
        return Result.success();
    }

    /**
     * 获取健康码的接口
     * @param uid 用户唯一标识，通过请求参数传递，不能为空
     * @return 包含健康码信息的结果
     */
    @GetMapping("/getHealthCode")
    public Result<?> getHealthCode(@RequestParam(name = "uid") @NotNull(message = "uid不能为空") Long uid) {
        // 调用健康码服务的获取健康码方法，获取健康码视图对象
        HealthQRCodeVo healthQRCodeVo = healthCodeService.getHealthCode(uid);
        // 将健康码视图对象封装到结果中返回
        return Result.success(healthQRCodeVo);
    }

    /**
     * 转码健康码事件的接口
     * @param request 包含用户唯一标识和事件类型的请求体，使用TranscodingEventsDto封装
     * @return 转码成功的结果
     */
    @PatchMapping("/transcodingHealthCodeEvents")
    public Result<?> transcodingHealthCodeEvents(@Valid @RequestBody TranscodingEventsDto request) {
        // 从请求体中获取用户唯一标识
        long uid = request.getUid();
        // 从请求体中获取事件类型
        int event = request.getEvent();
        // 定义健康码事件枚举类型变量
        FSMConst.HealthCodeEvent healthCodeEvent;
        // 根据事件类型的值进行判断，确定具体的健康码事件枚举类型
        switch (event) {
            case 0:
                // 当事件类型为0时，将健康码事件设置为强制变绿
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_GREEN;
                break;
            case 1:
                // 当事件类型为1时，将健康码事件设置为强制变黄
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_YELLOW;
                break;
            case 2:
                // 当事件类型为2时，将健康码事件设置为强制变红
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_RED;
                break;
            default:
                // 如果事件类型的值不在0、1、2范围内，抛出业务异常
                throw new BusinessException(ExceptionEnum.HEALTH_CODE_EVENT_INVALID);
        }
        // 调用健康码服务的转码健康码事件方法
        healthCodeService.transcodingHealthCodeEvents(uid, healthCodeEvent);
        // 返回成功结果
        return Result.success();
    }
}