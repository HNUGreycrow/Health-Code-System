package org.software.code.common.result;

/**
 * IResult 是一个接口，用于定义通用结果对象应具备的基本方法。
 * 在一个系统中，当处理各种业务逻辑并返回结果时，通常需要统一结果的格式，
 * 以便前端或其他调用方能够方便地处理和解析。这个接口规定了结果对象必须包含状态码和消息这两个基本信息。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface IResult {

    /**
     * 获取结果的状态码。
     * 状态码通常用于表示操作的执行结果状态，例如成功、失败、特定的业务错误等。
     * 不同的状态码可以对应不同的业务情况，调用方可以根据状态码进行相应的处理。
     *
     * @return 表示结果状态的整数类型状态码
     */
    Integer getCode();

    /**
     * 获取结果的消息描述。
     * 消息描述用于详细说明操作的执行结果，例如成功的提示信息、失败的具体原因等。
     * 调用方可以通过消息描述了解操作的具体情况，方便进行后续处理或向用户展示。
     *
     * @return 包含结果详细信息的字符串消息
     */
    String getMessage();

}