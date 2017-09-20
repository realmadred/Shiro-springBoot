package com.example.demo.config.redis;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.example.demo.config.shiro.MySession;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.web.util.SavedRequest;
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
        kryo.register(MySession.class);
        kryo.register(SavedRequest.class);
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        Output output = new Output(4096);
        try {
            kryo.writeClassAndObject(output, t);
            return output.toBytes();
        } finally {
            output.flush();
            output.close();
        }
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