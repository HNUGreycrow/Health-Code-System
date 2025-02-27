package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.service.ItineraryCodeService;
import org.software.code.vo.GetItineraryVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 行程码相关接口的控制器类，负责处理与行程码信息获取相关的 HTTP 请求。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/itinerary-code")
public class ItineraryCodeController {

    // 注入行程码服务类，用于处理行程码相关的业务逻辑
    @Resource
    private ItineraryCodeService itineraryCodeService;

    /**
     * 获取行程信息的接口。
     * 该接口接收一个包含在请求头中的 JWT Token，用于验证用户身份并获取用户 ID，
     * 然后调用服务层方法获取行程信息，最后将结果封装在统一的返回对象中返回。
     *
     * @param token 请求头中的 Authorization 字段，即 JWT Token，不能为 null
     * @return 封装了行程信息的统一返回对象
     */
    @GetMapping("/getItinerary")
    public Result<?> getItinerary(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从 JWT Token 中提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用服务层方法，根据用户 ID 获取行程信息
        GetItineraryVo getItineraryVo = itineraryCodeService.getItinerary(uid);
        // 将获取到的行程信息封装在统一返回对象中，表示请求成功
        return Result.success(getItineraryVo);
    }
}