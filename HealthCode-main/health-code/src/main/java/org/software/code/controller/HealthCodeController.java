package org.software.code.controller;

import org.software.code.common.consts.FSMConst;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.*;
import org.software.code.service.HealthCodeService;
import org.software.code.vo.AppealLogVo;
import org.software.code.vo.GetCodeVo;
import org.software.code.vo.HealthCodeInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * HealthCodeController 类是一个 RESTful 控制器，用于处理与健康码相关的 HTTP 请求。
 * 该控制器提供了健康码的申请、获取、信息查询以及转码等功能的接口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/health-code")
public class HealthCodeController {

    /**
     * 自动注入 HealthCodeService 实例，用于处理健康码相关的业务逻辑。
     */
    @Resource
    private HealthCodeService healthCodeService;

    /**
     * applyCode 方法用于处理用户在申请健康码页面（个人信息填写页面）提交的健康码申请请求。
     *
     * @param token 用户请求头中的授权令牌，不能为空。
     * @param input 包含用户申请健康码所需信息的 ApplyCodeDto 对象，需要进行数据验证。
     * @return 若申请成功，返回一个表示成功的 Result 对象。
     */
    @PostMapping("/applyCode")
    public Result<?> applyCode(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                               @Valid @RequestBody ApplyCodeDto input) {
        // 从授权令牌中提取用户的唯一标识 uid
        long uid = JWTUtil.extractID(token);
        // 调用健康码服务的 applyCode 方法处理健康码申请业务
        healthCodeService.applyCode(uid, input);
        // 返回表示成功的 Result 对象
        return Result.success();
    }

    /**
     * getCode 方法用于处理用户登录后在首页获取健康码状态的请求。
     * 首页展示的健康码状态包括绿码、黄码和红码。每次返回首页都需要重新获取，且每分钟自动刷新一次。
     *
     * @param token 用户请求头中的授权令牌，不能为空。
     * @return 包含健康码状态信息的 GetCodeVo 对象封装在 Result 对象中返回。
     */
    @GetMapping("/getCode")
    public Result<?> getCode(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从授权令牌中提取用户的唯一标识 uid
        long uid = JWTUtil.extractID(token);
        // 调用健康码服务的 getCode 方法获取健康码状态信息
        GetCodeVo getCodeVo = healthCodeService.getCode(uid);
        // 将获取到的健康码状态信息封装在 Result 对象中返回
        return Result.success(getCodeVo);
    }

    /**
     * getHealthCodeInfo 方法用于处理用户登录管理系统后，在转码管理页面通过身份证号查询用户健康码信息的请求。
     *
     * @param token 用户请求头中的授权令牌，不能为空。
     * @param identity_card 用户的身份证号，作为查询健康码信息的依据，不能为空。
     * @return 包含用户健康码信息的 HealthCodeInfoVo 对象封装在 Result 对象中返回。
     */
    @GetMapping("/health_code")
    public Result<?> getHealthCodeInfo(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                       @RequestParam(name = "identity_card") @NotNull(message = "identity_card不能为空") String identity_card) {
        // 从授权令牌中提取用户的唯一标识 uid
        JWTUtil.extractID(token);
        // 调用健康码服务的 getHealthCodeInfo 方法获取用户健康码信息
        HealthCodeInfoVo healthCodeInfoVo = healthCodeService.getHealthCodeInfo(identity_card);
        // 将获取到的用户健康码信息封装在 Result 对象中返回
        return Result.success(healthCodeInfoVo);
    }

