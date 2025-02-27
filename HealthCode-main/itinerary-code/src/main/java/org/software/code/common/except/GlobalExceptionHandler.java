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
 * 全局异常处理类，使用 @ControllerAdvice 注解捕获并处理控制器层抛出的各种异常。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 body 数据验证异常（如 @Valid 注解验证失败）。
     * @param e MethodArgumentNotValidException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Result<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 获取第一个验证错误信息
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 封装错误信息到 Result 对象
        Result<?> result = Result.failed(objectError.getDefaultMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求头参数缺失异常。
     * @param e MissingRequestHeaderException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Result<?>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Result<?> result;
        if (e.getHeaderName().equals("Authorization")) {
            // 若缺失的是 Authorization 头，提示 Token 缺失
            result = Result.failed(TOKEN_NOT_FIND.getMsg());
        } else {
            // 其他请求头缺失，提示具体参数缺失信息
            String errorMessage = String.format("参数%s缺失", e.getHeaderName());
            result = Result.failed(errorMessage);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 HTTP 消息不可读异常，通常是请求参数格式错误。
     * @param e HttpMessageNotReadableException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<?>> handleMHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // 提示请求参数异常
        Result<?> result = Result.failed(REQUEST_PARAMETER_ERROR.getMsg());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求参数缺失异常。
     * @param e MissingServletRequestParameterException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 提示具体参数缺失信息
        String errorMessage = String.format("参数%s缺失", e.getParameterName());
        Result<?> result = Result.failed(errorMessage);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理参数空值异常（如 @NotNull 注解验证失败）。
     * @param e ConstraintViolationException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> handleConstraintViolationException(ConstraintViolationException e) {
        // 获取第一个约束违规信息
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
        // 封装错误信息到 Result 对象
        Result<?> result = Result.failed(violation.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 Bean 相关异常，如 Bean 创建失败等。
     * @param ex BeansException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BeansException.class)
    public ResponseEntity<Result<?>> handleBeansExceptions(BeansException ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        // 提示 Bean 数据参数异常
        return new ResponseEntity<>(Result.failed(BEAN_FORMAT_ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理服务通信异常（如 Feign 调用失败）。
     * @param ex FeignException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Result<?>> handleFeignExceptions(FeignException ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        Result<?> resultEx;
        try {
            // 尝试从异常响应内容中解析出 Result 对象
            ObjectMapper objectMapper = new ObjectMapper();
            resultEx = objectMapper.readValue(ex.contentUTF8(), Result.class);
        } catch (Exception e) {
            // 解析失败，提示服务通信异常
            e.printStackTrace();
            return new ResponseEntity<>(Result.failed(FEIGN_EXCEPTION.getMsg()), HttpStatus.BAD_REQUEST);
        }
        // 封装解析出的错误信息到 Result 对象
        Result<?> result = Result.failed(resultEx.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理自定义业务异常。
     * @param ex BusinessException 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessExceptions(BusinessException ex) {
        // 封装业务异常信息到 Result 对象
        return new ResponseEntity<>(Result.failed(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理通用异常，作为最后的异常处理手段。
     * @param ex Exception 异常对象
     * @return 包含错误信息的响应实体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleExceptions(Exception ex) {
        // 打印异常堆栈信息
        ex.printStackTrace();
        // 提示服务执行错误
        return new ResponseEntity<>(Result.failed(RUN_EXCEPTION.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}