package org.software.code.common.result;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
public enum ResultEnum implements IResult {


    SUCCESS(200, "成功"),

    FAILED(400, "失败");

    private Integer code;

    private String message;

    ResultEnum() {
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}