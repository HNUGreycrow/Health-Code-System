package org.software.code.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.software.code.common.except.ExceptionEnum;
import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.AddNucleicAcidTestRecordByIDDto;
import org.software.code.dto.AddNucleicAcidTestRecordDto;
import org.software.code.dto.NucleicAcidTestRecordInputDto;
import org.software.code.service.NucleicAcidsService;
import org.software.code.vo.NucleicAcidTestResultVo;
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
 * NucleicAcidsController 是一个 RESTful 控制器，用于处理与核酸检测记录相关的 HTTP 请求。
 * 该控制器提供了获取核酸检测记录、添加核酸检测记录、录入核酸检测记录列表、获取核酸检测信息和阳性信息等功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/nucleic-acids")
public class NucleicAcidsController {
    // 日志记录器，用于记录控制器中的关键信息和错误信息
    private static final Logger logger = LogManager.getLogger(NucleicAcidsController.class);

    // 注入核酸检测服务，用于处理具体的业务逻辑
    @Resource
    private NucleicAcidsService nucleicAcidsService;
    // 日期格式化对象，用于将字符串日期转换为 Date 对象
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取用户的最后一条核酸检测记录。
     *
     * @param token 请求头中的 JWT 令牌，用于提取用户 ID
     * @return 包含最后一条核酸检测记录的结果对象
     */
    @GetMapping("/getLastNucleicAcidTestRecord")
    public Result<?> getLastNucleicAcidTestRecord(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从 JWT 令牌中提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用服务层方法获取最后一条核酸检测记录
        NucleicAcidTestResultVo nucleicAcidTestResultVo = nucleicAcidsService.getLastNucleicAcidTestRecordByUID(uid);
        // 返回包含检测记录的成功结果
        return Result.success(nucleicAcidTestResultVo);
    }

    /**
     * 获取用户的近14天的核酸检测记录。
     *
     * @param token 请求头中的 JWT 令牌，用于提取用户 ID
     * @return 包含所有核酸检测记录列表的结果对象
     */
    @GetMapping("/getNucleicAcidTestRecord")
    public Result<?> getNucleicAcidTestRecord(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token) {
        // 从 JWT 令牌中提取用户 ID
        long uid = JWTUtil.extractID(token);
        // 调用服务层方法获取所有核酸检测记录
        List<NucleicAcidTestResultVo> nucleicAcidTestResultVoList = nucleicAcidsService.getNucleicAcidTestRecordByUID(uid);
        // 返回包含检测记录列表的成功结果
        return Result.success(nucleicAcidTestResultVoList);
    }

    /**
     * 通过令牌添加核酸检测记录。
     *
     * @param tidToken 请求头中的 JWT 令牌，用于提取检测人员 ID
     * @param request 包含核酸检测记录信息的请求体
     * @return 表示添加成功的结果对象
     */
    @PostMapping("/addNucleicAcidTestRecordByToken")
    public Result<?> addNucleicAcidTestRecordByToken(@RequestHeader("Authorization") String tidToken,
                                                     @Valid @RequestBody AddNucleicAcidTestRecordDto request) {
        // 从 JWT 令牌中提取检测人员 ID
        long tid = JWTUtil.extractID(tidToken);
        // 从请求体中的令牌提取用户 ID
        long uid = JWTUtil.extractID(request.getToken());
        // 调用服务层方法添加核酸检测记录
        nucleicAcidsService.addNucleicAcidTestRecordByToken(tid, uid, request);
        // 返回添加成功的结果
        return Result.success();
    }

    /**
     * 通过 ID 添加核酸检测记录。
     *
     * @param token 请求头中的 JWT 令牌，用于提取检测人员 ID
     * @param request 包含核酸检测记录信息的请求体
     * @return 表示添加成功的结果对象
     */
    @PostMapping("/addNucleicAcidTestRecordByID")
    public Result<?> addNucleicAcidTestRecordByID(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                                  @Valid @RequestBody AddNucleicAcidTestRecordByIDDto request) {
        // 从 JWT 令牌中提取检测人员 ID
        long tid = JWTUtil.extractID(token);
        // 调用服务层方法添加核酸检测记录
        nucleicAcidsService.addNucleicAcidTestRecordByID(tid, request);
        // 返回添加成功的结果
        return Result.success();
    }

    /**
     * 批量录入核酸检测记录。
     *
     * @param token 请求头中的 JWT 令牌，用于提取用户 ID
     * @param inputs 包含多个核酸检测记录信息的列表
     * @return 表示录入成功的结果对象
     */
    @PutMapping("/enterNucleicAcidTestRecord")
    public Result<?> enterNucleicAcidTestRecord(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                                @RequestBody List<NucleicAcidTestRecordInputDto> inputs) {
        // 从 JWT 令牌中提取用户 ID
        JWTUtil.extractID(token);
        // 调用服务层方法批量录入核酸检测记录
        nucleicAcidsService.enterNucleicAcidTestRecordList(inputs);
        // 返回录入成功的结果
        return Result.success();
    }

    /**
     * 根据时间范围获取核酸检测信息。
     *
     * @param token 请求头中的 JWT 令牌，用于提取用户 ID
     * @param start_time 请求参数，开始日期，格式为 "yyyy-MM-dd"
     * @param end_time 请求参数，结束日期，格式为 "yyyy-MM-dd"
     * @return 包含指定时间范围内核酸检测信息的结果对象
     */
    @GetMapping("/getNucleicAcidTestInfo")
    public Result<?> getNucleicAcidTestInfo(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                            @RequestParam(name = "start_time") String start_time,
                                            @RequestParam(name = "end_time") String end_time) {
        try {
            // 从 JWT 令牌中提取用户 ID
            JWTUtil.extractID(token);
            // 将开始日期字符串转换为 Date 对象
            Date startDate = dateFormat.parse(start_time);
            // 将结束日期字符串转换为 Date 对象
            Date endDate = dateFormat.parse(end_time);
            // 调用服务层方法获取指定时间范围内的核酸检测信息
            return Result.success(nucleicAcidsService.getNucleicAcidTestInfoByTime(startDate, endDate));
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: {}", e.getMessage());
            // 返回日期格式错误的失败结果
            return Result.failed(ExceptionEnum.DATETIME_FORMAT_ERROR.getMsg());
        }
    }

    /**
     * 根据时间范围获取阳性核酸检测信息。
     *
     * @param token 请求头中的 JWT 令牌，用于提取用户 ID
     * @param start_time 请求参数，开始日期，格式为 "yyyy-MM-dd"
     * @param end_time 请求参数，结束日期，格式为 "yyyy-MM-dd"
     * @return 包含指定时间范围内阳性核酸检测信息的结果对象
     */
    @GetMapping("/getPositiveInfo")
    public Result<?> getPositiveInfo(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
                                     @RequestParam(name = "start_time") String start_time,
                                     @RequestParam(name = "end_time") String end_time) {
        try {
            // 从 JWT 令牌中提取用户 ID
            JWTUtil.extractID(token);
            // 将开始日期字符串转换为 Date 对象
            Date startDate = dateFormat.parse(start_time);
            // 将结束日期字符串转换为 Date 对象
            Date endDate = dateFormat.parse(end_time);
            // 调用服务层方法获取指定时间范围内的阳性核酸检测信息
            return Result.success(nucleicAcidsService.getPositiveInfoByTime(startDate, endDate));
        } catch (ParseException e) {
            // 记录日期解析错误日志
            logger.error("Date parsing error: {}", e.getMessage());
            // 返回日期格式错误的失败结果
            return Result.failed(ExceptionEnum.DATETIME_FORMAT_ERROR.getMsg());
        }
    }
}