package com.example.demo.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.Arrays;


/**
 * lf
 * 2017-09-19 16:47:17
 * 序列化
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private static final KryoFactory DEFAULT_FACTORY = KryoFactory.getDefaultFactory();

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) return ArrayUtils.EMPTY_BYTE_ARRAY;
        Output output = new Output(4096);
        Kryo kryo = DEFAULT_FACTORY.getKryo();
        try {
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        } finally {
            output.flush();
            output.close();
            cleanup(kryo);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) return null;
        Input input = new Input(bytes);
        Kryo kryo = DEFAULT_FACTORY.getKryo();
        try {
            @SuppressWarnings("unchecked")
            T t = (T) kryo.readClassAndObject(input);
            return t;
        } finally {
            input.close();
            cleanup(kryo);
        }
    }

    private void cleanup(Kryo kryo) {
        DEFAULT_FACTORY.returnKryo(kryo);
    }

}