    /**
     * transcodingEvents 方法用于处理用户登录管理系统后，在转码管理页面通过身份证对用户进行健康码转码的请求。
     *
     * @param token 用户请求头中的授权令牌，不能为空。
     * @param request 包含转码事件信息的 TranscodingEventsDto 对象，需要进行数据验证。
     * @return 若转码成功，返回一个表示成功的 Result 对象。
     * @throws BusinessException 当转码事件无效时，抛出 BusinessException 异常。
     */
    @PostMapping("/transcodingEvents")
    public Result<?> transcodingEvents(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                       @Valid @RequestBody TranscodingEventsDto request) {
        // TODO: 根据转码事件，修改健康码状态
        // FIXME: 健康码创建时应该默认为绿色，不应为null
        // WARNING: 这个方法可能会抛出异常，需要进行异常处理
        // 获取请求中的用户唯一标识 uid
        long uid = request.getUid();
        // 获取请求中的转码事件编号
        int event = request.getEvent();
        // 定义健康码事件枚举类型变量
        FSMConst.HealthCodeEvent healthCodeEvent;
        // 根据转码事件编号确定具体的健康码事件
        switch (event) {
            case 0:
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_GREEN;
                break;
            case 1:
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_YELLOW;
                break;
            case 2:
                healthCodeEvent = FSMConst.HealthCodeEvent.FORCE_RED;
                break;
            default:
                // 若转码事件编号无效，抛出 BusinessException 异常
                throw new BusinessException(ExceptionEnum.HEALTH_CODE_EVENT_INVALID);
        }
        // 从授权令牌中提取用户的唯一标识 uid
        JWTUtil.extractID(token);
        // 调用健康码服务的 transcodingHealthCodeEvents 方法处理健康码转码业务
        healthCodeService.transcodingHealthCodeEvents(uid, healthCodeEvent);
        // 返回表示成功的 Result 对象
        return Result.success();
    }

    /**
     * 创建一个申诉记录
     *
     * @param token            管理员用户的认证令牌，用于身份验证，从请求头的 "Authorization" 字段获取，不能为空
     * @param createAppealDto  创建申诉记录所需的数据传输对象，包含申诉的相关信息，使用 @Valid 注解进行数据验证
     * @return 返回一个统一的结果对象，表示操作成功，不包含具体数据
     */
    @PostMapping("/appeal")
    public Result<?> createAppeal(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                  @Valid @RequestBody CreateAppealDto createAppealDto) {
        long uid = JWTUtil.extractID(token);
        healthCodeService.createAppeal(uid, createAppealDto);
        return Result.success();
    }

    /**
     * 获取所有申诉记录
     *
     * @param token 用户的认证令牌，用于身份验证，从请求头的 "Authorization" 字段获取，不能为空
     * @return 返回一个统一的结果对象，包含所有申诉记录的视图对象列表
     */
    @GetMapping("/appeal")
    public Result<?> getAppealList(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        JWTUtil.extractID(token);
        List<AppealLogVo> appealLogVoList = healthCodeService.getAppealList();
        return Result.success(appealLogVoList);
    }

    /**
     * 更新申诉信息，updateAppealDto 内不传递的字段不更新
     *
     * @param token            用户的认证令牌，用于身份验证，从请求头的 "Authorization" 字段获取，不能为空
     * @param updateAppealDto  需要更新的申诉信息的数据传输对象，字段为空表示不更新该字段
     */
    @PutMapping("/appeal")
    public Result<?> updateAppeal(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                  @RequestBody UpdateAppealDto updateAppealDto ) {
        JWTUtil.extractID(token); // 检查token
        healthCodeService.updateAppeal(updateAppealDto);
        return Result.success();
    }

    /**
     * 根据 appealId 删除申诉
     *
     * @param token     用户的认证令牌，用于身份验证，从请求头的 "Authorization" 字段获取，不能为空
     * @param appealId  要删除的申诉记录的唯一标识，通过请求参数传递
     */
    @DeleteMapping("/appeal")
    public Result<?> deleteAppealById(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                      @RequestParam(name = "appealId") Integer appealId) {
        JWTUtil.extractID(token); // 检查token
        healthCodeService.deleteAppealById(appealId);
        return Result.success();
    }

    /**
     * 处理申诉、并进行转码操作
     *
     * @param token            用户的认证令牌，用于身份验证，从请求头的 "Authorization" 字段获取，不能为空
     * @param reviewAppealDto  处理申诉所需的数据传输对象，包含处理申诉和转码所需的相关信息
     */
    @PostMapping("/appeal/review")
    public Result<?> reviewAppealLog(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                     @RequestBody ReviewAppealDto reviewAppealDto) {
        JWTUtil.extractID(token); // 检查token
        healthCodeService.reviewAppeal(reviewAppealDto);
        return Result.success();
    }

}