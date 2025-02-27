package org.software.code.common.except;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.software.code.common.result.Result;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.software.code.common.except.ExceptionEnum.*;

/**
 * 全局异常处理类，使用 @ControllerAdvice 注解，用于捕获和处理控制器层抛出的各种异常。
 * 该类会将不同类型的异常转换为统一的响应结果，方便前端处理和用户查看错误信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理请求体数据验证异常。当使用 @Valid 注解对请求体进行验证，且验证不通过时，会抛出 MethodArgumentNotValidException 异常。
     *
     * @param e 方法参数验证异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Result<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 获取第一个验证错误信息
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 创建包含错误信息的结果对象
        Result<?> result = Result.failed(objectError.getDefaultMessage());
        // 返回包含结果对象和 HTTP 状态码的响应实体
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求头缺失异常。当请求中缺少必要的请求头时，会抛出 MissingRequestHeaderException 异常。
     *
     * @param e 请求头缺失异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Result<?>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Result<?> result;
        // 判断缺失的请求头是否为 Authorization
        if (e.getHeaderName().equals("Authorization")) {
            // 如果是 Authorization 头缺失，使用预定义的 Token 缺失错误信息
            result = Result.failed(TOKEN_NOT_FIND.getMsg());
        } else {
            // 其他请求头缺失，生成自定义的错误信息
            String errorMessage = String.format("参数%s缺失", e.getHeaderName());
            result = Result.failed(errorMessage);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求消息不可读异常。当请求体的格式无法被正确解析时，会抛出 HttpMessageNotReadableException 异常。
     *
     * @param e 请求消息不可读异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<?>> handleMHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // 使用预定义的请求参数错误信息创建结果对象
        Result<?> result = Result.failed(REQUEST_PARAMETER_ERROR.getMsg());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求参数缺失异常。当请求中缺少必要的请求参数时，会抛出 MissingServletRequestParameterException 异常。
     *
     * @param e 请求参数缺失异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 生成自定义的参数缺失错误信息
        String errorMessage = String.format("参数%s缺失", e.getParameterName());
        // 创建包含错误信息的结果对象
        Result<?> result = Result.failed(errorMessage);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理参数空值验证异常。当使用 @NotNull、@NotEmpty 等注解对参数进行验证，且参数为空时，会抛出 ConstraintViolationException 异常。
     *
     * @param e 参数空值验证异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> handleConstraintViolationException(ConstraintViolationException e) {
        // 获取第一个约束违规信息
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
        // 创建包含错误信息的结果对象
        Result<?> result = Result.failed(violation.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 Bean 相关异常。当 Spring 容器在创建、管理 Bean 过程中出现异常时，会抛出 BeansException 异常。
     *
     * @param ex Bean 异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BeansException.class)
    public ResponseEntity<Result<?>> handleBeansExceptions(BeansException ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        // 使用预定义的 Bean 格式错误信息创建结果对象
        return new ResponseEntity<>(Result.failed(BEAN_FORMAT_ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 Feign 调用异常。当使用 Feign 进行服务间调用出现异常时，会抛出 FeignException 异常。
     *
     * @param ex Feign 异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Result<?>> handleFeignExceptions(FeignException ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        Result<?> resultEx;
        try {
            // 创建 ObjectMapper 对象用于解析 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            // 尝试将 Feign 异常的响应内容解析为 Result 对象
            resultEx = objectMapper.readValue(ex.contentUTF8(), Result.class);
        } catch (Exception e) {
            // 解析失败，打印异常堆栈信息，并使用预定义的服务通信错误信息创建结果对象
            e.printStackTrace();
            return new ResponseEntity<>(Result.failed(FEIGN_EXCEPTION.getMsg()), HttpStatus.BAD_REQUEST);
        }
        // 使用解析得到的错误信息创建结果对象
        Result<?> result = Result.failed(resultEx.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理自定义业务异常。当业务逻辑中抛出 BusinessException 异常时，会进入此方法进行处理。
     *
     * @param ex 自定义业务异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessExceptions(BusinessException ex) {
        // 使用业务异常的错误信息创建结果对象
        return new ResponseEntity<>(Result.failed(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理通用异常。当抛出的异常不属于上述任何一种类型时，会进入此方法进行处理。
     *
     * @param ex 通用异常对象
     * @return 返回包含错误信息的响应实体，HTTP 状态码为 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleExceptions(Exception ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        // 使用预定义的服务执行错误信息创建结果对象
        return new ResponseEntity<>(Result.failed(RUN_EXCEPTION.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}