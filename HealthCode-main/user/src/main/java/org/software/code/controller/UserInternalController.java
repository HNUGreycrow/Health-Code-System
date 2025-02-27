package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.*;
import org.software.code.service.UserService;
import org.software.code.vo.HealthCodeManagerVo;
import org.software.code.vo.NucleicAcidTestPersonnelVo;
import org.software.code.vo.UserInfoVo;
import org.software.code.vo.UserLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户内部控制器类，负责处理与用户相关的内部业务逻辑请求，
 * 涵盖用户信息查询、登录、人员管理（核酸检测人员和管理人员）、信息修改、状态变更等操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/user")
public class UserInternalController {
    // 注入 UserService 实例，用于调用用户相关的业务逻辑方法
    @Autowired
    private UserService userService;

    /**
     * 根据用户 ID 获取用户信息。
     *
     * @param uid 用户的唯一标识，通过请求参数传递，不能为空。
     * @return 返回一个包含用户信息的统一结果对象。
     */
    @GetMapping("/getUserByUID")
    public Result<?> getUserByUID(@RequestParam(name = "uid") @NotNull(message = "uid不能为空") Long uid) {
        // 调用 UserService 的 getUserByUID 方法，根据用户 ID 获取用户信息
        UserInfoVo userInfoVo = userService.getUserByUID(uid);
        // 返回包含用户信息的成功结果
        return Result.success(userInfoVo);
    }

    /**
     * 根据身份证号码获取用户信息。
     *
     * @param identity_card 用户的身份证号码，通过请求参数传递，不能为空。
     * @return 返回一个包含用户信息的统一结果对象。
     */
    @GetMapping("/getUserByID")
    public Result<?> getUserByID(@RequestParam(name = "identity_card") @NotNull(message = "identity_card不能为空") String identity_card) {
        // 调用 UserService 的 getUserByID 方法，根据身份证号码获取用户信息
        UserInfoVo userInfoVo = userService.getUserByID(identity_card);
        // 返回包含用户信息的成功结果
        return Result.success(userInfoVo);
    }

    /**
     * 用户登录接口，使用 code 进行登录。
     *
     * @param input 包含登录所需 code 的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/userLogin")
    public Result<?> userLogin(@Valid @RequestBody CodeInputDto input) {
        // 调用 UserService 的 userLogin 方法，根据输入的 code 进行用户登录操作，获取 token
        UserLoginVo userLoginVo = userService.userLogin(input.getCode());
        // 返回包含 token 的成功结果
        return Result.success(userLoginVo);
    }

    /**
     * 核酸检测人员登录接口。
     *
     * @param request 包含核酸检测人员登录信息的数据传输对象。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/nucleicAcidTestUserLogin")
    public Result<?> nucleicAcidTestUserLogin(@RequestBody NucleicAcidsLoginDto request) {
        // 调用 UserService 的 nucleicAcidTestUserLogin 方法，根据输入的登录信息进行核酸检测人员登录操作，获取 token
        String token = userService.nucleicAcidTestUserLogin(request);
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 获取所有核酸检测人员信息列表。
     *
     * @return 返回一个包含核酸检测人员信息列表的统一结果对象。
     */
    @GetMapping("/getNucleicAcidTestUser")
    public Result<?> getNucleicAcidTestUser() {
        // 调用 UserService 的 getNucleicAcidTestUser 方法，获取所有核酸检测人员信息列表
        List<NucleicAcidTestPersonnelVo> nucleicAcidUserInfoList = userService.getNucleicAcidTestUser();
        // 返回包含核酸检测人员信息列表的成功结果
        return Result.success(nucleicAcidUserInfoList);
    }

    /**
     * 获取所有健康码管理人员信息列表。
     *
     * @return 返回一个包含健康码管理人员信息列表的统一结果对象。
     */
    @GetMapping("/getManagerUser")
    public Result<?> getManagerUser() {
        // 调用 UserService 的 getManagerUser 方法，获取所有健康码管理人员信息列表
        List<HealthCodeManagerVo> manageUserInfoList = userService.getManagerUser();
        // 返回包含健康码管理人员信息列表的成功结果
        return Result.success(manageUserInfoList);
    }

