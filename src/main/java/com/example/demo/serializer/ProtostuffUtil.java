package com.example.demo.serializer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * lf
 * 2017-09-20 14:29:16
 * protostuff工具类
 */
public class ProtostuffUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtostuffUtil.class);

    private static final LoadingCache<Class, Schema> SCHEMA_CACHE;

    static {
        SCHEMA_CACHE = CacheBuilder.newBuilder()
                .maximumSize(512)
                .expireAfterWrite(6, TimeUnit.HOURS)
                .softValues()
                .initialCapacity(64)
                .build(new CacheLoader<Class, Schema>() {
                    @Override
                    public Schema load(@Nonnull final Class aClass) throws Exception {
                        return RuntimeSchema.getSchema(aClass);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public static <T> byte[] serializer(T o) {
        Schema<T> schema = SCHEMA_CACHE.getUnchecked(o.getClass());
        return ProtobufIOUtil.toByteArray(o, schema, LinkedBuffer.allocate(256));
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema<T> schema = SCHEMA_CACHE.getUnchecked(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (Exception e) {
            LOGGER.error("deserializer error {}", clazz);
        }
        return obj;
    }
}  