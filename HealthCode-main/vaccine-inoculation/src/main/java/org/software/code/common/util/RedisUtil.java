package org.software.code.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RedisUtil 是一个用于操作 Redis 缓存的工具类，使用 Spring 的 StringRedisTemplate 进行操作。
 * 该类封装了一些常用的 Redis 操作，如设置值、获取值和删除值，方便在项目中使用 Redis 缓存。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Component
public class RedisUtil {

    /**
     * 注入 StringRedisTemplate 实例，用于与 Redis 进行交互。
     * StringRedisTemplate 是 Spring Data Redis 提供的一个模板类，专门用于处理字符串类型的键值对。
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向 Redis 中设置一个键值对。
     * 此方法使用 StringRedisTemplate 的 opsForValue() 方法获取值操作的接口，然后调用 set 方法将键值对存储到 Redis 中。
     *
     * @param key   要设置的键，类型为 String。
     * @param value 要设置的值，类型为 String。
     */
    public void setValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 从 Redis 中获取指定键对应的值。
     * 此方法使用 StringRedisTemplate 的 opsForValue() 方法获取值操作的接口，然后调用 get 方法根据键从 Redis 中获取对应的值。
     *
     * @param key 要获取值的键，类型为 String。
     * @return 如果键存在于 Redis 中，则返回对应的值；如果键不存在，则返回 null。
     */
    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 从 Redis 中删除指定的键及其对应的值。
     * 此方法使用 StringRedisTemplate 的 delete 方法，根据键从 Redis 中删除对应的键值对。
     *
     * @param key 要删除的键，类型为 String。
     */
    public void deleteValue(String key) {
        stringRedisTemplate.delete(key);
    }
}