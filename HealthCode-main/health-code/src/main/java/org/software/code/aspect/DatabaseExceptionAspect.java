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
 * @author “101”计划《软件工程》实践教材案例团队
 */
// @Aspect 注解表明这是一个切面类，Spring AOP 会识别并处理该类中的通知方法。
@Aspect
// @Component 注解将该类标记为 Spring 组件，以便 Spring 容器自动扫描并管理该类的实例。
@Component
public class DatabaseExceptionAspect {

    // 创建一个 Logger 实例，用于记录日志，日志记录的类为当前 DatabaseExceptionAspect 类。
    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 该方法是一个异常抛出后通知，当满足特定切入点条件的方法抛出异常时会执行此方法。
     *
     * @param joinPoint 切入点对象，包含了方法调用的相关信息，如方法名、参数等。
     * @param ex 抛出的异常对象，包含了异常的详细信息。
     */
    // @AfterThrowing 注解表示这是一个异常抛出后通知。
    // pointcut 属性指定了切入点表达式，这里表示匹配 org.software.code.mapper.HealthCodeMapper 包下所有继承 BaseMapper 的接口中的所有方法。
    // throwing 属性指定了异常对象的名称，用于在方法参数中接收抛出的异常。
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.HealthCodeMapper.*(..))", throwing = "ex")
    public void handleDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便开发人员在控制台查看异常的详细调用栈。
        ex.printStackTrace();

        // 通过 JoinPoint 对象获取当前执行方法的签名，进而获取方法名称。
        String methodName = joinPoint.getSignature().getName();

        // 使用 Logger 记录异常发生的方法名和异常消息，便于后续的问题排查和日志分析。
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据不同的方法名，抛出相应的业务异常，将底层的数据库异常转换为业务层面的异常。
        if (methodName.startsWith("insert")) {
            // 如果是插入相关方法抛出异常，抛出 HEALTH_CODE_INSERT_FAIL 业务异常。
            throw new BusinessException(ExceptionEnum.HEALTH_CODE_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            // 如果是相关更新方法抛出异常，抛出 HEALTH_CODE_UPDATE_FAIL 业务异常。
            throw new BusinessException(ExceptionEnum.HEALTH_CODE_UPDATE_FAIL);
        } else {
            // 对于其他方法抛出的异常，统一抛出 HEALTH_CODE_SELECT_FAIL 业务异常。
            throw new BusinessException(ExceptionEnum.HEALTH_CODE_SELECT_FAIL);
        }
    }

    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.AppealLogMapper.*(..)) && target(com.baomidou.mybatisplus.core.mapper.BaseMapper)", throwing = "ex")
    public void handleAppealLogDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便开发人员在控制台查看异常的详细调用栈。
        ex.printStackTrace();

        // 通过 JoinPoint 对象获取当前执行方法的签名，进而获取方法名称。
        String methodName = joinPoint.getSignature().getName();

        // 使用 Logger 记录异常发生的方法名和异常消息，便于后续的问题排查和日志分析。
        logger.error("Exception occurred in method: {}, message: {}", methodName, ex.getMessage());

        // 根据不同的方法名，抛出相应的业务异常，将底层的数据库异常转换为业务层面的异常。
        if (methodName.startsWith("insert")) {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_UPDATE_FAIL);
        } else if (methodName.startsWith("delete")) {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_DELETE_FAIL);
        }else {
            throw new BusinessException(ExceptionEnum.APPEAL_LOG_SELECT_FAIL);
        }
    }
}