package org.software.code.service;

import org.software.code.common.consts.FSMConst;
import org.software.code.dto.ApplyCodeDto;
import org.software.code.dto.CreateAppealDto;
import org.software.code.dto.ReviewAppealDto;
import org.software.code.dto.UpdateAppealDto;
import org.software.code.vo.AppealLogVo;
import org.software.code.vo.GetCodeVo;
import org.software.code.vo.HealthCodeInfoVo;
import org.software.code.vo.HealthQRCodeVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 健康码服务接口，提供与健康码相关的各种业务操作方法。
 * 该接口定义了一系列用于健康码申请、获取、转码、申诉等操作的方法，
 * 具体的业务逻辑由实现类来完成。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public interface HealthCodeService {

    /**
     * 根据用户唯一标识申请健康码。
     * @param uid 用户唯一标识
     */
    void applyHealthCode(long uid);

    /**
     * 根据用户唯一标识获取健康码二维码信息。
     * @param uid 用户唯一标识
     * @return 健康码二维码信息对象
     */
    HealthQRCodeVo getHealthCode(long uid);

    /**
     * 根据用户唯一标识和健康码事件对健康码进行转码操作。
     * @param uid 用户唯一标识
     * @param event 健康码事件
     */
    void transcodingHealthCodeEvents(long uid, FSMConst.HealthCodeEvent event);

    /**
     * 根据用户唯一标识和申请码数据传输对象进行健康码申请操作。
     * @param uid 用户唯一标识
     * @param applyCodeDto 申请码数据传输对象
     */
    void applyCode(long uid, ApplyCodeDto applyCodeDto);

    /**
     * 根据用户唯一标识获取健康码信息。
     * @param uid 用户唯一标识
     * @return 健康码信息对象
     */
    GetCodeVo getCode(long uid);

    /**
     * 根据身份证号获取健康码详细信息。
     * @param identityCard 身份证号
     * @return 健康码详细信息对象
     */
    HealthCodeInfoVo getHealthCodeInfo(String identityCard);

    /**
     * 用户发起健康码申诉操作。
     * 当用户对自己的健康码状态有异议时，可以使用此方法发起申诉，
     * 系统会根据用户标识和申诉相关信息记录该申诉请求。
     * @param uid 用户唯一标识
     * @param createAppealDto 包含申诉原因、申诉材料等信息的数据传输对象
     */
    void createAppeal(long uid, CreateAppealDto createAppealDto);

    /**
     * 获取所有健康码申诉记录列表。
     * 此方法用于查询系统中所有的健康码申诉记录，并以列表形式返回，
     * 每个申诉记录包含申诉的基本信息和处理状态等。
     * @return 健康码申诉记录列表
     */
    List<AppealLogVo> getAppealList();

    /**
     * 更新健康码申诉记录信息。
     * 此方法用于对已有的健康码申诉记录进行信息更新，
     * 可以修改申诉原因、申诉状态等内容。
     * @param updateAppealDto 包含要更新的申诉信息的数据传输对象
     */
    void updateAppeal(UpdateAppealDto updateAppealDto);

    /**
     * 根据申诉记录的唯一标识删除健康码申诉记录。
     * 此方法用于从系统中删除指定的健康码申诉记录。
     * @param appealId 申诉记录的唯一标识
     */
    void deleteAppealById(Integer appealId);

    /**
     * 审核健康码申诉请求并进行相应处理。
     * 此方法由系统管理员使用，用于审核用户的健康码申诉请求，
     * 根据审核结果对用户的健康码进行转码等操作。
     * @param reviewAppealDto 包含申诉审核结果和转码信息的数据传输对象
     */
    void reviewAppeal(ReviewAppealDto reviewAppealDto);
}