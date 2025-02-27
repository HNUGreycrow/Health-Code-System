package org.software.code.common.except;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
public enum ExceptionEnum {
    /**
     * 错误码规范，便于通过错误码快速定位服务
     * 0、通用服务错误码统一以 0 开头
     * 1、gateway   服务错误码统一以 1 开头
     * 2、user   服务错误码统一以 2 开头
     * 3、health-code    服务错误码统一以 3 开头
     * 4、itinerary-code  服务错误码统一以 4 开头
     * 5、nucleic-acids  服务错误码统一以 5 开头
     * 6、place-code  服务错误码统一以 6 开头
     * 7、vaccine-inoculation   服务错误码统一以 7 开头
     */
    RUN_EXCEPTION("00001", "服务执行错误，请稍后重试"),
    TOKEN_EXPIRED("00002", "Token异常或已过期，请重新登录"),
    DATETIME_FORMAT_ERROR("00003", "日期时间格式错误"),
    BEAN_FORMAT_ERROR("00004", "数据参数异常"),
    FEIGN_EXCEPTION("00005", "服务通信异常，请稍后重试"),
    TOKEN_NOT_FIND("00006", "没有提供Token"),
    REQUEST_PARAMETER_ERROR("00007", "请求参数异常"),


    VACCINATION_SITE_INSERT_FAIL("70001", "疫苗接种站点添加失败"),
    VACCINATION_SITE_UPDATE_FAIL("70002", "疫苗接种站点更新失败"),
    VACCINATION_SITE_DELETE_FAIL("70003", "疫苗接种站点删除失败"),
    VACCINATION_SITE_SELECT_FAIL("70004", "疫苗接种站点查询失败"),

    VACCINATION_RECORD_INSERT_FAIL("70005", "疫苗接种站记录添加失败"),
    VACCINATION_RECORD_UPDATE_FAIL("70006", "疫苗接种记录更新失败"),
    VACCINATION_RECORD_DELETE_FAIL("70007", "疫苗接种记录删除失败"),
    VACCINATION_RECORD_SELECT_FAIL("70008", "疫苗接种记录查询失败"),

    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;


    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
