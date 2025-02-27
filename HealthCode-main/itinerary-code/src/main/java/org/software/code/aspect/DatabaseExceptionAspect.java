package org.software.code.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.software.code.common.except.BusinessException;
import org.software.code.common.except.ExceptionEnum;
import org.springframework.stereotype.Component;

/**
 * 数据库异常切面类，用于捕获并处理 ItineraryCodeMapper 中方法抛出的异常
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Aspect
@Component
public class DatabaseExceptionAspect {

    // 创建一个日志记录器，用于记录异常信息
    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 当 ItineraryCodeMapper 中的方法抛出异常时，该方法会被触发
     *
     * @param joinPoint 切入点对象，可用于获取方法的相关信息
     * @param ex        抛出的异常对象
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.ItineraryCodeMapper.*(..))", throwing = "ex")
    public void handleItineraryCodeDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便调试
        ex.printStackTrace();

        // 从切入点中获取当前执行方法的名称
        String methodName = joinPoint.getSignature().getName();

        // 使用日志记录器记录发生异常的方法名和异常信息
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名，抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            // 如果是插入相关方法抛出异常，抛出插入行程代码失败的业务异常
            throw new BusinessException(ExceptionEnum.ITINERARY_CODE_INSERT_FAIL);
        } else if (methodName.startsWith("delete")) {
            // 如果是删除相关方法抛出异常，抛出删除行程代码失败的业务异常
            throw new BusinessException(ExceptionEnum.ITINERARY_CODE_DELETE_FAIL);
        } else {
            // 其他方法抛出异常，抛出查询行程代码失败的业务异常
            throw new BusinessException(ExceptionEnum.ITINERARY_CODE_SELECT_FAIL);
        }
    }
}