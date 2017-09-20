package com.example.demo.serializer;

import org.apache.commons.lang3.ArrayUtils;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.Arrays;


/**
 * lf
 * 2017-09-19 16:47:17
 * 序列化
 */
public class FstRedisSerializer<T> implements RedisSerializer<T> {

    public static final byte[] EMPTY_BYTES = new byte[0];

    private static final FSTConfiguration CONFIGURATION = FSTConfiguration.createDefaultConfiguration();

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) return EMPTY_BYTES;
        return CONFIGURATION.asByteArray(t);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) return null;
        return (T)CONFIGURATION.asObject(bytes);
    }

}