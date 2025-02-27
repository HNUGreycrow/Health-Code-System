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
 * 数据库异常切面类，用于捕获并处理 NucleicAcidTestMapper 中方法抛出的异常。
 * 通过 AOP 机制，在指定的方法抛出异常后进行统一处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Aspect
@Component
public class DatabaseExceptionAspect {

    // 创建日志记录器，用于记录异常信息
    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 当 NucleicAcidTestMapper 中的 MyBatis-Plus 通用方法抛出异常时，此方法会被触发。
     * 它会记录异常信息，并根据具体的方法名抛出相应的业务异常。
     *
     * @param joinPoint 切入点对象，可获取当前执行方法的相关信息
     * @param ex        抛出的异常对象
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.NucleicAcidTestMapper.*(..))", throwing = "ex")
    public void handleNucleicAcidTestMapperDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便调试定位问题
        ex.printStackTrace();

        // 从切入点中获取当前执行方法的名称
        String methodName = joinPoint.getSignature().getName();

        // 使用日志记录器记录发生异常的方法名和异常信息
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名，抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            // 如果是插入相关的方法抛出异常，意味着核酸检测记录插入失败，抛出对应的业务异常
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            // 如果是更新相关的方法抛出异常，认为核酸检测记录更新失败，抛出对应业务异常
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_UPDATE_FAIL);
        } else if (methodName.startsWith("delete")) {
            // 如果是删除相关的方法抛出异常，认为核酸检测记录删除失败，抛出对应业务异常
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_DELETE_FAIL);
        } else {
            // 其他方法（主要是查询相关）抛出异常，判定为核酸检测记录查询失败，抛出相应业务异常
            throw new BusinessException(ExceptionEnum.NUCLEIC_ACID_TEST_SELECT_FAIL);
        }
    }
}