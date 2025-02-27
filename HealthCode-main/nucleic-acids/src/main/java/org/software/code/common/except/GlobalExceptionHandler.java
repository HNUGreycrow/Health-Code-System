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
 * 全局异常处理类，使用 @ControllerAdvice 注解，可捕获并处理控制器层抛出的各类异常。
 * 通过不同的异常处理器方法，将不同类型的异常转换为统一的响应结果，方便前端处理和用户查看。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 body 数据验证异常，通常是使用 @Valid 注解验证请求体参数时出现的异常。
     *
     * @param e MethodArgumentNotValidException 异常对象，包含验证失败的详细信息。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Result<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 获取第一个验证失败的错误信息
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 创建包含错误信息的 Result 对象
        Result<?> result = Result.failed(objectError.getDefaultMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求头参数缺失异常，当请求中缺少必要的请求头时会触发该异常处理方法。
     *
     * @param e MissingRequestHeaderException 异常对象，包含缺失的请求头名称。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Result<?>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Result<?> result;
        if (e.getHeaderName().equals("Authorization")) {
            // 如果缺失的是 Authorization 头，提示 Token 缺失
            result = Result.failed(TOKEN_NOT_FIND.getMsg());
        } else {
            // 其他请求头缺失，提示具体参数缺失信息
            String errorMessage = String.format("参数%s缺失", e.getHeaderName());
            result = Result.failed(errorMessage);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 HTTP 消息不可读异常，通常是请求体格式错误或解析失败导致的。
     *
     * @param e HttpMessageNotReadableException 异常对象。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<?>> handleMHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // 提示请求参数异常
        Result<?> result = Result.failed(REQUEST_PARAMETER_ERROR.getMsg());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理请求参数缺失异常，当请求中缺少必要的请求参数时会触发该异常处理方法。
     *
     * @param e MissingServletRequestParameterException 异常对象，包含缺失的请求参数名称。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
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
     * 处理参数空值异常，通常是使用 JSR-303 验证注解（如 @NotNull、@NotEmpty 等）时，参数为空触发的异常。
     *
     * @param e ConstraintViolationException 异常对象，包含约束违规的详细信息。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> handleConstraintViolationException(ConstraintViolationException e) {
        // 获取第一个约束违规信息
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
        // 创建包含错误信息的 Result 对象
        Result<?> result = Result.failed(violation.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 Bean 相关异常，如 Spring 容器创建 Bean 失败等情况。
     *
     * @param ex BeansException 异常对象。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BeansException.class)
    public ResponseEntity<Result<?>> handleBeansExceptions(BeansException ex) {
        // 打印异常堆栈信息，方便调试
        ex.printStackTrace();
        // 提示 Bean 数据参数异常
        return new ResponseEntity<>(Result.failed(BEAN_FORMAT_ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理服务通信异常，通常是使用 Feign 进行服务调用时出现的异常。
     *
     * @param ex FeignException 异常对象，包含服务调用失败的详细信息。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Result<?>> handleFeignExceptions(FeignException ex) {
        // 打印异常堆栈信息，方便调试
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
        // 创建包含错误信息的 Result 对象
        Result<?> result = Result.failed(resultEx.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理自定义业务异常，当业务逻辑中抛出 BusinessException 时会触发该异常处理方法。
     *
     * @param ex BusinessException 异常对象，包含业务异常的详细信息。
     * @return 包含错误信息的响应实体，状态码为 400 Bad Request。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessExceptions(BusinessException ex) {
        // 创建包含错误信息的 Result 对象
        return new ResponseEntity<>(Result.failed(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理通用异常，作为最后的异常处理手段，捕获其他未被上述处理器捕获的异常。
     *
     * @param ex Exception 异常对象。
     * @return 包含错误信息的响应实体，状态码为 500 Internal Server Error。
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleExceptions(Exception ex) {
        // 打印异常堆栈信息，方便调试
        ex.printStackTrace();
        // 提示服务执行错误
        return new ResponseEntity<>(Result.failed(RUN_EXCEPTION.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}