package org.software.code.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.result.Result;
import org.software.code.dto.NucleicAcidTestRecordDto;
import org.software.code.dto.NucleicAcidTestRecordInputDto;
import org.software.code.service.NucleicAcidsService;
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
 * NucleicAcidsInternalController 是一个 RESTful 控制器，主要用于处理与核酸检测记录相关的内部接口请求。
 * 它提供了添加核酸检测记录、批量录入检测记录、根据用户 ID 获取检测记录、根据时间范围获取检测信息和阳性信息等功能，
 * 同时还支持通知重新检测和自动修改检测记录的操作。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping(value = "/nucleic-acids")
public class NucleicAcidsInternalController {
    // 日志记录器，用于记录控制器中的关键信息和错误信息
    private static final Logger logger = LogManager.getLogger(NucleicAcidsInternalController.class);

    // 注入核酸检测服务，用于处理具体的业务逻辑
    @Resource
    private NucleicAcidsService nucleicAcidsService;

    // 日期格式化对象，用于将字符串日期转换为 Date 对象
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 添加单条核酸检测记录。
     *
     * @param testRecord 包含核酸检测记录信息的 DTO 对象，使用 @Valid 注解进行数据校验
     * @return 包含添加的核酸检测记录的成功结果对象
     */
    @PostMapping("/addNucleicAcidTestRecord")
    public Result<?> addNucleicAcidTestRecord(@RequestBody @Valid NucleicAcidTestRecordDto testRecord) {
        // 调用服务层方法添加核酸检测记录
        nucleicAcidsService.addNucleicAcidTestRecord(testRecord);
        // 返回包含添加记录的成功结果
        return Result.success(testRecord);
    }

    /**
     * 批量录入核酸检测记录。
     *
     * @param testRecords 包含多个核酸检测记录信息的列表，使用 @Valid 注解进行数据校验
     * @return 包含录入的核酸检测记录列表的成功结果对象
     */
    @PutMapping("/enterNucleicAcidTestRecordList")
    public Result<?> enterNucleicAcidTestRecordList(@RequestBody @Valid List<NucleicAcidTestRecordInputDto> testRecords) {
        // 调用服务层方法批量录入核酸检测记录
        nucleicAcidsService.enterNucleicAcidTestRecordList(testRecords);
        // 返回包含录入记录列表的成功结果
        return Result.success(testRecords);
    }

    /**
     * 根据用户 ID 获取用户的最后一条核酸检测记录。
     *
     * @param uid 用户的唯一标识，使用 @NotNull 注解确保该参数不为空
     * @return 包含用户最后一条核酸检测记录的成功结果对象
     */
    @GetMapping("/getLastNucleicAcidTestRecordByUID")
    public Result<?> getLastNucleicAcidTestRecordByUID(@RequestParam @NotNull(message = "uid不能为空") Long uid) {
        // 调用服务层方法获取用户的最后一条核酸检测记录
        return Result.success(nucleicAcidsService.getLastNucleicAcidTestRecordByUID(uid));
    }

    /**
     * 根据用户 ID 获取用户的所有核酸检测记录。
     *
     * @param uid 用户的唯一标识，使用 @NotNull 注解确保该参数不为空
     * @return 包含用户所有核酸检测记录的成功结果对象
     */
    @GetMapping("/getNucleicAcidTestRecordByUID")
    public Result<?> getNucleicAcidTestRecordByUID(@RequestParam @NotNull(message = "uid不能为空") Long uid) {
        // 调用服务层方法获取用户的所有核酸检测记录
        return Result.success(nucleicAcidsService.getNucleicAcidTestRecordByUID(uid));
    }

    /**
     * 根据时间范围获取核酸检测信息。
     *
     * @param startTime 开始时间，格式为 "yyyy-MM-dd"，使用 @NotNull 注解确保该参数不为空
     * @param endTime 结束时间，格式为 "yyyy-MM-dd"，使用 @NotNull 注解确保该参数不为空
     * @return 包含指定时间范围内核酸检测信息的结果对象，若解析日期出错或发生其他异常则返回失败结果
     */
    @GetMapping("/getNucleicAcidTestInfoByTime")
    public Result<?> getNucleicAcidTestInfoByTime(@RequestParam("start_time") @NotNull(message = "开始时间不能为空") String startTime,
                                                  @RequestParam("end_time") @NotNull(message = "结束时间不能为空") String endTime) {
        try {
            // 将开始时间字符串转换为 Date 对象
            Date startDate = dateFormat.parse(startTime);
            // 将结束时间字符串转换为 Date 对象
            Date endDate = dateFormat.parse(endTime);
            // 调用服务层方法获取指定时间范围内的核酸检测信息
            return Result.success(nucleicAcidsService.getNucleicAcidTestInfoByTime(startDate, endDate));
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: {}", e.getMessage());
            // 返回日期解析失败的结果
            return Result.failed("服务执行失败，请稍后重试");
        } catch (Exception e) {
            // 记录其他异常错误日志
            logger.error("Unexpected error: {}", e.getMessage());
            // 返回包含异常信息的失败结果
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 根据时间范围获取阳性核酸检测信息。
     *
     * @param startTime 开始时间，格式为 "yyyy-MM-dd"，使用 @NotNull 注解确保该参数不为空
     * @param endTime 结束时间，格式为 "yyyy-MM-dd"，使用 @NotNull 注解确保该参数不为空
     * @return 包含指定时间范围内阳性核酸检测信息的结果对象，若解析日期出错或发生其他异常则返回失败结果
     */
    @GetMapping("/getPositiveInfoByTime")
    public Result<?> getPositiveInfoByTime(@RequestParam("start_time") @NotNull(message = "开始时间不能为空") String startTime,
                                           @RequestParam("end_time") @NotNull(message = "结束时间不能为空") String endTime) {
        try {
            // 将开始时间字符串转换为 Date 对象
            Date startDate = dateFormat.parse(startTime);
            // 将结束时间字符串转换为 Date 对象
            Date endDate = dateFormat.parse(endTime);
            // 调用服务层方法获取指定时间范围内的阳性核酸检测信息
            return Result.success(nucleicAcidsService.getPositiveInfoByTime(startDate, endDate));
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: {}", e.getMessage());
            // 返回日期解析失败的结果
            return Result.failed("服务执行失败，请稍后重试");
        } catch (Exception e) {
            // 记录其他异常错误日志
            logger.error("Unexpected error: {}", e.getMessage());
            // 返回包含异常信息的失败结果
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 通知需要重新检测的人员。
     *
     * @return 表示通知操作成功的结果对象
     */
    @GetMapping("/noticeReTest")
    public Result<?> noticeReTest() {
        // 调用服务层方法获取需要重新检测的记录并进行通知
        nucleicAcidsService.getNoticeReTestRecords();
        // 返回操作成功的结果
        return Result.success();
    }

    /**
     * 自动修改核酸检测记录。
     *
     * @return 表示自动修改操作成功的结果对象
     */
    @GetMapping("/autoModify")
    public Result<?> autoModify() {
        // 调用服务层方法进行自动修改操作
        nucleicAcidsService.autoModify();
        // 返回操作成功的结果
        return Result.success();
    }
}