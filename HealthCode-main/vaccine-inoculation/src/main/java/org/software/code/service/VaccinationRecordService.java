package org.software.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.software.code.dto.VaccinationRecordDto;
import org.software.code.entity.VaccinationRecord;
import org.software.code.vo.VaccinationRecordVo;

import java.util.List;

/**
 * 接种记录服务接口，继承自MyBatis-Plus的IService接口，用于处理接种记录相关业务逻辑。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface VaccinationRecordService extends IService<VaccinationRecord> {

  /**
   * 获取所有接种记录
   * @return 接种记录实体对象列表
   */
  List<VaccinationRecordVo> getVaccinationRecords();

  /**
   * 为指定用户添加接种记录
   * @param uid 用户ID
   * @param vaccinationRecordDto 接种记录数据传输对象，包含接种记录的相关信息
   */
  void addVaccinationRecordDto(Long uid, VaccinationRecordDto vaccinationRecordDto);

  /**
   * 根据身份证号获取接种记录
   * @param idCard 身份证号
   * @return 接种记录视图对象列表
   */
  List<VaccinationRecordVo> getVaccinationRecordsById(String idCard);
}