package org.software.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.software.code.client.UserClient;
import org.software.code.common.result.Result;
import org.software.code.dto.AddNucleicAcidTestingInstitutionsDto;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.UpdateNucleicAcidTestingInstitutionsDto;
import org.software.code.entity.NucleicAcidTestingInstitutions;
import org.software.code.mapper.NucleicAcidTestingInstitutionsMapper;
import org.software.code.service.NucleicAcidTestingInstitutionsService;
import org.software.code.vo.AreaCodeVo;
import org.software.code.vo.NucleicAcidTestingInstitutionsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 核酸检测机构 Service 实现类
 * 该类继承自 MyBatis-Plus 的 ServiceImpl 类，提供了对核酸检测机构实体的基本增删改查操作的具体实现。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class NucleicAcidTestingInstitutionsServiceImpl extends ServiceImpl<NucleicAcidTestingInstitutionsMapper, NucleicAcidTestingInstitutions> implements NucleicAcidTestingInstitutionsService {

    @Resource
    UserClient userClient;

    @Resource
    NucleicAcidTestingInstitutionsMapper institutionsMapper;

    /**
     * 添加核酸检测机构信息
     *
     * @param addNucleicAcidTestingInstitutionsDto 要添加的核酸检测机构实体对象
     */
    @Override
    public void addNucleicAcidTestingInstitutions(AddNucleicAcidTestingInstitutionsDto addNucleicAcidTestingInstitutionsDto) {
        // 调用 processNucleicAcidTestingInstitutions 方法进行添加操作
        processNucleicAcidTestingInstitutions(addNucleicAcidTestingInstitutionsDto, false);
    }

    /**
     * 根据 ID 删除核酸检测机构信息
     *
     * @param id 要删除的核酸检测机构的 ID
     */
    @Override
    public void deleteNucleicAcidTestingInstitutionsById(Long id) {
        // 调用 MyBatis-Plus 提供的 removeById 方法根据 ID 从数据库中删除核酸检测机构信息
        this.removeById(id);
    }

    /**
     * 更新核酸检测机构信息
     *
     * @param updateNucleicAcidTestingInstitutionsDto 要更新的核酸检测机构实体对象，该对象的 ID 用于定位要更新的记录
     */
    @Override
    public void updateNucleicAcidTestingInstitutions(UpdateNucleicAcidTestingInstitutionsDto updateNucleicAcidTestingInstitutionsDto) {
        // 调用 processNucleicAcidTestingInstitutions 方法进行更新操作
        processNucleicAcidTestingInstitutions(updateNucleicAcidTestingInstitutionsDto, true);
    }

    /**
     * 根据 ID 获取核酸检测机构信息
     *
     * @param id 要获取的核酸检测机构的 ID
     * @return 对应的核酸检测机构实体对象，如果未找到则返回 null
     */
    @Override
    public NucleicAcidTestingInstitutionsVo getNucleicAcidTestingInstitutionsById(Long id) {
        NucleicAcidTestingInstitutions nucleicAcidTestingInstitutions = this.getById(id);
        return convertToVo(nucleicAcidTestingInstitutions);
    }

    /**
     * 获取所有核酸检测机构信息
     *
     * @return 包含所有核酸检测机构信息的列表，如果没有记录则返回空列表
     */
    @Override
    public List<NucleicAcidTestingInstitutionsVo> getAllNucleicAcidTestingInstitutions() {
        List<NucleicAcidTestingInstitutions> records = this.list();
        return records.stream()
                .map(this::convertToVo)
                .collect(Collectors.toList());
    }

    /**
     * 根据区域代码 DTO 获取核酸检测机构列表
     * @param areaCodeDto 区域代码 DTO
     * @return 核酸检测机构 VO 列表
     */
    @Override
    public List<NucleicAcidTestingInstitutionsVo> getInstitutionsByArea(AreaCodeDto areaCodeDto) {
        // 调用 userClient 的 getAreaCodeID 方法，根据 areaCodeDto 获取地区代码 ID 的结果
        Result<?> result = userClient.getAreaCodeIDList(areaCodeDto);
        // 这里建议添加对 result 的判空和结果状态码的检查，确保数据正常获取
        if (result == null || result.getData() == null) {
            return new ArrayList<>();
        }
        List<AreaCodeVo> areaCodeVoList = getAreaCodeVoList(result);
        List<Long> ids = areaCodeVoList.stream().map(AreaCodeVo::getId).collect(Collectors.toList());

        // 如果 ids 列表为空，直接返回空列表
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        // 创建 LambdaQueryWrapper 对象，用于构建查询条件
        LambdaQueryWrapper<NucleicAcidTestingInstitutions> queryWrapper = Wrappers.lambdaQuery();

        // 使用 eq 方法指定查询条件，根据传入的区域代码 DTO 进行查询
        // 假设 NucleicAcidTestingInstitutions 类中有对应的 community、street 和 district 属性
        queryWrapper.in(NucleicAcidTestingInstitutions::getAreaId, ids);

        // 执行查询，获取核酸检测机构实体列表
        List<NucleicAcidTestingInstitutions> records = institutionsMapper.selectList(queryWrapper);

        // 使用 Stream API 将实体对象转换为 VO 对象并收集到列表中
        return records.stream()
                .map(this::convertToVo)
                .collect(Collectors.toList());
    }

    /**
     * 处理核酸检测机构信息的方法
     * @param dto 包含核酸检测机构信息的数据传输对象，可以是添加或更新的 DTO
     * @param isUpdate 标志位，用于判断是更新操作还是添加操作，true 表示更新，false 表示添加
     */
    private void processNucleicAcidTestingInstitutions(Object dto, boolean isUpdate) {
        AreaCodeDto areaCodeDto = new AreaCodeDto();
        BeanUtil.copyProperties(dto, areaCodeDto);
        Result<?> result = userClient.getAreaCodeID(areaCodeDto);
        AreaCodeVo areaCodeVo = getAreaCodeVo(result);

        NucleicAcidTestingInstitutions nucleicAcidTestingInstitutions = new NucleicAcidTestingInstitutions();
        BeanUtil.copyProperties(dto, nucleicAcidTestingInstitutions);
        nucleicAcidTestingInstitutions.setAreaId(areaCodeVo.getId());

        if (isUpdate) {
            // 调用 MyBatis-Plus 提供的 updateById 方法根据 ID 更新核酸检测机构信息
            this.updateById(nucleicAcidTestingInstitutions);
        } else {
            // 调用 MyBatis-Plus 提供的 save 方法将核酸检测机构信息保存到数据库
            this.save(nucleicAcidTestingInstitutions);
        }
    }

    /**
     * 将 NucleicAcidTestingInstitutions 转换为 NucleicAcidTestingInstitutionsVo
     *
     * @param nucleicAcidTestingInstitutions 核酸检测机构实体对象
     * @return 对应的 NucleicAcidTestingInstitutionsVo 对象
     */
    private NucleicAcidTestingInstitutionsVo convertToVo(NucleicAcidTestingInstitutions nucleicAcidTestingInstitutions) {
        if (nucleicAcidTestingInstitutions == null) {
            return null;
        }
        // 调用 userClient 的 getAreaCodeID 方法，根据 areaCodeDto 获取地区代码 ID 的结果
        Result<?> result = userClient.getAreaCodeByID(nucleicAcidTestingInstitutions.getAreaId());
        AreaCodeVo areaCodeVo = getAreaCodeVo(result);

        NucleicAcidTestingInstitutionsVo nucleicAcidTestingInstitutionsVo = new NucleicAcidTestingInstitutionsVo();
        BeanUtil.copyProperties(nucleicAcidTestingInstitutions, nucleicAcidTestingInstitutionsVo);
        nucleicAcidTestingInstitutionsVo.setCommunity(areaCodeVo.getCommunity());
        nucleicAcidTestingInstitutionsVo.setDistrict(areaCodeVo.getDistrict());
        nucleicAcidTestingInstitutionsVo.setStreet(areaCodeVo.getStreet());

        return nucleicAcidTestingInstitutionsVo;
    }

    /**
     * 从 Result 对象中获取 AreaCodeVo 列表
     * @param result Result 对象
     * @return AreaCodeVo 列表
     */
    private List<AreaCodeVo> getAreaCodeVoList(Result<?> result) {
        if (result == null || result.getData() == null) {
            return Collections.emptyList();
        }

        // 直接将 result.getData() 强制转换为 List<AreaCodeVo>
        if (result.getData() instanceof List<?>) {
            List<?> rawList = (List<?>) result.getData();
            return rawList.stream().map(vo -> {
                ObjectMapper objectMapper = new ObjectMapper();
                  return objectMapper.convertValue(vo, AreaCodeVo.class);
                })
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 从 Result 对象中获取 AreaCodeVo
     * @param result Result 对象
     * @return AreaCodeVo 对象
     */
    private AreaCodeVo getAreaCodeVo(Result<?> result) {
        ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.convertValue(result.getData(), AreaCodeVo.class);
    }
}