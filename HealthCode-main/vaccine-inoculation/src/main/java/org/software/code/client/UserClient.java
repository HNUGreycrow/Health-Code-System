package org.software.code.client;

import org.software.code.common.result.Result;
import org.software.code.dto.AreaCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 这是一个使用 Spring Cloud OpenFeign 实现的 Feign 客户端接口。
 * 该接口用于与名为 "user" 的服务进行远程调用，通过 Feign 可以方便地发起 HTTP 请求。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "user")
public interface UserClient {

  /**
   * 调用名为 "user" 的服务的接口 /user/getUserByUID，根据用户的 UID 获取用户信息
   *
   * @param uid 用户的唯一标识符（UID）
   * @return 返回一个封装了结果信息的 Result 对象，其中包含调用远程服务获取的用户信息
   */
  @GetMapping("/user/getUserByUID")
  Result<?> getUserByUID(@RequestParam(name = "uid") Long uid);

  /**
   * 调用名为 "user" 的服务的接口 /user/getUserById，根据用户的身份证号码获取用户信息
   *
   * @param identity_card 用户的身份证号码
   * @return 返回一个封装了结果信息的 Result 对象，其中包含调用远程服务获取的用户信息
   */
  @GetMapping("/user/getUserByID")
  Result<?> getUserByID(@RequestParam(name = "identity_card") String identity_card);

  /**
   * 根据 ID 获取区域代码信息
   *
   * @param id 用于查询区域代码的 ID，使用 @NotNull 注解确保该参数不能为空
   * @return 返回一个封装了结果信息的 Result 对象，其中包含调用远程服务获取的区域代码信息
   */
  @GetMapping("/user/area_code")
  Result<?> getAreaCodeByID(@RequestParam(name = "id") @NotNull(message = "id不能为空") Long id);

  /**
   * 根据 AreaCodeDto 对象中的信息获取区域代码的 ID
   *
   * @param dto 包含查询区域代码 ID 所需信息的 DTO 对象，使用 @Valid 注解对该对象进行数据验证
   * @return 返回一个封装了结果信息的 Result 对象，其中包含调用远程服务获取的区域代码信息
   */
  @PostMapping("/user/area_code/id")
  Result<?> getAreaCodeID(@Valid @RequestBody AreaCodeDto dto);
}
