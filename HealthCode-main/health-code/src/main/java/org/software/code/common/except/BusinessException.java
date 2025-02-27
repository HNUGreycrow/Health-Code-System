package org.software.code.common.except;

import lombok.Getter;

/**
 * BusinessException 是一个自定义的运行时异常类，用于处理业务层面的异常情况。
 * 当业务逻辑执行过程中出现不符合预期的情况时，可以抛出该异常，
 * 它继承自 RuntimeException，意味着在代码中不需要显式地捕获或声明抛出该异常。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Getter
public class BusinessException extends RuntimeException {

    // 异常的错误码，用于标识具体的异常类型，方便在处理异常时进行区分和定位问题
    private String code;

    // 异常的错误消息，用于详细描述异常发生的原因，便于开发人员和运维人员理解问题
    private String msg;

    /**
     * 构造函数，通过传入错误码和错误消息来创建 BusinessException 实例。
     *
     * @param code 异常的错误码
     * @param msg  异常的错误消息
     */
    public BusinessException(String code, String msg) {
        // 调用父类 RuntimeException 的构造函数，将错误消息传递给父类，用于异常堆栈信息的输出
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数，通过传入 ExceptionEnum 枚举类型的对象来创建 BusinessException 实例。
     * ExceptionEnum 枚举通常包含了预定义的异常类型及其对应的错误码和错误消息。
     *
     * @param exceptionEnum 包含错误码和错误消息的枚举对象
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        // 调用父类 RuntimeException 的构造函数，将枚举对象中的错误消息传递给父类
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

}