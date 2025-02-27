package org.software.code.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * Redis 配置类，用于配置 Redis 相关的组件，开启 Spring 缓存注解支持。
 *
 * @author “101”计划《软件工程》实践教材案例团队
 */
@Configuration
// 启用 Spring 的缓存注解功能，允许在代码中使用 @Cacheable、@CachePut 等注解
@EnableCaching
public class RedisConfig {

    /**
     * 创建一个 StringRedisTemplate 实例，用于操作 Redis 中的字符串类型数据。
     *
     * @param redisConnectionFactory Redis 连接工厂，用于创建 Redis 连接
     * @return StringRedisTemplate 实例
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 使用传入的 Redis 连接工厂创建 StringRedisTemplate 实例
        return new StringRedisTemplate(redisConnectionFactory);
    }

    /**
     * 创建一个 RedisCacheManager 实例，用于管理 Redis 缓存。
     *
     * @param connectionFactory Redis 连接工厂，用于创建 Redis 连接
     * @return RedisCacheManager 实例
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 获取 Redis 缓存的默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
            // 设置缓存值的序列化方式为 JSON 序列化，使用 GenericJackson2JsonRedisSerializer
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 使用 Redis 连接工厂和配置创建 RedisCacheManager 实例
        return RedisCacheManager.builder(connectionFactory)
            // 设置默认的缓存配置
            .cacheDefaults(config)
            .build();
    }
}
