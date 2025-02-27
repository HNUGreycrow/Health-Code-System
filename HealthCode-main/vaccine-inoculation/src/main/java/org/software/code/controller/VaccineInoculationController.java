package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.common.util.JWTUtil;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.VaccinationRecordDto;
import org.software.code.dto.VaccinationSiteDto;
import org.software.code.service.VaccinationRecordService;
import org.software.code.service.VaccinationSiteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 疫苗接种相关的控制器类，处理与疫苗接种记录和接种点相关的请求。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Validated
@RestController
@RequestMapping("/vaccine-inoculation")
public class VaccineInoculationController {

  /**
   * 注入疫苗接种点服务类，用于处理接种点相关业务逻辑。
   */
  @Resource
  private VaccinationSiteService vaccinationSiteService;

  /**
   * 注入疫苗接种记录服务类，用于处理接种记录相关业务逻辑。
   */
  @Resource
  private VaccinationRecordService vaccinationRecordService;

  /**
   * 根据身份证号获取疫苗接种记录，如果身份证号为空则获取所有接种记录。
   *
   * @param idCard 身份证号
   * @return 包含疫苗接种记录的结果对象
   */
  @GetMapping("/vaccinationRecord")
  public Result<?> getVaccinationRecordsById(@RequestParam String idCard) {
    // 判断idCard是否为空
    if (idCard == null || idCard.isEmpty()) {
      // 如果为空，调用getVaccinationRecords方法
      return Result.success(vaccinationRecordService.getVaccinationRecords());
    }
    return Result.success(vaccinationRecordService.getVaccinationRecordsById(idCard));
  }

  /**
   * 添加疫苗接种记录。
   *
   * @param token 授权令牌，不能为空
   * @param vaccinationRecordDto 疫苗接种记录的数据传输对象
   * @return 操作结果对象
   */
  @PostMapping("/vaccinationRecord")
  public Result<?> addVaccinationRecord(@RequestHeader("Authorization") @NotNull(message = "token不能为空") String token,
      @Valid @RequestBody VaccinationRecordDto vaccinationRecordDto){
    // 从令牌中提取用户ID
    Long uid = JWTUtil.extractID(token);
    // 调用服务类方法添加接种记录
    vaccinationRecordService.addVaccinationRecordDto(uid,vaccinationRecordDto);
    return Result.success();
  }

  /**
   * 添加疫苗接种点。
   *
   * @param vaccinationSiteDto 疫苗接种点的数据传输对象
   * @return 操作结果对象
   */
  @PostMapping("/vaccinationSite")
  public Result<?> addVaccinationSite(@RequestBody VaccinationSiteDto vaccinationSiteDto){
    // 调用服务类方法添加接种点
    vaccinationSiteService.addVaccinationSiteDto(vaccinationSiteDto);
    return Result.success();
  }

  /**
   * 获取所有疫苗接种点信息。
   *
   * @return 包含疫苗接种点信息的结果对象
   */
  @GetMapping("/vaccinationSite")
  public Result<?> getVaccinationSites(){
    return Result.success(vaccinationSiteService.getVaccinationSites());
  }

  /**
   * 根据地区代码 DTO 获取疫苗接种点信息
   * 该方法接收一个 AreaCodeDto 对象作为请求体，调用 VaccinationSiteService 的相应方法获取疫苗接种点列表
   * 并将结果封装在 Result 对象中返回给客户端
   *
   * @param areaCodeDto 包含地区代码信息的 DTO 对象
   * @return 包含疫苗接种点列表的 Result 对象
   */
  @PostMapping("/getVaccinationSiteByArea")
  public Result<?> getVaccinationSiteByArea(@RequestBody AreaCodeDto areaCodeDto) {
    return Result.success(vaccinationSiteService.getVaccinationSiteByArea(areaCodeDto));
  }


}