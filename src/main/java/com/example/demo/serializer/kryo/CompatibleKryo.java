package com.example.demo.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lishen
 */
public class CompatibleKryo extends Kryo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompatibleKryo.class);

    @Override
    public Serializer getDefaultSerializer(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null");
        }
        if (!type.isArray() && !ReflectionUtils.checkZeroArgConstructor(type)) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(type + " has no zero-arg constructor and this will affect the serialization performance");
            }
            return new JavaSerializer();
        }
        return super.getDefaultSerializer(type);
    }
}