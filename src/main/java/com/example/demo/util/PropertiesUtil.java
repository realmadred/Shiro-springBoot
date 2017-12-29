package com.example.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public final class PropertiesUtil extends PropertyPlaceholderConfigurer {
    private static Map<String, String> props = new HashMap<>();

    public static Map<String, String> getProperties() {
        return props;
    }

    /**
     * Get a value based on key , if key does not exist , null is returned
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return props.get(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * Get a value based on key , if key does not exist , null is returned
     *
     * @param key
     * @return
     */
    public static String getString(String key, String defaultValue) {
        try {
            String value = props.get(key);
            if (Common.isEmpty(value)) {
                return defaultValue;
            }
            return value;
        } catch (MissingResourceException e) {
            return defaultValue;
        }
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        String value = props.get(key);
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        return Integer.valueOf(value);
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        String value = props.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    /**
     * 从配置文件中取得 long 值，若无（或解析异常）则返回默认值
     *
     * @param keyName      属性名
     * @param defaultValue 默认值
     * @return 属性值
     */
    public static long getLong(String keyName, long defaultValue) {
        String value = getString(keyName);
        if (value != null && value.length() > 0) {
            try {
                return Long.parseLong(value.trim());
            } catch (Exception e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = props.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.valueOf(value.trim());
    }
}
