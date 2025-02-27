package org.software.code.common.except;

import lombok.Getter;

/**
 * 业务异常类，继承自 RuntimeException，用于在业务逻辑处理过程中抛出特定的异常。
 * 当业务逻辑出现不符合预期的情况时，可以使用该类封装错误码和错误信息抛出异常，
 * 方便对不同业务场景下的异常进行分类处理和统一管理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Getter
public class BusinessException extends RuntimeException {

    // 异常对应的错误码，用于标识不同类型的业务异常
    private String code;

    // 异常的详细错误信息，用于描述异常发生的具体情况
    private String msg;

    /**
     * 构造方法，通过传入错误码和错误信息创建业务异常对象。
     *
     * @param code 异常的错误码
     * @param msg  异常的详细错误信息
     */
    public BusinessException(String code, String msg) {
        // 调用父类 RuntimeException 的构造方法，将错误信息传递给父类
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造方法，通过传入异常枚举类对象创建业务异常对象。
     *
     * @param exceptionEnum 异常枚举类对象，包含了预定义的错误码和错误信息
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        // 调用父类 RuntimeException 的构造方法，将枚举类中的错误信息传递给父类
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

}