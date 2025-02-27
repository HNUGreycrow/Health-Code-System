package org.software.code.service;

import org.software.code.dto.AddPlaceInputDto;
import org.software.code.dto.CreatePlaceCodeRequestDto;
import org.software.code.vo.GetPlaceVo;
import org.software.code.vo.PlaceCodeInfoVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * PlaceCodeService 是一个服务接口，定义了场所码相关业务逻辑的操作方法。
 * 这些方法涵盖了场所的添加、查询、场所码状态变更、扫描场所码等功能，
 * 由具体的服务实现类来实现这些方法，完成相应的业务处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public interface PlaceCodeService {
    /**
     * 添加一个新的场所。
     *
     * @param placeDto 包含场所信息的输入数据传输对象。
     * @return 新添加场所的唯一标识。
     */
    Long addPlace(AddPlaceInputDto placeDto);

    /**
     * 获取所有场所的信息。
     *
     * @return 包含所有场所信息的列表，每个场所信息封装在 GetPlaceVo 对象中。
     */
    List<GetPlaceVo> getPlaces();

    /**
     * 根据场所 ID 和时间范围获取相关记录。
     *
     * @param pid 场所的唯一标识。
     * @param startTime 记录查询的开始时间。
     * @param endTime 记录查询的结束时间。
     * @return 符合条件的记录 ID 列表。
     */
    List<Long> getRecordByPid(long pid, Date startTime, Date endTime);

    /**
     * 更改场所码的状态。
     *
     * @param pid 场所的唯一标识。
     * @param status 要设置的场所码状态。
     */
    void oppositePlaceCode(long pid, boolean status);

    /**
     * 处理扫描场所码的操作。
     *
     * @param uid 扫描场所码的用户的唯一标识。
     * @param pid 被扫描场所的唯一标识。
     */
    void scanPlaceCode(long uid, long pid);

    /**
     * 根据用户列表和时间范围获取相关场所的信息。
     *
     * @param uidList 用户唯一标识的列表。
     * @param startTime 查询的开始时间。
     * @param endTime 查询的结束时间。
     * @return 符合条件的场所 ID 列表。
     */
    List<Long> getPlacesByUserList(List<Long> uidList, Date startTime, Date endTime);

    /**
     * 创建一个新的场所码。
     *
     * @param request 包含创建场所码所需信息的请求数据传输对象。
     */
    void createPlaceCode(CreatePlaceCodeRequestDto request);

    /**
     * 获取所有场所码的详细信息。
     *
     * @return 包含所有场所码详细信息的列表，每个场所码信息封装在 PlaceCodeInfoVo 对象中。
     */
    List<PlaceCodeInfoVo> getPlaceInfoList();

    /**
     * 反转指定场所码的状态。
     *
     * @param pid 场所的唯一标识。
     */
    void placeCodeOpposite(Long pid);

    /**
     * 获取所有场所的唯一标识。
     *
     * @return 包含所有场所唯一标识的列表。
     */
    List<Long> getAllPids();
}