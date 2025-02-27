package org.software.code.client;

import org.software.code.common.result.Result;
import org.software.code.dto.GetPlacesByUserListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 地点码服务的 Feign 客户端接口，使用 Spring Cloud OpenFeign 实现与 "place - code" 服务的远程调用。
 * 通过该接口可以方便地调用地点码服务提供的各种功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "place-code")
public interface PlaceCodeClient {

    /**
     * 调用地点码服务的 /place-code/getPlacesByUserList 接口，根据用户列表获取相关地点信息。
     *
     * @param request 包含用户列表信息的请求体，由 GetPlacesByUserListDto 对象封装。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含根据用户列表查询到的地点相关信息。
     */
    @PostMapping("/place-code/getPlacesByUserList")
    Result<?> getPlacesByUserList(@RequestBody GetPlacesByUserListDto request);

    /**
     * 调用地点码服务的 /place-code/getRecordByPid 接口，根据地点 ID 和时间范围获取地点记录信息。
     *
     * @param pid       地点的唯一标识。
     * @param startTime 查询记录的起始时间。
     * @param endTime   查询记录的结束时间。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含指定地点在指定时间范围内的记录信息。
     */
    @GetMapping("/place-code/getRecordByPid")
    Result<?> getRecordByPid(@RequestParam("pid") Long pid,
                             @RequestParam("start_time") String startTime,
                             @RequestParam("end_time") String endTime);

    /**
     * 调用地点码服务的 /place-code/getAllPids 接口，获取所有地点的 ID 信息。
     *
     * @return 返回一个封装了结果信息的 Result 对象，其中包含所有地点的 ID 列表。
     */
    @GetMapping("/place-code/getAllPids")
    Result<?> getAllPids();

    /**
     * 调用地点码服务的 /place-code/setPlaceRisk 接口，设置指定地点的风险等级。
     *
     * @param pid  地点的唯一标识。
     * @param risk 要设置的风险等级。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含设置地点风险等级操作的结果信息。
     */
    @PutMapping("/place-code/setPlaceRisk")
    Result<?> setPlaceRisk(@RequestParam("pid") Long pid, @RequestParam("risk") String risk);
}