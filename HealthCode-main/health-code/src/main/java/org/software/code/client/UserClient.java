package org.software.code.client;

import org.software.code.common.result.Result;
import org.software.code.dto.UserInfoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserClient 是一个使用 Spring Cloud OpenFeign 实现的 Feign 客户端接口。
 * Feign 客户端允许我们以声明式的方式调用其他服务的 RESTful API，简化了服务间的调用过程。
 * 该接口主要用于与名为 "user" 的服务进行交互，提供了添加用户信息、删除用户信息、根据 UID 获取用户信息以及根据身份证号获取用户信息等功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
// @FeignClient 注解用于声明一个 Feign 客户端，name 属性指定了要调用的目标服务的名称。
// Spring Cloud 会根据服务名称从服务注册中心（如 Nacos、Eureka 等）查找对应的服务实例，并进行负载均衡调用。
@FeignClient(name = "user")
public interface UserClient {

    /**
     * 该方法用于向 "user" 服务发送添加用户信息的请求。
     * 它使用 PUT 请求方式，请求路径为 "/user/addUserInfo"。
     *
     * @param request 包含用户信息的请求 DTO 对象，通过 @RequestBody 注解将其作为请求体发送。
     * @return 返回一个包含操作结果的通用 Result 对象，Result 类型为泛型，可根据实际情况包含不同的数据。
     */
    // @PutMapping 注解指定了该方法使用 HTTP PUT 请求方式，请求路径为 "/user/addUserInfo"。
    @PutMapping("/user/addUserInfo")
    Result<?> addUserInfo(@RequestBody UserInfoRequestDto request);

    /**
     * 该方法用于向 "user" 服务发送删除用户信息的请求。
     * 它使用 GET 请求方式，请求路径为 "/user/delete"，并通过请求参数传递要删除的用户的 UID。
     *
     * @param uid 要删除的用户的唯一标识，通过 @RequestParam 注解将其作为请求参数发送。
     * @return 返回一个包含操作结果的通用 Result 对象，Result 类型为泛型，可根据实际情况包含不同的数据。
     */
    // @GetMapping 注解指定了该方法使用 HTTP GET 请求方式，请求路径为 "/user/delete"。
    @GetMapping("/user/delete")
    Result<?> deleteUserInfo(@RequestParam("uid") long uid);

    /**
     * 该方法用于向 "user" 服务发送根据 UID 获取用户信息的请求。
     * 它使用 GET 请求方式，请求路径为 "/user/getUserByUID"，并通过请求参数传递要查询的用户的 UID。
     *
     * @param uid 要查询的用户的唯一标识，通过 @RequestParam 注解将其作为请求参数发送，name 属性指定了请求参数的名称。
     * @return 返回一个包含操作结果的通用 Result 对象，Result 类型为泛型，可根据实际情况包含不同的数据。
     */
    // @GetMapping 注解指定了该方法使用 HTTP GET 请求方式，请求路径为 "/user/getUserByUID"。
    @GetMapping("/user/getUserByUID")
    Result<?> getUserByUID(@RequestParam(name = "uid") long uid);

    /**
     * 该方法用于向 "user" 服务发送根据身份证号获取用户信息的请求。
     * 它使用 GET 请求方式，请求路径为 "/user/getUserByID"，并通过请求参数传递要查询的用户的身份证号。
     *
     * @param identity_card 要查询的用户的身份证号，通过 @RequestParam 注解将其作为请求参数发送，name 属性指定了请求参数的名称。
     * @return 返回一个包含操作结果的通用 Result 对象，Result 类型为泛型，可根据实际情况包含不同的数据。
     */
    // @GetMapping 注解指定了该方法使用 HTTP GET 请求方式，请求路径为 "/user/getUserByID"。
    @GetMapping("/user/getUserByID")
    Result<?> getUserByID(@RequestParam(name = "identity_card") String identity_card);
}