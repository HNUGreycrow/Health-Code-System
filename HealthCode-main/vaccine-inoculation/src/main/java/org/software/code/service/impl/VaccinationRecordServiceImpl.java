package org.software.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Random;
import org.software.code.client.UserClient;
import org.software.code.common.result.Result;
import org.software.code.dto.VaccinationRecordDto;
import org.software.code.entity.VaccinationRecord;
import org.software.code.mapper.VaccinationRecordMapper;
import org.software.code.service.VaccinationRecordService;
import org.software.code.vo.UserInfoVo;
import org.software.code.vo.VaccinationRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
// 疫苗接种记录服务实现类，继承自 MyBatis-Plus 的 ServiceImpl 类
public class VaccinationRecordServiceImpl extends ServiceImpl<VaccinationRecordMapper, VaccinationRecord> implements
    VaccinationRecordService {

  @Resource
  UserClient userClient;

  /**
   * 获取所有疫苗接种记录
   * @return 疫苗接种记录列表
   */
  @Override
  public List<VaccinationRecordVo> getVaccinationRecords() {
    // 调用 MyBatis-Plus 提供的 list 方法获取所有疫苗接种记录
    List<VaccinationRecord> vaccinationRecords = this.list();
    return vaccinationRecords.stream()
        .map(vaccinationRecord -> {
          // 创建一个 VaccinationRecordVo 对象
          VaccinationRecordVo vo = new VaccinationRecordVo();
          // 使用 BeanUtils 工具类将 vaccinationRecord 中的属性复制到 vo 中
          BeanUtils.copyProperties(vaccinationRecord, vo);
          return vo;
        })
        .collect(Collectors.toList());
  }

  /**
   * 添加疫苗接种记录
   * @param uid 用户 ID
   * @param vaccinationRecordDto 疫苗接种记录数据传输对象
   */
  @Override
  public void addVaccinationRecordDto(Long uid, VaccinationRecordDto vaccinationRecordDto) {
    // 创建一个 VaccinationRecord 对象
    VaccinationRecord vaccinationRecord = new VaccinationRecord();
    // 设置用户 ID
    vaccinationRecord.setUid(uid);
    // 使用 BeanUtil 工具类将 vaccinationRecordDto 中的属性复制到 vaccinationRecord 中
    BeanUtil.copyProperties(vaccinationRecordDto, vaccinationRecord);
    //随机设置疫苗种类
    List<String> types = new ArrayList<>();
    types.add("北京科兴");
    types.add("国药中生");
    types.add("康希诺");
    int random_index = new Random().nextInt(3);
    vaccinationRecord.setVaccineType(types.get(random_index));
    // 调用 MyBatis-Plus 提供的 save 方法将疫苗接种记录保存到数据库
    this.save(vaccinationRecord);
  }

  /**
   * 根据身份证号获取疫苗接种记录
   * @param idCard 身份证号
   * @return 疫苗接种记录 VO 列表
   */
  @Override
  public List<VaccinationRecordVo> getVaccinationRecordsById(String idCard) {
    // 调用 userClient 的 getUserByID 方法根据身份证号获取用户信息
    Result<?> result = userClient.getUserByID(idCard);
    // 创建一个 ObjectMapper 对象，用于 JSON 数据的转换
    ObjectMapper objectMapper = new ObjectMapper();
    // 将结果中的数据转换为 UserInfoVo 对象
    UserInfoVo userInfoVo = objectMapper.convertValue(result.getData(), UserInfoVo.class);
    // 获取用户 ID
    Long uid = userInfoVo.getUid();
    // 使用 LambdaQueryWrapper 构建查询条件，根据用户 ID 查询疫苗接种记录
    List<VaccinationRecord> vaccinationRecords = this.list(new LambdaQueryWrapper<VaccinationRecord>().
        eq(VaccinationRecord::getUid, uid));
    // 将接种记录列表转换为 VaccinationRecordVo 列表

    return vaccinationRecords.stream()
        .map(vaccinationRecord -> {
          // 创建一个 VaccinationRecordVo 对象
          VaccinationRecordVo vo = new VaccinationRecordVo();
          // 使用 BeanUtils 工具类将 vaccinationRecord 中的属性复制到 vo 中
          BeanUtils.copyProperties(vaccinationRecord, vo);
          return vo;
        })
        .collect(Collectors.toList());
  }
}