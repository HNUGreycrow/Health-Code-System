package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.service.ItineraryCodeService;
import org.software.code.vo.PlaceStatusVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 行程码内部接口的控制器类，负责处理与行程码内部逻辑相关的 HTTP 请求。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/itinerary-code")
public class ItineraryCodeInternalController {

    // 注入行程码服务类，用于处理行程码相关的业务逻辑
    @Resource
    ItineraryCodeService itineraryCodeService;

    /**
     * 获取用户近 14 天行程信息的接口。
     * 该接口接收用户 ID 作为参数，调用服务层方法获取用户 14 天内途径的城市信息，
     * 同时读取本地风险 json 文件获取当日风险地区的城市 ID，
     * 对于有风险的城市将其状态设置为 1；若用户行程是国外（通过 province 小于 0 判断），默认状态返回 1。
     *
     * @param uid 用户 ID，不能为 null
     * @return 封装了行程信息列表的统一返回对象
     */
    @GetMapping("/getItineraryCodeList")
    public Result<?> getItineraryCodeList(@RequestParam(name = "uid") @NotNull(message = "uid不能为空") Long uid) {
        // 调用服务层方法，根据用户 ID 获取行程信息列表
        List<PlaceStatusVo> placeStatusVoList = itineraryCodeService.getItineraryCodeList(uid);
        // 将获取到的行程信息列表封装在统一返回对象中，表示请求成功
        return Result.success(placeStatusVoList);
    }

    /**
     * 清理 15 天以前用户行程信息的接口。
     * 该接口不接收参数，直接调用服务层方法清理 15 天以前的用户行程数据。
     *
     * @return 表示清理操作成功的统一返回对象
     */
    @GetMapping("/cleanItinerary")
    public Result<?> cleanItinerary() {
        // 调用服务层方法清理 15 天以前的用户行程
        itineraryCodeService.cleanItinerary();
        // 返回表示操作成功的统一返回对象
        return Result.success();
    }
}