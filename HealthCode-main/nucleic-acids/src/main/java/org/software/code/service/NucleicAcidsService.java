package org.software.code.service;

import org.software.code.dto.AddNucleicAcidTestRecordByIDDto;
import org.software.code.dto.AddNucleicAcidTestRecordDto;
import org.software.code.dto.NucleicAcidTestRecordDto;
import org.software.code.dto.NucleicAcidTestRecordInputDto;
import org.software.code.vo.NucleicAcidTestInfoVo;
import org.software.code.vo.NucleicAcidTestResultVo;
import org.software.code.vo.PositiveInfoVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * NucleicAcidsService 是一个服务接口，用于定义与核酸检测相关的业务逻辑操作。
 * 这些操作涵盖了核酸检测记录的添加、查询，核酸检测信息的统计，阳性信息的获取，
 * 以及通知重新检测和自动修改等功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public interface NucleicAcidsService {

    /**
     * 添加单条核酸检测记录。
     *
     * @param testRecord 包含核酸检测记录信息的数据传输对象
     */
    void addNucleicAcidTestRecord(NucleicAcidTestRecordDto testRecord);

    /**
     * 批量录入核酸检测记录。
     *
     * @param testRecords 包含多条核酸检测记录输入信息的数据传输对象列表
     */
    void enterNucleicAcidTestRecordList(List<NucleicAcidTestRecordInputDto> testRecords);

    /**
     * 根据用户唯一标识获取用户的最后一条核酸检测记录。
     *
     * @param uid 用户的唯一标识
     * @return 包含最后一条核酸检测记录信息的视图对象
     */
    NucleicAcidTestResultVo getLastNucleicAcidTestRecordByUID(long uid);

    /**
     * 根据用户唯一标识获取用户的近14天核酸检测记录。
     *
     * @param uid 用户的唯一标识
     * @return 包含用户所有核酸检测记录信息的视图对象列表
     */
    List<NucleicAcidTestResultVo> getNucleicAcidTestRecordByUID(long uid);

    /**
     * 根据指定的时间范围获取核酸检测信息。
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 包含指定时间范围内核酸检测信息的视图对象
     */
    NucleicAcidTestInfoVo getNucleicAcidTestInfoByTime(Date startTime, Date endTime);

    /**
     * 根据指定的时间范围获取阳性信息。
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 包含指定时间范围内阳性信息的视图对象列表
     */
    List<PositiveInfoVo> getPositiveInfoByTime(Date startTime, Date endTime);

    /**
     * 获取需要通知重新检测的记录。
     * 具体的通知逻辑和记录筛选规则由实现类决定。
     */
    void getNoticeReTestRecords();

    /**
     * 自动修改核酸检测相关信息。
     * 具体的修改逻辑和触发条件由实现类决定。
     */
    void autoModify();

    /**
     * 根据检测人员标识和用户唯一标识添加核酸检测记录。
     *
     * @param tid               检测人员的唯一标识
     * @param uid               用户的唯一标识
     * @param acidTestRecordDto 包含核酸检测记录信息的数据传输对象
     */
    void addNucleicAcidTestRecordByToken(long tid, long uid, AddNucleicAcidTestRecordDto acidTestRecordDto);

    /**
     * 根据检测人员标识和特定方式添加核酸检测记录。
     *
     * @param tid                      检测人员的唯一标识
     * @param acidTestRecordByIDDto 包含核酸检测记录信息的数据传输对象
     */
    void addNucleicAcidTestRecordByID(long tid, AddNucleicAcidTestRecordByIDDto acidTestRecordByIDDto);
}