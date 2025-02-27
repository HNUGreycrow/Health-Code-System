package org.software.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.VaccinationSiteDto;
import org.software.code.entity.VaccinationSite;
import org.software.code.vo.VaccinationSiteVo;

import java.util.List;

/**
 * 接种点服务接口，继承自MyBatis-Plus的IService接口，用于处理接种点相关业务逻辑。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface VaccinationSiteService extends IService<VaccinationSite>{

  /**
   * 添加接种点信息
   * @param vaccinationSiteDto 接种点数据传输对象，包含接种点的相关信息
   */
  void addVaccinationSiteDto(VaccinationSiteDto vaccinationSiteDto);

  /**
   * 根据接种点ID删除接种点信息
   * @param id 接种点的ID
   */
  void deleteVaccinationSiteById(Long id);

  /**
   * 更新接种点信息
   * @param vaccinationSiteDto 接种点数据传输对象，包含需要更新的接种点相关信息
   */
  void updateVaccinationSite(VaccinationSiteDto vaccinationSiteDto);

  /**
   * 查询所有接种点信息
   * @return 接种点实体对象列表
   */
  List<VaccinationSiteVo> getVaccinationSites();

  List<VaccinationSiteVo> getVaccinationSiteByArea(AreaCodeDto areaCodeDto);
}