package org.software.code.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.AddPlaceInputDto;
import org.software.code.dto.GetPlacesByUserListRequestDto;
import org.software.code.dto.OppositePlaceCodeRequestDto;
import org.software.code.dto.ScanPlaceCodeRequestDto;
import org.software.code.service.PlaceCodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 场所码内部操作的控制器类，负责处理场所码相关的内部业务逻辑请求，
 * 如添加场所、获取场所记录、扫描场所码等操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/place-code")
public class PlaceCodeInternalController {

    // 日志记录器，用于记录异常信息和操作日志
    private static final Logger logger = LogManager.getLogger(PlaceCodeInternalController.class);

    // 注入场所码服务层，用于调用具体的业务逻辑方法
    @Resource
    private PlaceCodeService placeCodeService;

    // 日期格式化对象，用于将字符串日期转换为 Date 类型
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 添加场所的接口。
     * 接收一个包含场所信息的请求体，调用服务层的 addPlace 方法添加场所，
     * 并将添加结果封装在统一的 Result 对象中返回。
     *
     * @param placeDto 包含场所信息的请求体，经过 @Valid 注解进行数据验证
     * @return 封装了添加场所操作结果的 Result 对象
     */
    @PostMapping("/addPlace")
    public Result<?> addPlace(@RequestBody @Valid AddPlaceInputDto placeDto) {
        return Result.success(placeCodeService.addPlace(placeDto));
    }

    /**
     * 获取所有场所信息的接口。
     * 调用服务层的 getPlaces 方法获取所有场所信息，
     * 并将结果封装在统一的 Result 对象中返回。
     *
     * @return 封装了所有场所信息的 Result 对象
     */
    @GetMapping("/getPlaces")
    public Result<?> getPlaces() {
        return Result.success(placeCodeService.getPlaces());
    }

    /**
     * 根据场所 ID 和时间范围获取场所记录的接口。
     * 接收场所 ID、开始时间和结束时间作为请求参数，
     * 将时间字符串解析为 Date 类型，调用服务层的 getRecordByPid 方法获取记录，
     * 若日期解析失败则返回日期格式错误的结果。
     *
     * @param pid 场所 ID，不能为 null
     * @param start_time 开始时间，不能为 null
     * @param end_time 结束时间，不能为 null
     * @return 封装了场所记录信息的 Result 对象，或日期格式错误的结果
     */
    @GetMapping("/getRecordByPid")
    public Result<?> getRecordByPid(@RequestParam("pid") @NotNull(message = "pid不能为空") Long pid,
                                    @RequestParam("start_time") @NotNull(message = "开始时间不能为空") String start_time,
                                    @RequestParam("end_time") @NotNull(message = "结束时间不能为空") String end_time) {
        Date startDate;
        Date endDate;
        try {
            // 将开始时间字符串解析为 Date 类型
            startDate = timeFormat.parse(start_time);
            // 将结束时间字符串解析为 Date 类型
            endDate = timeFormat.parse(end_time);
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: start_time={}, end_time={}, message={}", start_time, end_time, e.getMessage());
            // 返回日期格式错误的结果
            return Result.failed(ExceptionEnum.DATETIME_FORMAT_ERROR.getMsg());
        }
        // 调用服务层方法获取场所记录并返回结果
        return Result.success(placeCodeService.getRecordByPid(pid, startDate, endDate));
    }

    /**
     * 扫描场所码的接口。
     * 接收一个包含扫描信息的请求体，从请求体的令牌中提取场所 ID，
     * 调用服务层的 scanPlaceCode 方法进行扫描操作，并返回操作成功的结果。
     *
     * @param request 包含扫描信息的请求体，经过 @Valid 注解进行数据验证
     * @return 操作成功的 Result 对象
     */
    @PostMapping("/scanPlaceCode")
    public Result<?> scanPlaceCode(@Valid @RequestBody ScanPlaceCodeRequestDto request) {
        // 从请求体的令牌中提取场所 ID
        long pid = JWTUtil.extractID(request.getToken());
        // 调用服务层方法进行扫描操作
        placeCodeService.scanPlaceCode(request.getUid(), pid);
        return Result.success();
    }

    /**
     * 反转场所码状态的接口。
     * 接收一个包含场所 ID 和目标状态的请求体，
     * 调用服务层的 oppositePlaceCode 方法反转场所码状态，并返回操作成功的结果。
     *
     * @param request 包含场所 ID 和目标状态的请求体，经过 @Valid 注解进行数据验证
     * @return 操作成功的 Result 对象
     */
    @PostMapping("/oppositePlaceCode")
    public Result<?> oppositePlaceCode(@Valid @RequestBody OppositePlaceCodeRequestDto request) {
        // 调用服务层方法反转场所码状态
        placeCodeService.oppositePlaceCode(request.getPid(), request.getStatus());
        return Result.success();
    }

    /**
     * 根据用户列表和时间范围获取场所信息的接口。
     * 接收一个包含用户列表、开始时间和结束时间的请求体，
     * 将时间字符串解析为 Date 类型，调用服务层的 getPlacesByUserList 方法获取场所信息，
     * 若日期解析失败则返回日期格式错误的结果。
     *
     * @param request 包含用户列表、开始时间和结束时间的请求体，经过 @Valid 注解进行数据验证
     * @return 封装了场所信息的 Result 对象，或日期格式错误的结果
     */
    @PostMapping("/getPlacesByUserList")
    public Result<?> getPlacesByUserList(@Valid @RequestBody GetPlacesByUserListRequestDto request) {
        Date startDate;
        Date endDate;
        try {
            // 将开始时间字符串解析为 Date 类型
            startDate = timeFormat.parse(request.getStart_time());
            // 将结束时间字符串解析为 Date 类型
            endDate = timeFormat.parse(request.getEnd_time());
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: start_time={}, end_time={}, message={}", request.getStart_time(), request.getEnd_time(), e.getMessage());
            // 返回日期格式错误的结果
            return Result.failed(ExceptionEnum.DATETIME_FORMAT_ERROR.getMsg());
        }
        // 调用服务层方法获取场所信息并返回结果
        return Result.success(placeCodeService.getPlacesByUserList(request.getUidList(), startDate, endDate));
    }

    /**
     * 获取所有场所 ID 的接口。
     * 调用服务层的 getAllPids 方法获取所有场所 ID 列表，
     * 并将结果封装在统一的 Result 对象中返回。
     *
     * @return 封装了所有场所 ID 列表的 Result 对象
     */
    @GetMapping("/getAllPids")
    public Result<?> getAllPids() {
        // 调用服务层方法获取所有场所 ID 列表
        List<Long> pids = placeCodeService.getAllPids();
        return Result.success(pids);
    }

    /**
     * 设置场所风险等级的接口。
     * 接收场所 ID 和风险等级作为请求参数，
     * 目前此方法仅预留，待实现具体的设置场所风险等级逻辑，
     * 暂时返回操作成功的结果。
     *
     * @param pid 场所 ID
     * @param risk 风险等级
     * @return 操作成功的 Result 对象
     */
    @PutMapping("/setPlaceRisk")
    public Result<?> setPlaceRisk(@RequestParam("pid") Long pid, @RequestParam("risk") String risk) {
        // TODO 设置场所的风险等级
        return Result.success();
    }
}