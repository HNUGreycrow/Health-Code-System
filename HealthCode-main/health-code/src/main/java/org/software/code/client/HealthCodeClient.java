package org.software.code.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * HealthCodeClient 是一个使用 Spring Cloud OpenFeign 实现的 Feign 客户端接口。
 * Feign 客户端可以让我们以声明式的方式来调用其他服务的 RESTful API，简化了服务间调用的复杂度。
 * 此接口用于与名为 "health-code" 的服务进行交互。不过目前接口内部为空，后续可以根据业务需求添加具体的服务调用方法。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
// @FeignClient 注解用于声明一个 Feign 客户端，其中 name 属性指定了要调用的目标服务的名称。
// Spring Cloud 会依据这个服务名称，从服务注册中心（例如 Nacos、Eureka 等）查找对应的服务实例，并且进行负载均衡调用。
@FeignClient(name = "health-code")
public interface HealthCodeClient {
    // 这里可以添加调用 "health-code" 服务的具体方法，
    // 例如使用 @GetMapping、@PostMapping 等注解定义 HTTP 请求方法和路径，
    // 并通过方法参数传递请求参数，返回值接收服务响应结果。
    // 示例：
    // @GetMapping("/health-code/api/path")
    // SomeResponseType someMethod(@RequestParam("paramName") String paramValue);
}