package org.software.code.controller;

import org.software.code.common.result.Result;
import org.software.code.dto.AddNucleicAcidTestingInstitutionsDto;
import org.software.code.dto.AreaCodeDto;
import org.software.code.dto.UpdateNucleicAcidTestingInstitutionsDto;
import org.software.code.service.NucleicAcidTestingInstitutionsService;
import org.software.code.vo.NucleicAcidTestingInstitutionsVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 核酸检测机构 Controller 类，用于处理与核酸检测机构相关的 HTTP 请求。
 * 提供了对核酸检测机构信息的增删改查等操作的接口。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@RestController
@RequestMapping("/nucleic-acids")
public class NucleicAcidTestingInstitutionsController {
    @Resource
    private NucleicAcidTestingInstitutionsService institutionsService;

    /**
     * 新增核酸检测机构信息。
     * 接收一个核酸检测机构实体作为请求体，调用服务层的新增方法添加机构信息。
     *
     * @param dto 核酸检测机构实体，包含要新增的机构信息。
     * @return 若新增成功，返回成功的结果信息；否则返回相应的错误信息。
     */
    @PostMapping("/testing-institutions")
    public Result<?> addNucleicAcidTestingInstitutions(@RequestBody AddNucleicAcidTestingInstitutionsDto dto) {
        institutionsService.addNucleicAcidTestingInstitutions(dto);
        return Result.success();
    }

    /**
     * 根据 ID 删除核酸检测机构信息。
     * 从请求路径中获取核酸检测机构的唯一标识 ID，调用服务层的删除方法删除对应机构信息。
     *
     * @param id 核酸检测机构的唯一标识，用于指定要删除的机构。
     * @return 若删除成功，返回成功的结果信息；否则返回相应的错误信息。
     */
    @DeleteMapping("/testing-institutions/{id}")
    public Result<?> deleteNucleicAcidTestingInstitutionsById(@PathVariable Long id) {
        institutionsService.deleteNucleicAcidTestingInstitutionsById(id);
        return Result.success();
    }

    /**
     * 修改核酸检测机构信息。
     * 接收一个核酸检测机构实体作为请求体，调用服务层的修改方法更新机构信息。
     *
     * @param updateNucleicAcidTestingInstitutionsDto 核酸检测机构实体，包含要修改的机构信息。
     * @return 若修改成功，返回成功的结果信息；否则返回相应的错误信息。
     */
    @PutMapping("/testing-institutions")
    public Result<?> updateNucleicAcidTestingInstitutions(@RequestBody UpdateNucleicAcidTestingInstitutionsDto updateNucleicAcidTestingInstitutionsDto) {
        institutionsService.updateNucleicAcidTestingInstitutions(updateNucleicAcidTestingInstitutionsDto);
        return Result.success();
    }

    /**
     * 根据 ID 查询核酸检测机构信息。
     * 从请求路径中获取核酸检测机构的唯一标识 ID，调用服务层的查询方法获取对应机构信息。
     *
     * @param id 核酸检测机构的唯一标识，用于指定要查询的机构。
     * @return 若查询到对应机构信息，返回包含该机构实体的成功结果信息；否则返回相应的错误信息。
     */
    @GetMapping("/testing-institutions/{id}")
    public Result<?>  getNucleicAcidTestingInstitutionsById(@PathVariable Long id) {
        NucleicAcidTestingInstitutionsVo record = institutionsService.getNucleicAcidTestingInstitutionsById(id);
        return Result.success(record);
    }

    /**
     * 查询所有核酸检测机构信息。
     * 调用服务层的查询方法获取所有核酸检测机构的信息列表。
     *
     * @return 若查询成功，返回包含核酸检测机构列表的成功结果信息；否则返回相应的错误信息。
     */
    @GetMapping("/testing-institutions")
    public Result<?>  getAllNucleicAcidTestingInstitutions() {
        List<NucleicAcidTestingInstitutionsVo> list = institutionsService.getAllNucleicAcidTestingInstitutions();
        return Result.success(list);
    }

    @PostMapping("/testing-institutionsByArea")
    public Result<?>  getAllNucleicAcidTestingInstitutionsByArea(@RequestBody AreaCodeDto areaCodeDto) {
        List<NucleicAcidTestingInstitutionsVo> list = institutionsService.getInstitutionsByArea(areaCodeDto);
        return Result.success(list);
    }
}