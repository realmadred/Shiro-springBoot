package com.example.demo.config.redis;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


/**
 * lf
 * 2017-09-19 16:47:17
 * 序列化
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private Kryo kryo = new Kryo();
    {
        kryo.register(SimpleSession.class);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        byte[] buffer = new byte[2048];
        Output output = new Output(buffer);
        kryo.writeClassAndObject(output, t);
        return output.toBytes();
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (ArrayUtils.isEmpty(bytes)) return null;
        Input input = new Input(bytes);
        @SuppressWarnings("unchecked")
        T t = (T) kryo.readClassAndObject(input);
        return t;
    }

}