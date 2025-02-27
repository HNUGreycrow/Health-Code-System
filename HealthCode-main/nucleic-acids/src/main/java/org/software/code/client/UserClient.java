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
 * 这是一个使用 Spring Cloud OpenFeign 实现的 Feign 客户端接口，
 * 用于与名为 "user" 的服务进行远程调用，实现与用户服务的交互。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@FeignClient(name = "user")
public interface UserClient {

    /**
     * 调用用户服务的 /user/getUserByUID 接口，根据用户的唯一标识（UID）获取用户信息。
     *
     * @param uid 用户的唯一标识，作为请求参数传递给用户服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用用户服务获取的用户相关信息。
     */
    @GetMapping("/user/getUserByUID")
    Result<?> getUserByUID(@RequestParam(name = "uid") Long uid);

    /**
     * 调用用户服务的 /user/getUserByID 接口，根据用户的身份证号码获取用户信息。
     *
     * @param identity_card 用户的身份证号码，作为请求参数传递给用户服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用用户服务获取的用户相关信息。
     */
    @GetMapping("/user/getUserByID")
    Result<?> getUserByID(@RequestParam(name = "identity_card") String identity_card);

    /**
     * 调用用户服务的 /user/area_code 接口，根据传入的 ID 获取对应的地区码信息。
     *
     * @param id 用户相关的 ID，该参数不能为 null，作为请求参数传递给用户服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用用户服务获取的地区码相关信息。
     */
    @GetMapping("/user/area_code")
    Result<?> getAreaCodeByID(@RequestParam(name = "id") @NotNull(message = "id不能为空") Long id);

    /**
     * 调用用户服务的 /user/area_code/id 接口，根据传入的 AreaCodeDto 对象获取地区码信息。
     * 传入的 AreaCodeDto 对象需要通过验证。
     *
     * @param dto 包含地区码相关信息的对象，作为请求体传递给用户服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用用户服务获取的地区码相关信息。
     */
    @PostMapping("/user/area_code/id")
    Result<?> getAreaCodeID(@Valid @RequestBody AreaCodeDto dto);

    /**
     * 调用用户服务的 /user/area_code/id/list 接口，根据传入的 AreaCodeDto 对象获取地区码列表信息。
     * 传入的 AreaCodeDto 对象需要通过验证。
     *
     * @param dto 包含地区码相关信息的对象，作为请求体传递给用户服务。
     * @return 返回一个封装了结果信息的 Result 对象，其中包含调用用户服务获取的地区码列表相关信息。
     */
    @PostMapping("/user/area_code/id/list")
    Result<?> getAreaCodeIDList(@Valid @RequestBody AreaCodeDto dto);
}