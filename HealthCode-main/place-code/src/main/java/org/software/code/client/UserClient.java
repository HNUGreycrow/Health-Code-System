package org.software.code.client;

import org.software.code.common.result.Result;
import org.software.code.dto.AreaCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * UserClient 是一个基于 Spring Cloud OpenFeign 的客户端接口，
 * 用于与名为 "user" 的服务进行远程调用。
 * 它定义了一系列与用户信息和区域代码相关的 HTTP GET 请求方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "user")
public interface UserClient {

    /**
     * 通过用户唯一标识符（UID）获取用户信息。
     * 该方法会向名为 "user" 的服务发送一个 HTTP GET 请求，
     * 请求路径为 "/user/getUserByUID"，并携带用户的 UID 作为请求参数。
     *
     * @param uid 用户的唯一标识符，类型为 Long
     * @return 封装了请求结果的 Result 对象，具体结果类型由服务端决定
     */
    @GetMapping("/user/getUserByUID")
    Result<?> getUserByUID(@RequestParam(name = "uid") Long uid);

    /**
     * 通过用户身份证号码获取用户信息。
     * 该方法会向名为 "user" 的服务发送一个 HTTP GET 请求，
     * 请求路径为 "/user/getUserByID"，并携带用户的身份证号码作为请求参数。
     *
     * @param identity_card 用户的身份证号码，类型为 String
     * @return 封装了请求结果的 Result 对象，具体结果类型由服务端决定
     */
    @GetMapping("/user/getUserByID")
    Result<?> getUserByID(@RequestParam(name = "identity_card") String identity_card);

    /**
     * 通过 ID 获取区域代码信息。
     * 该方法会向名为 "user" 的服务发送一个 HTTP GET 请求，
     * 请求路径为 "/user/area_code"，并携带 ID 作为请求参数。
     * 其中，ID 参数不能为空，否则会触发验证异常。
     *
     * @param id 用于查询区域代码的 ID，类型为 Long，且不能为空
     * @return 封装了请求结果的 Result 对象，具体结果类型由服务端决定
     */
    @GetMapping("/user/area_code")
    Result<?> getAreaCodeByID(@RequestParam(name = "id") @NotNull(message = "id不能为空") Long id);

    /**
     * 通过 AreaCodeDto 对象获取区域代码信息。
     * 该方法会向名为 "user" 的服务发送一个 HTTP GET 请求，
     * 请求路径为 "/user/area_code/id"，并将 AreaCodeDto 对象作为请求体发送。
     * 在发送请求前，会对 AreaCodeDto 对象进行验证，确保其符合验证规则。
     *
     * @param dto 包含查询区域代码所需信息的 AreaCodeDto 对象，需要进行验证
     * @return 封装了请求结果的 Result 对象，具体结果类型由服务端决定
     */
    @PostMapping("/user/area_code/id")
    Result<?> getAreaCodeID(@Valid @RequestBody AreaCodeDto dto);
}