package com.example.demo.serializer.kryo;

/**
 * @author lishen
 */
public class ReflectionUtils {

    public static boolean checkZeroArgConstructor(Class clazz) {
        try {
            clazz.getDeclaredConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}