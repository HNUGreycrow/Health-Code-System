package org.software.code.common.except;

/**
 * 自定义业务异常类，继承自 RuntimeException，用于在业务逻辑中抛出特定的业务异常。
 * 当业务处理过程中出现不符合预期的情况时，可以使用该异常类来封装错误信息，
 * 包括错误码和错误消息，方便统一处理业务异常。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public class BusinessException extends RuntimeException {

    /**
     * 业务异常的错误码，用于标识不同类型的业务异常，方便后续根据错误码进行不同的处理。
     */
    private String code;

    /**
     * 业务异常的错误消息，用于详细描述业务异常的具体信息，便于开发人员和运维人员定位问题。
     */
    private String msg;

    /**
     * 构造函数，通过传入错误码和错误消息来创建业务异常对象。
     *
     * @param code 业务异常的错误码，用于标识异常类型。
     * @param msg  业务异常的详细错误消息，描述异常发生的具体情况。
     */
    public BusinessException(String code, String msg) {
        // 调用父类 RuntimeException 的构造函数，传入错误消息
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数，通过传入枚举类型的异常信息来创建业务异常对象。
     * 这种方式可以将常见的业务异常信息封装在枚举中，方便统一管理和使用。
     *
     * @param exceptionEnum 包含错误码和错误消息的枚举对象，定义了常见的业务异常信息。
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        // 调用父类 RuntimeException 的构造函数，传入枚举对象中的错误消息
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    /**
     * 获取业务异常的错误码。
     *
     * @return 业务异常的错误码，用于标识异常类型。
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取业务异常的错误消息。
     *
     * @return 业务异常的详细错误消息，描述异常发生的具体情况。
     */
    public String getMsg() {
        return msg;
    }
}