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
 * DatabaseExceptionAspect 是一个切面类，用于处理数据库操作相关的异常。
 * 当指定的数据库操作方法抛出异常时，该切面会捕获异常并进行统一处理，
 * 同时根据不同的方法名抛出相应的业务异常。
 *
 * @author  “101”计划《软件工程》实践教材案例团队
 */
@Aspect
@Component
public class DatabaseExceptionAspect {

    // 创建一个日志记录器，用于记录数据库操作中发生的异常信息
    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 处理 UserInfoMapper 数据库操作异常
     * 切入点：拦截 UserInfoMapper 所有方法
     * 异常处理：根据方法名前缀抛出对应业务异常
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.UserInfoMapper.*(..))", throwing = "ex")
    public void handleUserInfoDatabaseException(JoinPoint joinPoint, Throwable ex) {
        ex.printStackTrace();
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception occurred in UserInfoMapper method: {}, message: {}", methodName, ex.getMessage());

        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.USER_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            throw new BusinessException(ExceptionEnum.USER_UPDATE_FAIL);
        } else if (methodName.startsWith("delete")) {
            throw new BusinessException(ExceptionEnum.USER_DELETE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.USER_SELECT_FAIL);
        }
    }

    /**
     * 处理 UidMappingMapper 数据库操作异常
     * 切入点：拦截 UidMappingMapper 所有方法
     * 异常处理：根据方法名前缀抛出对应业务异常
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.UidMappingMapper.*(..))", throwing = "ex")
    public void handleUidMappingDatabaseException(JoinPoint joinPoint, Throwable ex) {
        ex.printStackTrace();
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception occurred in UidMappingMapper method: {}, message: {}", methodName, ex.getMessage());

        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.USER_BIND_INSERT_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.USER_BIND_SELECT_FAIL);
        }
    }

    /**
     * 处理 NucleicAcidTestPersonnelMapper 数据库操作异常
     * 切入点：拦截 NucleicAcidTestPersonnelMapper 所有方法
     * 异常处理：根据方法名前缀抛出对应业务异常
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.NucleicAcidTestPersonnelMapper.*(..))", throwing = "ex")
    public void handleAcidTestPersonnelDatabaseException(JoinPoint joinPoint, Throwable ex) {
        ex.printStackTrace();
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception occurred in NucleicAcidTestPersonnelMapper method: {}, message: {}", methodName, ex.getMessage());

        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_USER_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_USER_UPDATE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_USER_SELECT_FAIL);
        }
    }

    /**
     * 处理 HealthCodeManagerMapper 数据库操作异常
     * 切入点：拦截 HealthCodeManagerMapper 所有方法
     * 异常处理：根据方法名前缀抛出对应业务异常
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.HealthCodeManagerMapper.*(..))", throwing = "ex")
    public void handleHealthCodeMangerDatabaseException(JoinPoint joinPoint, Throwable ex) {
        ex.printStackTrace();
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception occurred in HealthCodeMangerMapper method: {}, message: {}", methodName, ex.getMessage());

        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.MANAGER_USER_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            throw new BusinessException(ExceptionEnum.MANAGER_USER_UPDATE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.MANAGER_USER_SELECT_FAIL);
        }
    }

    /**
     * 处理 AreaCodeMapper 数据库操作异常
     * 切入点：拦截 AreaCodeMapper 所有方法
     * 异常处理：根据方法名前缀抛出对应业务异常
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.AreaCodeMapper.*(..))", throwing = "ex")
    public void handleAreaCodeMangerDatabaseException(JoinPoint joinPoint, Throwable ex) {
        ex.printStackTrace();
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception occurred in HealthCodeMangerMapper method: {}, message: {}", methodName, ex.getMessage());

        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.AREA_CODE_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            throw new BusinessException(ExceptionEnum.AREA_CODE_UPDATE_FAIL);
        } else if (methodName.startsWith("delete")) {
            throw new BusinessException(ExceptionEnum.AREA_CODE_DELETE_FAIL);
        } else {
            throw new BusinessException(ExceptionEnum.AREA_CODE_SELECT_FAIL);
        }
    }
}