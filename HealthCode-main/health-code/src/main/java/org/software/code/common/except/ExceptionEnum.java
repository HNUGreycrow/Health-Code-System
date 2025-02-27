package org.software.code.common.except;

import lombok.Getter;

/**
 * ExceptionEnum 是一个枚举类，用于定义系统中各种业务异常的错误码和对应的错误信息。
 * 通过使用枚举类，可以将错误码和错误信息集中管理，便于维护和扩展，同时也能提高代码的可读性。
 * 每个枚举常量代表一种特定的异常情况，错误码的设计遵循一定的规范，便于快速定位出现问题的服务。
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
     * 3、health-code    服务错误码统一以 3 开头
     * 4、itinerary-code  服务错误码统一以 4 开头
     * 5、nucleic-acids  服务错误码统一以 5 开头
     * 6、place-code  服务错误码统一以 6 开头
     * 7、vaccine-inoculation   服务错误码统一以 7 开头
     */

    // 通用的服务执行异常，当服务执行过程中出现未知错误时抛出
    RUN_EXCEPTION("00001", "服务执行错误，请稍后重试"),
    // Token 相关异常，当 Token 无效或过期时抛出
    TOKEN_EXPIRED("00002", "Token异常或已过期，请重新登录"),
    // 日期时间格式异常，当输入的日期时间格式不符合要求时抛出
    DATETIME_FORMAT_ERROR("00003", "日期时间格式错误"),
    // 数据参数异常，当传入的数据参数不符合预期时抛出
    BEAN_FORMAT_ERROR("00004", "数据参数异常"),
    // 服务通信异常，当服务之间调用出现问题时抛出
    FEIGN_EXCEPTION("00005", "服务通信异常，请稍后重试"),
    // Token 缺失异常，当请求中没有提供 Token 时抛出
    TOKEN_NOT_FIND("00006", "没有提供Token"),
    // 请求参数异常，当请求参数不完整或格式错误时抛出
    REQUEST_PARAMETER_ERROR("00007", "请求参数异常"),

    // 健康码已存在异常，当尝试创建已存在的健康码时抛出
    HEALTH_CODE_EXIST("30001", "健康码已存在"),
    // 健康码不存在异常，当查询或操作不存在的健康码时抛出
    HEALTH_CODE_NOT_FIND("30002", "健康码不存在，请申请"),
    // 健康码添加失败异常，当向数据库中添加健康码信息失败时抛出
    HEALTH_CODE_INSERT_FAIL("30003", "健康码添加失败"),
    // 健康码更新失败异常，当更新健康码信息失败时抛出
    HEALTH_CODE_UPDATE_FAIL("30004", "健康码更新失败"),
    // 健康码删除失败异常，当删除健康码信息失败时抛出
    HEALTH_CODE_DELETE_FAIL("30005", "健康码删除失败"),
    // 健康码查询失败异常，当查询健康码信息失败时抛出
    HEALTH_CODE_SELECT_FAIL("30006", "健康码查询失败"),
    // 健康码事件无效异常，当触发的健康码事件不符合规则时抛出
    HEALTH_CODE_EVENT_INVALID("30007", "健康码事件无效"),

    // 申诉相关异常
    APPEAL_LOG_UPDATE_FAIL("30008", "申诉更新失败"),
    APPEAL_LOG_DELETE_FAIL("30009", "申诉删除失败"),
    APPEAL_LOG_SELECT_FAIL("30010", "申诉查询失败"),
    APPEAL_LOG_INSERT_FAIL("30011", "申诉添加失败"),

    ;

    /**
     * 错误码，用于唯一标识一种异常情况
     */
    private String code;

    /**
     * 错误信息，用于详细描述异常情况，方便开发人员和用户理解错误原因
     */
    private String msg;

    /**
     * 枚举类的构造函数，用于初始化每个枚举常量的错误码和错误信息
     *
     * @param code 错误码
     * @param msg  错误信息
     */
    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}