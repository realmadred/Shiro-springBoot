package com.example.demo.config.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.hashCode());
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        rcm.setDefaultExpiration(15 * 60);//秒
        return rcm;
    }

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
//        RedisSerializer<Object> kryoRedisSerializer = new KryoRedisSerializer<>();
//        redisTemplate.setKeySerializer(kryoRedisSerializer);
//        redisTemplate.setValueSerializer(kryoRedisSerializer);
//        redisTemplate.setHashKeySerializer(kryoRedisSerializer);
//        redisTemplate.setHashValueSerializer(kryoRedisSerializer);
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        return redisTemplate;
    }

}