package org.software.code.common.except;

import lombok.Getter;

/**
 * 异常枚举类，定义系统业务异常的错误码与错误信息，各服务错误码按规则区分，方便定位异常来源
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Getter
public enum ExceptionEnum {
    // 以下注释为错误码规范说明，通过开头数字快速定位对应服务的异常
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
    // 通用服务执行出错，告知用户稍后重试
    RUN_EXCEPTION("00001", "服务执行错误，请稍后重试"),
    // Token 有问题或已过期，要求用户重新登录
    TOKEN_EXPIRED("00002", "Token异常或已过期，请重新登录"),
    // 日期时间格式输入有误
    DATETIME_FORMAT_ERROR("00003", "日期时间格式错误"),
    // 传入的数据参数不符合规定
    BEAN_FORMAT_ERROR("00004", "数据参数异常"),
    // 服务之间通信出现故障，提示稍后重试
    FEIGN_EXCEPTION("00005", "服务通信异常，请稍后重试"),
    // 请求里未携带 Token
    TOKEN_NOT_FIND("00006", "没有提供Token"),
    // 请求参数存在异常情况
    REQUEST_PARAMETER_ERROR("00007", "请求参数异常"),
    // 重复定义，可能是代码编写时的遗漏，应处理
    _NOT_FIND("00006", "没有提供Token"),

    // 行程码记录插入数据库失败
    ITINERARY_CODE_INSERT_FAIL("40001", "行程码记录添加失败"),
    // 行程码记录更新操作未成功
    ITINERARY_CODE_UPDATE_FAIL("40002", "行程码记录更新失败"),
    // 行程码记录删除操作失败
    ITINERARY_CODE_DELETE_FAIL("40003", "行程码记录删除失败"),
    // 行程码记录查询操作未得到结果
    ITINERARY_CODE_SELECT_FAIL("40004", "行程码记录查询失败"),

    ;

    // 异常对应的错误码
    private String code;
    // 异常的详细错误信息
    private String msg;

    /**
     * 枚举构造函数，用于初始化错误码和错误信息
     * @param code 异常对应的错误码
     * @param msg  异常的详细错误信息
     */
    ExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}