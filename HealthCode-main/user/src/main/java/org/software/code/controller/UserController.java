package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.*;
import org.software.code.service.UserService;
import org.software.code.vo.HealthCodeManagerVo;
import org.software.code.vo.NucleicAcidTestPersonnelVo;
import org.software.code.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户控制器类，处理与用户相关的各类 HTTP 请求，包括登录、信息修改、核酸检测人员管理、健康码管理人员管理等操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    // 注入 UserService 实例，用于调用用户相关的业务逻辑
    @Autowired
    private UserService userService;

    /**
     * 用户登录接口，使用 code 进行登录操作。
     *
     * @param input 包含登录所需 code 的输入数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody CodeInputDto input) {
        // 调用 UserService 的 userLogin 方法，传入 code 进行登录操作，获取 token
        UserLoginVo userLoginVo = userService.userLogin(input.getCode());
        // 返回包含 token 的成功结果
        return Result.success(userLoginVo);
    }

    /**
     * 测试环境下的用户登录接口，使用 code 进行登录操作。
     *
     * @param input 包含登录所需 code 的输入数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/login-test")
    public Result<?> login_test(@Valid @RequestBody CodeInputDto input) {
        // 调用 UserService 的 userLogin_test 方法，传入 code 进行登录操作，获取 token
        String token = userService.userLogin_test(input.getCode());
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 用户信息修改接口，需要提供有效的 token 和修改信息。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @param request 包含用户修改信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PutMapping("/userModify")
    public Result<?> userModify(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
        @Valid @RequestBody UserModifyDto request) {
        // 从 token 中提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用 UserService 的 userModify 方法，传入用户 ID 和修改信息进行用户信息修改
        userService.userModify(uid, request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 核酸检测人员登录接口。
     *
     * @param request 包含核酸检测人员登录信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/nucleicAcidsLogin")
    public Result<?> nucleicAcidsLogin(@Valid @RequestBody NucleicAcidsLoginDto request) {
        // 调用 UserService 的 nucleicAcidTestUserLogin 方法，传入登录信息进行核酸检测人员登录，获取 token
        String token = userService.nucleicAcidTestUserLogin(request);
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 健康码管理人员登录接口。
     *
     * @param request 包含健康码管理人员登录信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/managerLogin")
    public Result<?> managerLogin(@Valid @RequestBody ManagerLoginDto request) {
        // 调用 UserService 的 managerLogin 方法，传入登录信息进行健康码管理人员登录，获取 token
        String token = userService.managerLogin(request);
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 创建核酸检测人员信息接口，需要提供有效的 token 和核酸检测人员信息。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @param request 包含要创建的核酸检测人员信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PostMapping("/nucleic_acid")
    public Result<?> createNucleicAcid(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
        @Valid @RequestBody CreateNucleicAcidDto request) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 newNucleicAcidTestUser 方法，传入核酸检测人员信息进行创建操作
        userService.newNucleicAcidTestUser(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 获取核酸检测人员列表接口，需要提供有效的 token。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @return 返回一个包含核酸检测人员信息列表的统一结果对象。
     */
    @GetMapping("/nucleic_acid")
    public Result<?> getNucleicAcidList(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 getNucleicAcidTestUser 方法，获取核酸检测人员信息列表
        List<NucleicAcidTestPersonnelVo> nucleicAcidUserInfoList = userService.getNucleicAcidTestUser();
        // 返回包含核酸检测人员信息列表的成功结果
        return Result.success(nucleicAcidUserInfoList);
    }

    /**
     * 对核酸检测人员进行相反操作（具体操作由业务逻辑决定）的接口，需要提供有效的 token 和核酸检测人员 ID。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @param request 包含核酸检测人员 ID 的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PatchMapping("/nucleic_acid_opposite")
    public Result<?> nucleicAcidOpposite(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
        @Valid @RequestBody TidInputDto request) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 nucleicAcidOpposite 方法，传入核酸检测人员 ID 进行相反操作
        userService.nucleicAcidOpposite(request.getTid());
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 创建健康码管理人员信息接口，需要提供有效的 token 和健康码管理人员信息。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @param request 包含要创建的健康码管理人员信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含操作成功信息和 token 的统一结果对象。
     */
    @PostMapping("/manage")
    public Result<?> createManage(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
        @Valid @RequestBody CreateManageDto request) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 newMangerUser 方法，传入健康码管理人员信息进行创建操作
        userService.newMangerUser(request);
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 获取健康码管理人员列表接口，需要提供有效的 token。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @return 返回一个包含健康码管理人员信息列表的统一结果对象。
     */
    @GetMapping("/manager")
    public Result<?> getManageList(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 getManagerUser 方法，获取健康码管理人员信息列表
        List<HealthCodeManagerVo> manageUserInfoList = userService.getManagerUser();
        // 返回包含健康码管理人员信息列表的成功结果
        return Result.success(manageUserInfoList);
    }

    /**
     * 对健康码管理人员进行相反操作（具体操作由业务逻辑决定）的接口，需要提供有效的 token 和健康码管理人员 ID。
     *
     * @param token  用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @param request 包含健康码管理人员 ID 的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PatchMapping("/manage_opposite")
    public Result<?> manageOpposite(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
        @Valid @RequestBody MidInputDto request) {
        // 从 token 中提取用户 ID
        JWTUtil.extractID(token);
        // 调用 UserService 的 manageOpposite 方法，传入健康码管理人员 ID 进行相反操作
        userService.manageOpposite(request.getMid());
        // 返回操作成功的结果
        return Result.success();
    }
}