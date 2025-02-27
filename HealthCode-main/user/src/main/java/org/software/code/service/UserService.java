package org.software.code.service;

import org.software.code.dto.*;
import org.software.code.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService 是一个服务接口，定义了与用户相关的各类业务操作方法。
 * 这些方法涵盖了用户信息的获取、登录、创建、修改、状态管理以及区域编码相关操作等功能。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public interface UserService {

    /**
     * 根据用户唯一标识（UID）获取用户信息。
     *
     * @param uid 用户唯一标识
     * @return 用户信息视图对象
     */
    UserInfoVo getUserByUID(long uid);

    /**
     * 根据用户身份证号获取用户信息。
     *
     * @param identity_card 用户身份证号
     * @return 用户信息视图对象
     */
    UserInfoVo getUserByID(String identity_card);

    /**
     * 用户登录方法，使用微信小程序登录返回的 code 进行登录操作。
     *
     * @param code 微信小程序登录返回的 code
     * @return 登录结果，如 token 等
     */
    UserLoginVo userLogin(String code);

    /**
     * 核酸检测用户登录方法，使用核酸检测用户登录信息进行登录操作。
     *
     * @param nucleicAcidsLoginDto 核酸检测用户登录信息数据传输对象
     * @return 登录结果，如 token 等
     */
    String nucleicAcidTestUserLogin(NucleicAcidsLoginDto nucleicAcidsLoginDto);

    /**
     * 获取所有核酸检测用户信息列表。
     *
     * @return 核酸检测用户信息视图对象列表
     */
    List<NucleicAcidTestPersonnelVo> getNucleicAcidTestUser();

    /**
     * 获取所有健康码管理人员信息列表。
     *
     * @return 健康码管理人员信息视图对象列表
     */
    List<HealthCodeManagerVo> getManagerUser();

    /**
     * 创建新的核酸检测用户。
     *
     * @param createNucleicAcidDto 创建核酸检测用户所需的信息数据传输对象
     */
    void newNucleicAcidTestUser(CreateNucleicAcidDto createNucleicAcidDto);

    /**
     * 创建新的健康码管理人员。
     *
     * @param createManageDto 创建健康码管理人员所需的信息数据传输对象
     */
    void newMangerUser(CreateManageDto createManageDto);

    /**
     * 健康码管理人员登录方法，使用管理人员登录信息进行登录操作。
     *
     * @param managerLoginDto 健康码管理人员登录信息数据传输对象
     * @return 登录结果，如 token 等
     */
    String managerLogin(ManagerLoginDto managerLoginDto);

    /**
     * 修改用户信息。
     *
     * @param userInfoDto 用户信息数据传输对象，包含要修改的用户信息
     */
    void modifyUserInfo(UserInfoDto userInfoDto);

    /**
     * 修改核酸检测用户的状态。
     *
     * @param statusNucleicAcidTestUserDto 包含核酸检测用户 ID 和新状态的数据传输对象
     */
    void statusNucleicAcidTestUser(StatusNucleicAcidTestUserDto statusNucleicAcidTestUserDto);

    /**
     * 修改健康码管理人员的状态。
     *
     * @param statusManagerDto 包含健康码管理人员 ID 和新状态的数据传输对象
     */
    void statusManager(StatusManagerDto statusManagerDto);

    /**
     * 用户修改自身信息。
     *
     * @param uid 用户唯一标识
     * @param userModifyDto 用户修改信息数据传输对象，包含要修改的信息
     */
    void userModify(long uid, UserModifyDto userModifyDto);

    /**
     * 反转核酸检测用户的状态。
     *
     * @param tid 核酸检测用户唯一标识
     */
    void nucleicAcidOpposite(long tid);

    /**
     * 反转健康码管理人员的状态。
     *
     * @param mid 健康码管理人员唯一标识
     */
    void manageOpposite(long mid);

    /**
     * 添加用户信息。
     *
     * @param userInfoDto 用户信息数据传输对象，包含要添加的用户信息
     */
    void addUserInfo(UserInfoDto userInfoDto);

    /**
     * 用户测试登录方法，使用微信小程序登录返回的 code 进行登录操作（用于测试环境）。
     *
     * @param code 微信小程序登录返回的 code
     * @return 登录结果，如 token 等
     */
    String userLogin_test(String code);

    /**
     * 根据用户唯一标识删除用户信息。
     *
     * @param uid 用户唯一标识
     */
    void deleteUserInfo(long uid);

    /**
     * 根据区域 ID 获取区域编码信息。
     *
     * @param id 区域 ID
     * @return 区域编码信息视图对象
     */
    AreaCodeVo getAreaCode(long id);

    /**
     * 根据区域编码数据传输对象获取区域编码信息。
     *
     * @param areaCodeDto 区域编码数据传输对象，包含区域相关信息
     * @return 区域编码信息视图对象
     */
    AreaCodeVo getAreaCode(AreaCodeDto areaCodeDto);

    /**
     * 根据区域编码数据传输对象获取区域编码信息列表。
     *
     * @param dto 区域编码数据传输对象，包含区域相关信息
     * @return 区域编码信息视图对象列表
     */
    List<AreaCodeVo> getAreaCodeList(AreaCodeDto dto);
}