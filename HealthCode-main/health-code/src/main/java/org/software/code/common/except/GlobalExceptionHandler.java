package org.software.code.common.except;

// 静态导入 ExceptionEnum 中的部分枚举常量，方便在代码中直接使用这些常量

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
 * GlobalExceptionHandler 是一个全局异常处理类，用于捕获和处理控制器层抛出的各种异常。
 * 通过使用 @ControllerAdvice 注解，Spring 会自动扫描该类，并将其应用到所有的控制器中。
 * 不同的 @ExceptionHandler 方法用于处理不同类型的异常，确保在出现异常时能给客户端返回统一格式的错误响应。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 MethodArgumentNotValidException 异常，该异常通常在 @Valid 注解验证请求体参数失败时抛出。
     *
     * @param e MethodArgumentNotValidException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Result<?>> handleValidationExceptions(MethodArgumentNotValidException e) {
        // 获取第一个验证失败的错误信息
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 创建一个包含错误信息的 Result 对象
        Result<?> result = Result.failed(objectError.getDefaultMessage());
        // 返回包含 Result 对象的 ResponseEntity，状态码为 400
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 MissingRequestHeaderException 异常，该异常在请求中缺少必需的请求头时抛出。
     *
     * @param e MissingRequestHeaderException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Result<?>> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        Result<?> result;
        // 判断缺失的请求头是否为 Authorization
        if (e.getHeaderName().equals("Authorization")) {
            // 如果是 Authorization 头缺失，使用预定义的 TOKEN_NOT_FIND 错误信息
            result = Result.failed(TOKEN_NOT_FIND.getMsg());
        } else {
            // 否则，生成一个包含缺失头名称的错误信息
            String errorMessage = String.format("参数%s缺失", e.getHeaderName());
            result = Result.failed(errorMessage);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 HttpMessageNotReadableException 异常，该异常在请求体解析失败时抛出。
     *
     * @param e HttpMessageNotReadableException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<?>> handleMHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        // 使用预定义的 REQUEST_PARAMETER_ERROR 错误信息创建 Result 对象
        Result<?> result = Result.failed(REQUEST_PARAMETER_ERROR.getMsg());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 MissingServletRequestParameterException 异常，该异常在请求中缺少必需的请求参数时抛出。
     *
     * @param e MissingServletRequestParameterException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Result<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 生成一个包含缺失参数名称的错误信息
        String errorMessage = String.format("参数%s缺失", e.getParameterName());
        Result<?> result = Result.failed(errorMessage);
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 ConstraintViolationException 异常，该异常在方法参数的约束验证失败时抛出。
     *
     * @param e ConstraintViolationException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Result<?>> handleConstraintViolationException(ConstraintViolationException e) {
        // 获取第一个约束验证失败的错误信息
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
        Result<?> result = Result.failed(violation.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 BeansException 异常，该异常在 Spring Bean 创建、初始化等过程中出现错误时抛出。
     *
     * @param ex BeansException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BeansException.class)
    public ResponseEntity<Result<?>> handleBeansExceptions(BeansException ex) {
        // 打印异常堆栈信息，方便调试
        ex.printStackTrace();
        // 使用预定义的 BEAN_FORMAT_ERROR 错误信息创建 Result 对象
        return new ResponseEntity<>(Result.failed(BEAN_FORMAT_ERROR.getMsg()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 FeignException 异常，该异常在使用 Feign 进行服务间调用失败时抛出。
     *
     * @param ex FeignException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Result<?>> handleFeignExceptions(FeignException ex) {
        // 打印异常堆栈信息，方便调试
        ex.printStackTrace();
        Result<?> resultEx;
        try {
            // 创建一个 ObjectMapper 用于解析异常响应的 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            // 将异常响应的内容解析为 Result 对象
            resultEx = objectMapper.readValue(ex.contentUTF8(), Result.class);
        } catch (Exception e) {
            // 如果解析过程中出现异常，打印异常信息并使用预定义的 FEIGN_EXCEPTION 错误信息
            e.printStackTrace();
            return new ResponseEntity<>(Result.failed(FEIGN_EXCEPTION.getMsg()), HttpStatus.BAD_REQUEST);
        }
        // 使用解析后的 Result 对象中的错误信息创建新的 Result 对象
        Result<?> result = Result.failed(resultEx.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理 BusinessException 异常，这是自定义的业务异常。
     *
     * @param ex BusinessException 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 400 Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<?>> handleBusinessExceptions(BusinessException ex) {
        // 使用业务异常中的错误信息创建 Result 对象
        return new ResponseEntity<>(Result.failed(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 处理未被其他异常处理方法捕获的通用异常。
     *
     * @param ex Exception 异常对象
     * @return 返回一个包含错误信息的 ResponseEntity，HTTP 状态码为 500 Internal Server Error
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleExceptions(Exception ex) {
        // 打印异常堆栈信息，方便调试
        ex.printStackTrace();
        // 使用预定义的 RUN_EXCEPTION 错误信息创建 Result 对象
        return new ResponseEntity<>(Result.failed(RUN_EXCEPTION.getMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}