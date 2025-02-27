package org.software.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.software.code.client.UserClient;
import org.software.code.common.result.Result;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.VaccinationSiteDto;
import org.software.code.entity.VaccinationSite;
import org.software.code.mapper.VaccinationSiteMapper;
import org.software.code.service.VaccinationSiteService;
import org.software.code.vo.AreaCodeVo;
import org.software.code.vo.VaccinationSiteVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
// 疫苗接种站点服务实现类，继承自 MyBatis-Plus 的 ServiceImpl 类
public class VaccinationSiteServiceImpl extends ServiceImpl<VaccinationSiteMapper, VaccinationSite> implements
    VaccinationSiteService {

  @Resource
  UserClient userClient;

  @Resource
  private VaccinationSiteMapper vaccinationSiteMapper;

  /**
   * 处理疫苗接种站点信息的方法
   * @param dto 包含疫苗接种站点的数据传输对象，可以是添加或更新的 DTO
   * @param isUpdate 标志位，用于判断是更新操作还是添加操作，true 表示更新，false 表示添加
   */
  private void processVaccinationSites(Object dto, boolean isUpdate) {
    // 创建一个 AreaCodeDto 对象，用于存储地区代码相关信息
    AreaCodeDto areaCodeDto = new AreaCodeDto();
    // 使用 BeanUtil 工具类将 dto 中的属性复制到 areaCodeDto 中
    BeanUtil.copyProperties(dto, areaCodeDto);
    // 调用 userClient 的 getAreaCodeID 方法，根据 areaCodeDto 获取地区代码 ID 的结果
    Result<?> result = userClient.getAreaCodeID(areaCodeDto);
    // 创建一个 AreaCodeVo 对象，用于存储从结果中提取的地区代码信息
    AreaCodeVo areaCodeVo = new AreaCodeVo();
    // 使用 BeanUtil 工具类将结果中的数据复制到 areaCodeVo 中
    BeanUtil.copyProperties(result.getData(), areaCodeVo);

    // 创建一个 VaccinationSite 对象，用于存储疫苗接种站点信息
    VaccinationSite vaccinationSite = new VaccinationSite();
    // 使用 BeanUtil 工具类将 dto 中的属性复制到 vaccinationSite 中
    BeanUtil.copyProperties(dto, vaccinationSite);
    // 设置疫苗接种站点的地区 ID 为从 areaCodeVo 中获取的 ID
    vaccinationSite.setAreaId(areaCodeVo.getId());

    if (isUpdate) {
      // 调用 MyBatis-Plus 提供的 updateById 方法根据 ID 更新疫苗接种站点信息
      this.updateById(vaccinationSite);
    } else {
      // 调用 MyBatis-Plus 提供的 save 方法将疫苗接种站点信息保存到数据库
      this.save(vaccinationSite);
    }
  }

  /**
   * 添加疫苗接种站点信息
   *
   * @param vaccinationSiteDto 要添加的疫苗接种站点实体对象
   */
  @Override
  public void addVaccinationSiteDto(VaccinationSiteDto vaccinationSiteDto) {
    // 调用 processVaccinationSites 方法进行添加操作
    processVaccinationSites(vaccinationSiteDto, false);
  }

  /**
   * 根据 ID 删除疫苗接种站点信息
   *
   * @param id 要删除的疫苗接种站点的 ID
   */
  @Override
  public void deleteVaccinationSiteById(Long id) {
    // 调用 MyBatis-Plus 提供的 removeById 方法根据 ID 从数据库中删除疫苗接种站点信息
    this.removeById(id);
  }

  /**
   * 更新疫苗接种站点信息
   *
   * @param vaccinationSiteDto 要更新的疫苗接种站点实体对象，该对象的 ID 用于定位要更新的记录
   */
  @Override
  public void updateVaccinationSite(VaccinationSiteDto vaccinationSiteDto) {
    // 调用 processVaccinationSites 方法进行更新操作
    processVaccinationSites(vaccinationSiteDto, true);
  }

  /**
   * 获取所有疫苗接种站点信息
   * @return 疫苗接种站点列表
   */
  @Override
  public List<VaccinationSiteVo> getVaccinationSites(){
    // 调用 MyBatis-Plus 提供的 list 方法获取所有疫苗接种站点信息
    List<VaccinationSite> vaccinationSites = this.list();
    return vaccinationSites.stream().map(vaccinationSite -> {
      VaccinationSiteVo vaccinationSiteVo = new VaccinationSiteVo();
      BeanUtil.copyProperties(vaccinationSite,vaccinationSiteVo);
      Result<?> result = userClient.getAreaCodeByID(vaccinationSite.getAreaId());
      AreaCodeVo areaCodeVo = new AreaCodeVo();
      BeanUtil.copyProperties(result.getData(), areaCodeVo);
      BeanUtil.copyProperties(areaCodeVo, vaccinationSiteVo);

      return vaccinationSiteVo;
    }).collect(Collectors.toList());
  }

  /**
   * 根据地区代码 DTO 获取疫苗接种点视图对象列表
   * 该方法首先调用 UserClient 的 getAreaCodeID 方法获取地区代码的结果
   * 然后将结果中的数据转换为 AreaCodeVo 对象
   * 接着使用 LambdaQueryWrapper 构建查询条件，根据地区 ID 查询疫苗接种点列表
   * 最后将查询到的疫苗接种点实体对象转换为视图对象并返回
   *
   * @param areaCodeDto 包含地区代码信息的 DTO 对象
   * @return 疫苗接种点视图对象列表
   */
  @Override
  public List<VaccinationSiteVo> getVaccinationSiteByArea(AreaCodeDto areaCodeDto) {
    Result<?> result = userClient.getAreaCodeID(areaCodeDto);
    ObjectMapper objectMapper = new ObjectMapper();
    AreaCodeVo areaCodeVo = objectMapper.convertValue(result.getData(), AreaCodeVo.class);
    LambdaQueryWrapper<VaccinationSite> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(VaccinationSite::getAreaId, areaCodeVo.getId());
    List<VaccinationSite> vaccinationSiteList = vaccinationSiteMapper.selectList(lambdaQueryWrapper);

    return vaccinationSiteList.stream().map(vaccinationSite -> {
      VaccinationSiteVo vaccinationSiteVo = new VaccinationSiteVo();
      BeanUtil.copyProperties(areaCodeVo, vaccinationSiteVo);
      BeanUtil.copyProperties(vaccinationSite, vaccinationSiteVo);
      return vaccinationSiteVo;
    }).collect(Collectors.toList());
  }

}