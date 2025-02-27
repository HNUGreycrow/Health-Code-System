package org.software.code.client;

import org.software.code.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 这是一个使用 Spring Cloud OpenFeign 实现的 Feign 客户端接口。
 * 该接口用于与名为 "user" 的服务进行远程调用，通过 Feign 可以方便地发起 HTTP 请求。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "user")
public interface UserClient {

    /**
     * 调用名为 "user" 的服务中的 /user/getUserByUID 接口，根据用户 ID 获取用户信息。
     *
     * @param uid 用户的唯一标识，作为请求参数传递给远程服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用远程服务获取的用户信息。
     */
    @GetMapping("/user/getUserByUID")
    Result<?> getUserByUID(@RequestParam(name = "uid") Long uid);
}