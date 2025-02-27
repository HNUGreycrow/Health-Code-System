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
 * 数据库异常切面类，用于捕获并处理特定数据库操作相关的异常。
 * 该类利用 AOP（面向切面编程）的机制，在指定的 Mapper 方法抛出异常后进行统一处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Aspect
@Component
public class DatabaseExceptionAspect {

    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 处理疫苗接种点数据库操作异常的方法。
     * 当 org.software.code.mapper.VaccinationSiteMapper 类中的任何方法抛出异常时，此方法会被触发。
     * 它会记录异常信息，并根据具体的方法名抛出相应的业务异常。
     *
     * @param joinPoint 切入点对象，包含了方法调用的相关信息，如方法名、参数等。
     * @param ex 抛出的异常对象，包含了异常的详细信息。
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.VaccinationSiteMapper.*(..))", throwing = "ex")
    public void handleVaccinationSiteDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印堆栈跟踪
        ex.printStackTrace();

        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();

        // 记录异常信息
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.VACCINATION_SITE_INSERT_FAIL);
        } else if (methodName.startsWith("delete")) {
            throw new BusinessException(ExceptionEnum.VACCINATION_SITE_DELETE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.VACCINATION_SITE_SELECT_FAIL);
        }
    }

    /**
     * 处理疫苗接种记录数据库操作异常的方法。
     * 当 org.software.code.mapper.VaccinationRecordMapper 类中的任何方法抛出异常时，此方法会被触发。
     * 它会记录异常信息，并根据具体的方法名抛出相应的业务异常。
     *
     * @param joinPoint 切入点对象，包含了方法调用的相关信息，如方法名、参数等。
     * @param ex 抛出的异常对象，包含了异常的详细信息。
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.VaccinationRecordMapper.*(..))", throwing = "ex")
    public void handleVaccinationRecordDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印堆栈跟踪
        ex.printStackTrace();

        // 获取方法名称
        String methodName = joinPoint.getSignature().getName();

        // 记录异常信息
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.VACCINATION_RECORD_INSERT_FAIL);
        } else if (methodName.startsWith("delete")) {
            throw new BusinessException(ExceptionEnum.VACCINATION_RECORD_DELETE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.VACCINATION_RECORD_SELECT_FAIL);
        }
    }
}