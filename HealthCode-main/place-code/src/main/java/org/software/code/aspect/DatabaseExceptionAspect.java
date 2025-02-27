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
 * 数据库异常切面类，用于捕获并处理数据库操作中抛出的异常。
 * 该类使用 AOP 技术，在特定的数据库操作方法抛出异常时进行拦截，
 * 记录异常信息，并根据方法名抛出相应的业务异常。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Aspect
@Component
public class DatabaseExceptionAspect {

    // 初始化日志记录器，用于记录异常信息
    private static final Logger logger = LogManager.getLogger(DatabaseExceptionAspect.class);

    /**
     * 处理 PlaceInfoMapper 类中方法抛出的异常。
     * 当 PlaceInfoMapper 类中的任何方法抛出异常时，该方法会被触发。
     *
     * @param joinPoint 连接点，包含了方法执行的相关信息
     * @param ex        抛出的异常对象
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.PlaceInfoMapper.*(..))", throwing = "ex")
    public void handlePlaceInfoDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便调试
        ex.printStackTrace();

        // 获取当前执行方法的名称
        String methodName = joinPoint.getSignature().getName();

        // 记录异常信息到日志中，包括方法名和异常消息
        logger.error("Exception occurred in PlaceInfoMapper method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名判断具体的异常类型，并抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            // 如果是 insertPlace 方法抛出异常，说明插入地点信息失败
            throw new BusinessException(ExceptionEnum.PLACE_CODE_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            // 如果是 updatePlaceStatusByPid 方法抛出异常，说明更新地点状态失败
            throw new BusinessException(ExceptionEnum.PLACE_CODE_UPDATE_FAIL);
        } else {
            // 其他方法抛出异常，默认认为是查询地点信息失败
            throw new BusinessException(ExceptionEnum.PLACE_CODE_SELECT_FAIL);
        }
    }

    /**
     * 处理 PlaceMappingMapper 类中方法抛出的异常。
     * 当 PlaceMappingMapper 类中的任何方法抛出异常时，该方法会被触发。
     *
     * @param joinPoint 连接点，包含了方法执行的相关信息
     * @param ex        抛出的异常对象
     */
    @AfterThrowing(pointcut = "execution(* org.software.code.mapper.PlaceMappingMapper.*(..))", throwing = "ex")
    public void handlePlaceMappingDatabaseException(JoinPoint joinPoint, Throwable ex) {
        // 打印异常的堆栈跟踪信息，方便调试
        ex.printStackTrace();

        // 获取当前执行方法的名称
        String methodName = joinPoint.getSignature().getName();

        // 记录异常信息到日志中，包括方法名和异常消息
        logger.error("Exception occurred in PlaceMappingMapper method: {}, message: {}", methodName, ex.getMessage());

        // 根据方法名判断具体的异常类型，并抛出相应的业务异常
        if (methodName.startsWith("insert")) {
            // 如果是 addUserInfo 方法抛出异常，说明插入用户地点关联信息失败
            throw new BusinessException(ExceptionEnum.USER_PLACE_CODE_INSERT_FAIL);
        } else if (methodName.startsWith("update")) {
            // 如果是 updateUserInfo 方法抛出异常，说明更新用户地点关联信息失败
            throw new BusinessException(ExceptionEnum.USER_PLACE_CODE_UPDATE_FAIL);
        } else if (methodName.startsWith("delete")) {
            // 如果是 deleteById 方法抛出异常，说明删除用户地点关联信息失败
            throw new BusinessException(ExceptionEnum.USER_PLACE_CODE_DELETE_FAIL);
        } else {
            // 其他方法抛出异常，默认认为是查询用户地点关联信息失败
            throw new BusinessException(ExceptionEnum.USER_PLACE_CODE_SELECT_FAIL);
        }
    }
}