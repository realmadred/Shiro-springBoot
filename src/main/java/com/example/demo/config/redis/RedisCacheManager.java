package com.example.demo.config.redis;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @auther Administrator
 * @date 2017/9/19
 * @description 描述
 */
public class RedisCacheManager extends AbstractCacheManager {

    private RedisTemplate<String,Object> template;

    @Override
    protected Cache createCache(final String s) throws CacheException {
        final RedisCache redisCache = new RedisCache();
        redisCache.setTemplate(template);
        redisCache.setName(s);
        return redisCache;
    }

    public RedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(final RedisTemplate<String,Object> template) {
        this.template = template;
    }
}
