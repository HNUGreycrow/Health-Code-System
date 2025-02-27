package org.software.code.vo;

import lombok.Data;

/**
 * UserLoginVo 是一个视图对象（Value Object），用于封装用户登录后返回给前端的信息。
 * 它主要包含两个属性：用户登录后生成的 JWT Token 和用户的姓名。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Data
public class UserLoginVo {

  /**
   * 用户登录后生成的 JWT（JSON Web Token）。
   * 前端在后续的请求中需要携带此 Token 以进行身份验证和授权。
   */
  private String token;

  /**
   * 用户的姓名，用于在前端界面显示用户的身份信息。
   */
  private String userName;
}