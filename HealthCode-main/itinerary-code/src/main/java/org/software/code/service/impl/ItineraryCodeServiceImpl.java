package org.software.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.software.code.client.UserClient;
import org.software.code.common.result.Result;
import org.software.code.entity.ItineraryCode;
import org.software.code.mapper.ItineraryCodeMapper;
import org.software.code.service.ItineraryCodeService;
import org.software.code.vo.GetItineraryVo;
import org.software.code.vo.PlaceStarVo;
import org.software.code.vo.PlaceStatusVo;
import org.software.code.vo.UserInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 行程码服务接口的实现类，负责具体的业务逻辑处理。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Service
public class ItineraryCodeServiceImpl implements ItineraryCodeService {
    // 注入用户客户端，用于调用用户服务接口
    @Resource
    UserClient userClient;
    // 注入行程码数据访问层接口
    @Resource
    ItineraryCodeMapper itineraryCodeMapper;

    /**
     * 获取指定用户的行程码列表信息。
     *
     * @param uid 用户ID
     * @return 包含地点状态信息的列表
     */
    @Override
    public List<PlaceStatusVo> getItineraryCodeList(long uid) {
        // 根据用户ID从数据库查询行程码记录
        List<ItineraryCode> itineraryCodeList = itineraryCodeMapper.selectList(new LambdaQueryWrapper<ItineraryCode>().
                eq(ItineraryCode::getUid, uid));
        // 将行程码记录转换为地点状态信息列表
        return itineraryCodeList.stream()
                .map(itineraryCode -> {
                    PlaceStatusVo placeStatusVo = new PlaceStatusVo();
                    // 随机选择一个城市作为地点
                    List<String> stringList = Arrays.asList("北京市", "上海市", "广州市", "深圳市", "杭州市");
                    int randomIndex = ThreadLocalRandom.current().nextInt(stringList.size());
                    String place = stringList.get(randomIndex);
                    // 随机生成地点状态
                    boolean status = ThreadLocalRandom.current().nextBoolean();
                    placeStatusVo.setPlace(place);
                    placeStatusVo.setStatus(status);
                    return placeStatusVo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 清理15天前的行程码记录。
     */
    @Override
    public void cleanItinerary() {
        // 获取当前日期
        Date currentDate = new Date();
        // 创建 Calendar 实例并设置为当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // 减去15天
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        // 获取15天前的日期
        Date dateBefore15Days = calendar.getTime();
        // 从数据库中删除15天前的行程码记录
        itineraryCodeMapper.delete(new LambdaQueryWrapper<ItineraryCode>().
                le(ItineraryCode::getTime, dateBefore15Days));
    }

    /**
     * 获取指定用户的行程信息。
     *
     * @param uid 用户ID
     * @return 包含行程信息的视图对象
     */
    @Override
    public GetItineraryVo getItinerary(long uid) {
        // 根据用户ID从数据库查询行程码记录
        List<ItineraryCode> itineraryCodeList = itineraryCodeMapper.selectList(new LambdaQueryWrapper<ItineraryCode>().
                eq(ItineraryCode::getUid, uid));
        // 调用用户服务接口获取用户信息
        Result<?> result = userClient.getUserByUID(uid);
        ObjectMapper objectMapper = new ObjectMapper();
        // 将用户信息转换为视图对象
        UserInfoVo userInfoDto = objectMapper.convertValue(result.getData(), UserInfoVo.class);
        // 将行程码记录转换为地点星级信息列表
        List<PlaceStarVo> places = itineraryCodeList.stream()
                .map(itineraryCode -> {
                    PlaceStarVo placeStarVo = new PlaceStarVo();
                    // 随机选择一个城市作为地点
                    List<String> stringList = Arrays.asList("北京市", "上海市", "广州市", "深圳市", "杭州市");
                    int randomIndex = ThreadLocalRandom.current().nextInt(stringList.size());
                    String place = stringList.get(randomIndex);
                    // 随机生成地点星级状态
                    boolean status = ThreadLocalRandom.current().nextBoolean();
                    placeStarVo.setPlace(place);
                    placeStarVo.setStar(status);
                    return placeStarVo;
                })
                .collect(Collectors.toList());
        GetItineraryVo getItineraryVo = new GetItineraryVo();
        getItineraryVo.setPlaces(places);
        getItineraryVo.setCreated_at(new Date());
        getItineraryVo.setIdentityCard(userInfoDto.getIdentityCard());
        return getItineraryVo;
    }
}