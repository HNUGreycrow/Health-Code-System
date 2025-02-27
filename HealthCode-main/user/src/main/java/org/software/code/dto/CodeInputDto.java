package org.software.code.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * CodeInputDto 是一个数据传输对象（DTO），专门用于封装微信小程序登录 API 返回的登录凭证 code。
 * 该类主要在前后端数据交互过程中，负责接收和传递从微信小程序端获取的登录 code，以便后端进行后续的登录验证、用户身份识别等业务逻辑处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class CodeInputDto {
    /**
     * 微信小程序登录 API 返回的登录凭证 code 字符串，此属性为必填项。
     * 在微信小程序的登录流程中，调用登录 API 后会返回一个临时的 code，该 code 可用于向微信服务器换取用户的唯一标识（openid）等信息。
     * 在进行 JSON 序列化和反序列化时，该属性在 JSON 数据中对应的字段名为 "code"。
     * 当该属性为空字符串或仅包含空白字符时，会触发验证错误，提示 "code 不能为空"。
     */
    @NotBlank(message = "code 不能为空")
    @JsonProperty("code")
    private String code;
}