package org.software.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.software.code.dto.AddNucleicAcidTestingInstitutionsDto;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.UpdateNucleicAcidTestingInstitutionsDto;
import org.software.code.entity.NucleicAcidTestingInstitutions;
import org.software.code.vo.NucleicAcidTestingInstitutionsVo;

import java.util.List;

/**
 * NucleicAcidTestingInstitutionsService 是一个服务接口，继承自 MyBatis-Plus 的 IService 接口，
 * 主要用于定义与核酸检测机构相关的业务逻辑操作方法。这些方法涵盖了核酸检测机构信息的增删改查等功能，
 * 为上层调用者提供了统一的服务调用入口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
public interface NucleicAcidTestingInstitutionsService extends IService<NucleicAcidTestingInstitutions> {
    /**
     * 新增核酸检测机构信息。
     * 该方法接收一个 AddNucleicAcidTestingInstitutionsDto 对象，将其包含的机构信息添加到系统中。
     *
     * @param addNucleicAcidTestingInstitutionsDto 包含新增核酸检测机构信息的数据传输对象
     */
    void addNucleicAcidTestingInstitutions(AddNucleicAcidTestingInstitutionsDto addNucleicAcidTestingInstitutionsDto);

    /**
     * 根据 ID 删除核酸检测机构信息。
     * 该方法接收一个核酸检测机构的唯一标识 ID，将对应 ID 的机构信息从系统中删除。
     *
     * @param id 要删除的核酸检测机构的唯一标识
     */
    void deleteNucleicAcidTestingInstitutionsById(Long id);

    /**
     * 修改核酸检测机构信息。
     * 该方法接收一个 UpdateNucleicAcidTestingInstitutionsDto 对象，根据其中的信息更新系统中对应机构的信息。
     *
     * @param updateNucleicAcidTestingInstitutionsDto 包含要更新的核酸检测机构信息的数据传输对象
     */
    void updateNucleicAcidTestingInstitutions(UpdateNucleicAcidTestingInstitutionsDto updateNucleicAcidTestingInstitutionsDto);

    /**
     * 根据 ID 查询核酸检测机构信息。
     * 该方法接收一个核酸检测机构的唯一标识 ID，返回对应 ID 的机构信息的视图对象。
     *
     * @param id 要查询的核酸检测机构的唯一标识
     * @return 包含查询到的核酸检测机构信息的视图对象
     */
    NucleicAcidTestingInstitutionsVo getNucleicAcidTestingInstitutionsById(Long id);

    /**
     * 查询所有核酸检测机构信息。
     * 该方法返回系统中所有核酸检测机构信息的视图对象列表。
     *
     * @return 包含所有核酸检测机构信息的视图对象列表
     */
    List<NucleicAcidTestingInstitutionsVo> getAllNucleicAcidTestingInstitutions();

    /**
     * 根据区域编码查询核酸检测机构信息。
     * 该方法接收一个 AreaCodeDto 对象，根据其中的区域编码信息查询符合条件的核酸检测机构信息，
     * 并返回这些机构信息的视图对象列表。
     *
     * @param areaCodeDto 包含区域编码信息的数据传输对象
     * @return 包含符合区域条件的核酸检测机构信息的视图对象列表
     */
    List<NucleicAcidTestingInstitutionsVo> getInstitutionsByArea(AreaCodeDto areaCodeDto);
}