    /**
     * 创建新的核酸检测人员信息。
     *
     * @param request 包含新核酸检测人员信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PostMapping("/newNucleicAcidTestUser")
    public Result<?> newNucleicAcidTestUser(@Valid @RequestBody CreateNucleicAcidDto request) {
        // 调用 UserService 的 newNucleicAcidTestUser 方法，根据输入的信息创建新的核酸检测人员信息
        userService.newNucleicAcidTestUser(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 创建新的健康码管理人员信息。
     *
     * @param request 包含新健康码管理人员信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PostMapping("/newMangerUser")
    public Result<?> newMangerUser(@Valid @RequestBody CreateManageDto request) {
        // 调用 UserService 的 newMangerUser 方法，根据输入的信息创建新的健康码管理人员信息
        userService.newMangerUser(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 健康码管理人员登录接口。
     *
     * @param request 包含健康码管理人员登录信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个包含登录成功后生成的 token 的统一结果对象。
     */
    @PostMapping("/managerUserLogin")
    public Result<?> managerLogin(@Valid @RequestBody ManagerLoginDto request) {
        // 调用 UserService 的 managerLogin 方法，根据输入的登录信息进行健康码管理人员登录操作，获取 token
        String token = userService.managerLogin(request);
        // 返回包含 token 的成功结果
        return Result.success(token);
    }

    /**
     * 修改用户信息。
     *
     * @param request 包含要修改的用户信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PutMapping("/modifyUserInfo")
    public Result<?> modifyUserInfo(@Valid @RequestBody UserInfoDto request) {
        // 调用 UserService 的 modifyUserInfo 方法，根据输入的信息修改用户信息
        userService.modifyUserInfo(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 修改核酸检测人员的状态。
     *
     * @param request 包含要修改的核酸检测人员状态信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PatchMapping("/statusNucleicAcidTestUser")
    public Result<?> statusNucleicAcidTestUser(@Valid @RequestBody StatusNucleicAcidTestUserDto request) {
        // 调用 UserService 的 statusNucleicAcidTestUser 方法，根据输入的信息修改核酸检测人员的状态
        userService.statusNucleicAcidTestUser(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 修改健康码管理人员的状态。
     *
     * @param request 包含要修改的健康码管理人员状态信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PatchMapping("/statusManager")
    public Result<?> statusManager(@Valid @RequestBody StatusManagerDto request) {
        // 调用 UserService 的 statusManager 方法，根据输入的信息修改健康码管理人员的状态
        userService.statusManager(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 添加新的用户信息。
     *
     * @param request 包含要添加的用户信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PutMapping("/addUserInfo")
    public Result<?> addUserInfo(@Valid @RequestBody UserInfoDto request) {
        // 调用 UserService 的 addUserInfo 方法，根据输入的信息添加新的用户信息
        userService.addUserInfo(request);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 根据用户 ID 删除用户信息。
     *
     * @param uid 用户的唯一标识，通过请求参数传递，不能为空。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @GetMapping("/delete")
    public Result<?> deleteUserInfo(@RequestParam("uid") @NotNull(message = "uid不能为空") Long uid) {
        // 调用 UserService 的 deleteUserInfo 方法，根据用户 ID 删除用户信息
        userService.deleteUserInfo(uid);
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 测试接口，打印传入的 tid 信息。
     *
     * @param tidInpt 包含 tid 信息的数据传输对象，使用 @Valid 注解进行数据验证。
     * @return 返回一个表示操作成功的统一结果对象。
     */
    @PostMapping("/testuid")
    public Result<?> testuid(@RequestBody @Valid TidInputDto tidInpt) {
        // 打印传入的 tid 信息
        System.out.println(tidInpt.getTid());
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 从请求头的 token 中提取用户 ID。
     *
     * @param token 用户身份验证的 token，从请求头的 "Authorization" 字段获取，不能为空。
     * @return 返回一个包含从 token 中提取的用户 ID 的统一结果对象。
     */
    @GetMapping("/getuid")
    public Result<?> createManage(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 调用 JWTUtil 的 extractID 方法，从 token 中提取用户 ID
        return Result.success(JWTUtil.extractID(token));
    }
}