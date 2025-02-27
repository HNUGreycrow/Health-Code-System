package org.software.code.service;

import org.software.code.vo.GetItineraryVo;
import org.software.code.vo.PlaceStatusVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行程码服务接口，定义了与行程码业务相关的操作方法。
 * 该接口由 Spring 框架的 @Service 注解标识，用于处理行程码相关的核心业务逻辑。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public interface ItineraryCodeService {

    /**
     * 获取指定用户近 14 天的行程码列表信息。
     * 该方法会根据用户 ID 查询用户 14 天内途径的城市信息，并结合本地风险地区数据，
     * 为每个地点设置相应的状态（有风险或无风险）。
     *
     * @param uid 用户的唯一标识
     * @return 包含地点状态信息的列表
     */
    List<PlaceStatusVo> getItineraryCodeList(long uid);

    /**
     * 清理 15 天以前的用户行程数据。
     * 该方法用于定期清理过期的行程信息，以节省存储空间并保证数据的时效性。
     */
    void cleanItinerary();

    /**
     * 获取指定用户的行程信息。
     * 该方法根据用户 ID 获取用户的行程详情，封装在 GetItineraryVo 对象中返回。
     *
     * @param uid 用户的唯一标识
     * @return 包含用户行程信息的视图对象
     */
    GetItineraryVo getItinerary(long uid);
}