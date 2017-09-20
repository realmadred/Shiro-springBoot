package com.example.demo.config.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.Set;

/**
 * @auther lf
 * @date 2017/9/19
 * @description redis缓存
 */
public class RedisCache implements Cache<Object, Object> {

    private String name;

    private RedisTemplate<Object,Object> template;

    private ValueOperations<Object,Object> operations;

    @Override
    public Object get(final Object s) throws CacheException {
        return operations.get(s);
    }

    @Override
    public Object put(final Object s, final Object o) throws CacheException {
        operations.set(s, o);
        return o;
    }

    @Override
    public Object remove(final Object s) throws CacheException {
        template.delete(s);
        return s;
    }

    @Override
    public void clear() throws CacheException {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<Object> keys() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    public RedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(final RedisTemplate<Object,Object> template) {
        this.template = template;
        operations = template.opsForValue();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
