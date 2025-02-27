package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.dto.AreaCodeDto;
import org.software.code.service.UserService;
import org.software.code.vo.AreaCodeVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 区域码控制器类，负责处理与区域码相关的 HTTP 请求。
 * 该类提供了获取单个区域码信息、根据特定条件获取区域码信息以及获取区域码列表的接口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/user")
public class AreaCodeController {

  /**
   * 注入 UserService 实例，用于调用业务逻辑方法。
   */
  @Resource
  private UserService userService;

  /**
   * 根据 ID 获取区域码信息。
   * 该方法处理 GET 请求，请求路径为 /user/area_code，需要传入一个非空的 ID 参数。
   *
   * @param id 区域码的唯一标识符，通过请求参数传递，不能为 null。
   * @return 返回一个统一的结果对象，包含根据 ID 获取到的区域码信息。
   */
  @GetMapping("/area_code")
  public Result<?> getAreaCodeByID(@RequestParam(name = "id") @NotNull(message = "id不能为空") Long id) {
    // 调用 UserService 的 getAreaCode 方法，根据 ID 获取区域码信息
    AreaCodeVo areaCodeVo = userService.getAreaCode(id);
    // 返回包含区域码信息的成功结果
    return Result.success(areaCodeVo);
  }

  /**
   * 根据请求体中的数据获取区域码信息。
   * 该方法处理 GET 请求，请求路径为 /user/area_code/id，需要传入一个有效的 AreaCodeDto 对象。
   *
   * @param dto 包含获取区域码所需条件的 DTO 对象，通过请求体传递，需要进行数据验证。
   * @return 返回一个统一的结果对象，包含根据 DTO 条件获取到的区域码信息。
   */
  @PostMapping("/area_code/id")
  public Result<?> getAreaCodeID(@Valid @RequestBody AreaCodeDto dto) {
    // 调用 UserService 的 getAreaCode 方法，根据 DTO 对象获取区域码信息
    AreaCodeVo areaCodeVo = userService.getAreaCode(dto);
    // 返回包含区域码信息的成功结果
    return Result.success(areaCodeVo);
  }

  /**
   * 根据请求体中的数据获取区域码列表信息。
   * 该方法处理 GET 请求，请求路径为 /user/area_code/id/list，需要传入一个有效的 AreaCodeDto 对象。
   *
   * @param dto 包含获取区域码列表所需条件的 DTO 对象，通过请求体传递，需要进行数据验证。
   * @return 返回一个统一的结果对象，包含根据 DTO 条件获取到的区域码列表信息。
   */
  @PostMapping("/area_code/id/list")
  public Result<?> getAreaCodeIDList(@Valid @RequestBody AreaCodeDto dto) {
    // 调用 UserService 的 getAreaCodeList 方法，根据 DTO 对象获取区域码列表信息
    List<AreaCodeVo> list = userService.getAreaCodeList(dto);
    // 返回包含区域码列表信息的成功结果
    return Result.success(list);
  }
}