package org.software.code.client;

import org.software.code.common.result.Result;
import org.software.code.dto.TranscodingEventsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 健康码服务的 Feign 客户端接口。
 * 借助 Spring Cloud OpenFeign，该接口用于与名为 "health - code" 的服务进行远程调用，实现与健康码服务的交互。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "health-code")
public interface HealthCodeClient {

    /**
     * 调用健康码服务的 /health-code/getHealthCode 接口，根据用户 ID 获取健康码信息。
     *
     * @param uid 用户的唯一标识，作为请求参数传递给健康码服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用健康码服务获取的健康码相关信息。
     */
    @GetMapping("/health-code/getHealthCode")
    Result<?> getHealthCode(@RequestParam(name = "uid") Long uid);

    /**
     * 调用健康码服务的 /health-code/transcodingHealthCodeEvents 接口，处理健康码转码事件。
     *
     * @param request 包含健康码转码事件相关信息的请求体，由 TranscodingEventsDto 对象封装。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含健康码服务处理转码事件后的响应信息。
     */
    @PatchMapping("/health-code/transcodingHealthCodeEvents")
    Result<?> transcodingHealthCodeEvents(@RequestBody TranscodingEventsDto request);
}