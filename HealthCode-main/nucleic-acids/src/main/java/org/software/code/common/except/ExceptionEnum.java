package org.software.code.common.except;

import lombok.Getter;

/**
 * 异常枚举类，定义了系统中各类业务异常的错误码和对应的错误信息。
 * 通过统一的错误码规范，能快速定位异常发生的服务模块，方便系统的调试和维护。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Getter
public enum ExceptionEnum {
    /**
     * 错误码规范，便于通过错误码快速定位服务
     * 0、通用服务错误码统一以 0 开头
     * 1、gateway   服务错误码统一以 1 开头
     * 2、user   服务错误码统一以 2 开头
     * 3、health - code    服务错误码统一以 3 开头
     * 4、itinerary - code  服务错误码统一以 4 开头
     * 5、nucleic - acids  服务错误码统一以 5 开头
     * 6、place - code  服务错误码统一以 6 开头
     */
    // 通用服务执行错误，提示用户稍后重试
    RUN_EXCEPTION("00001", "服务执行错误，请稍后重试"),
    // Token 异常或过期，需用户重新登录
    TOKEN_EXPIRED("00002", "Token异常或已过期，请重新登录"),
    // 日期时间格式不符合要求
    DATETIME_FORMAT_ERROR("00003", "日期时间格式错误"),
    // 数据参数不符合预期
    BEAN_FORMAT_ERROR("00004", "数据参数异常"),
    // 服务间通信出现问题
    FEIGN_EXCEPTION("00005", "服务通信异常，请稍后重试"),
    // 请求中未提供 Token
    TOKEN_NOT_FIND("00006", "没有提供Token"),
    // 请求参数存在异常
    REQUEST_PARAMETER_ERROR("00007", "请求参数异常"),

    // 核酸记录插入数据库失败
    NUCLEIC_ACID_TEST_INSERT_FAIL("50001", "核酸记录添加失败"),
    // 核酸记录更新操作未成功
    NUCLEIC_ACID_TEST_UPDATE_FAIL("50002", "核酸记录更新失败"),
    // 核酸记录删除操作失败
    NUCLEIC_ACID_TEST_DELETE_FAIL("50003", "核酸记录删除失败"),
    // 核酸记录查询操作未得到结果
    NUCLEIC_ACID_TEST_SELECT_FAIL("50004", "核酸记录查询失败"),
    // 未找到地区风险计算策略
    RISK_CALCULATION_NOT_FIND("50005", "地区风险策略不存在"),
    // 通知用户进行复检时出现异常
    RETEST_NOTIFICATION_EXCEPTION("50006", "通知复检时异常"),

    ;

    // 异常对应的错误码
    private String code;

    // 异常的详细错误信息
    private String msg;

    /**
     * 枚举构造方法，用于初始化错误码和错误信息。
     *
     * @param code 异常的错误码
     * @param msg  异常的详细错误信息
     */
    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}