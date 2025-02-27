package org.software.code.common.except;

/**
 * 业务异常类，继承自 RuntimeException，用于在业务逻辑处理过程中抛出特定的业务异常。
 * 该异常包含错误码和错误信息，方便在异常处理时提供详细的错误描述。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码，用于标识不同类型的业务异常
     */
    private String code;

    /**
     * 错误信息，用于描述业务异常的具体内容
     */
    private String msg;

    /**
     * 构造函数，通过传入错误码和错误信息创建业务异常对象
     *
     * @param code 错误码
     * @param msg  错误信息
     */
    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数，通过传入异常枚举对象创建业务异常对象
     *
     * @param exceptionEnum 异常枚举对象，包含错误码和错误信息
     */
    public BusinessException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getMsg() {
        return msg;
    }
}
