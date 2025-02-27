package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.CreatePlaceCodeRequestDto;
import org.software.code.dto.PidInputDto;
import org.software.code.dto.PidTokenInputDto;
import org.software.code.service.PlaceCodeService;
import org.software.code.vo.PlaceCodeInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 场所码相关业务的控制器类，负责处理与场所码操作相关的 HTTP 请求，
 * 如扫描场所码、创建场所码、获取场所码列表等操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/place-code")
public class PlaceCodeController {
    // 注入场所码服务类，用于处理场所码相关的业务逻辑
    @Resource
    private PlaceCodeService placeCodeService;

    /**
     * 基于令牌扫描场所码的接口。
     * 该接口接收请求头中的 JWT Token 和包含场所码令牌的请求体，
     * 从令牌中提取用户 ID 和场所 ID，调用服务层方法进行场所码扫描操作。
     *
     * @param token  请求头中的 Authorization 字段，即 JWT Token，不能为 null
     * @param request 包含场所码令牌的请求体，经过 @Valid 注解验证
     * @return 操作成功的统一返回对象
     */
    @PostMapping("/scanCodeByToken")
    public Result<?> scanCodeByToken(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                     @Valid @RequestBody PidTokenInputDto request) {
        // 从请求体中的令牌提取场所 ID
        long pid = JWTUtil.extractID(request.getToken());
        // 从请求头的令牌提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用服务层方法进行场所码扫描
        placeCodeService.scanPlaceCode(uid, pid);
        return Result.success();
    }

    /**
     * 普通扫描场所码的接口。
     * 该接口接收请求头中的 JWT Token 和包含场所 ID 的请求体，
     * 从令牌中提取用户 ID，结合请求体中的场所 ID，调用服务层方法进行场所码扫描操作。
     *
     * @param token  请求头中的 authorization 字段，即 JWT Token，不能为 null
     * @param request 包含场所 ID 的请求体，经过 @Valid 注解验证
     * @return 操作成功的统一返回对象
     */
    @PostMapping("/scanCode")
    public Result<?> scanCode(@RequestHeader("authorization") @NotNull(message = "token不能为空") String token,
                              @Valid @RequestBody PidInputDto request) {
        // 从请求体中获取场所 ID
        long pid = request.getPid();
        // 从请求头的令牌提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用服务层方法进行场所码扫描
        placeCodeService.scanPlaceCode(uid, pid);
        return Result.success();
    }

    /**
     * 用户登录管理系统后，新建场所码的接口。
     * 该接口接收请求头中的 JWT Token 和包含创建场所码所需信息的请求体，
     * 从令牌中提取用户 ID，调用服务层方法创建场所码。
     *
     * @param token  请求头中的 Authorization 字段，即 JWT Token，不能为 null
     * @param request 包含创建场所码所需信息的请求体，经过 @Valid 注解验证
     * @return 操作成功的统一返回对象
     */
    @PostMapping("/placeCode")
    public Result<?> createPlaceCode(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                     @Valid @RequestBody CreatePlaceCodeRequestDto request) {
        // 从请求头的令牌提取用户 ID
        JWTUtil.extractID(token);
        // 调用服务层方法创建场所码
        placeCodeService.createPlaceCode(request);
        return Result.success();
    }

    /**
     * 用户登录管理系统后，获取所有场所码的接口。
     * 该接口接收请求头中的 JWT Token，从令牌中提取用户 ID，
     * 调用服务层方法获取所有场所码信息列表，并将结果封装在统一返回对象中返回。
     *
     * @param token  请求头中的 Authorization 字段，即 JWT Token，不能为 null
     * @return 包含场所码信息列表的统一返回对象
     */
    @GetMapping("/placeCode")
    public Result<?> getPlaceCodeList(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从请求头的令牌提取用户 ID
        JWTUtil.extractID(token);
        // 调用服务层方法获取场所码信息列表
        List<PlaceCodeInfoVo> placeInfoList = placeCodeService.getPlaceInfoList();
        return Result.success(placeInfoList);
    }

    /**
     * 对场所码状态进行反转操作的接口。
     * 该接口接收请求头中的 JWT Token 和包含场所 ID 的请求体，
     * 从令牌中提取用户 ID，结合请求体中的场所 ID，调用服务层方法对场所码状态进行反转。
     *
     * @param token  请求头中的 Authorization 字段，即 JWT Token，不能为 null
     * @param request 包含场所 ID 的请求体，经过 @Valid 注解验证
     * @return 操作成功的统一返回对象
     */
    @PatchMapping("/place_code_opposite")
    public Result<?> placeCodeOpposite(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                       @Valid @RequestBody PidInputDto request) {
        // 从请求头的令牌提取用户 ID
        JWTUtil.extractID(token);
        // 调用服务层方法对场所码状态进行反转
        placeCodeService.placeCodeOpposite(request.getPid());
        return Result.success();
    }
}