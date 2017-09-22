package com.example.demo.util;


import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ConcurrentReferenceHashMap;
import sun.reflect.misc.MethodUtil;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 基础工具类
 * 
 * @author lf
 * 
 */
public class Common {

	private static final Logger LOGGER = LoggerFactory.getLogger(Common.class);

	// 方法缓存
	private static final ConcurrentMap<String, Method[]> METHOD_CACHE = new ConcurrentReferenceHashMap<>(64);

	// javaBean序列化缓存
	private static final LoadingCache<Object, JavaBeanSerializer> SERIALIZER_CACHE = CacheBuilder.newBuilder()
			.maximumSize(256)
			.softValues()
			.expireAfterAccess(1, TimeUnit.DAYS)
			.build(new CacheLoader<Object, JavaBeanSerializer>() {
				@Override
				public JavaBeanSerializer load(@Nonnull final Object key) throws Exception {
					LOGGER.info("load cache ：{}",key.getClass().getName());
					return new JavaBeanSerializer(key.getClass());
				}
			});

	public static final Map<String, Object> EMPTY_MAP = new HashMap<>(0);

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Integer nullToZero(Integer num) {
		if (num == null) {
			return 0;
		} else {
			return num;
		}
	}

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Long nullToZero(Long num) {
		if (num == null) {
			return 0L;
		} else {
			return num;
		}
	}

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Double nullToZero(Double num) {
		if (num == null) {
			return 0.0;
		} else {
			return num;
		}
	}

	public static String toString(Object obj){
		return obj == null ? "":obj.toString();
	}

	/**
	 * 获取map中的字符串值
	 * lf
	 * 2017-5-25 16:46:45
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getMapString(Map<String, Object> map,String key){
		if (CollectionUtils.isEmpty(map) ||
				StringUtils.isBlank(key) ||
				!map.containsKey(key)) return "";
		return toString(map.get(key));
	}

	/**
	 * 获取map中的long值
	 * lf
	 * 2017-5-25 16:46:45
	 * @param map
	 * @param key
	 * @return
	 */
	public static long getMapLong(Map<String, Object> map,String key){
		String string = getMapString(map, key);
		return getLong(string);
	}

	/**
	 * 获取map中的long值
	 * lf
	 * 2017-5-25 16:46:45
	 * @param map
	 * @param key
	 * @return
	 */
	public static int getMapInteger(Map<String, Object> map,String key){
		String string = getMapString(map, key);
		return getInteger(string);
	}

	/**
	 * 判读是否存在为空对象
	 * lf
	 * 2017-9-18 16:41:28
	 * @param objs
	 * @return
	 */
	public static boolean hasEmptyObj(Object... objs){
		if (ArrayUtils.isEmpty(objs)) return true;
		for (final Object obj : objs) {
			if (isEmpty(obj)) return true;
		}
		return false;
	}

	/**
	 * 判读是否存在为空字符串
	 * lf
	 * 2017-9-18 16:41:28
	 * @param strs
	 * @return
	 */
	public static boolean hasEmpty(String... strs){
		if (ArrayUtils.isEmpty(strs)) return true;
		for (final String str : strs) {
			if (StringUtils.isBlank(str)) return true;
		}
		return false;
	}

	/**
	 * 获取int值
	 * lf
	 * 2017-5-25 16:43:52
	 * @param obj
	 * @return
	 */
	public static Integer getInteger(Object obj){
		return isEmpty(obj) ? 0 : Integer.valueOf(obj.toString());
	}

	/**
	 * 获取long值
	 * lf
	 * 2017-5-25 16:43:52
	 * @param obj
	 * @return
	 */
	public static Long getLong(Object obj){
		return isEmpty(obj) ? 0L : Long.valueOf(obj.toString());
	}

	/**
	 * 获取double值
	 * lf
	 * 2017-5-25 16:43:52
	 * @param obj
	 * @return
	 */
	public static double getDouble(Object obj){
		return isEmpty(obj) ? 0.0 : Double.valueOf(obj.toString());
	}

	/**
	 * 判读对象是否为空
	 * lf
	 * 2017-5-25 16:41:28
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return StringUtils.isBlank(toString(obj));
	}

	/**
	 * 判读是否存在为空的对象
	 * lf
	 * 2017-5-25 16:41:28
	 *
	 * @param obj 对象
	 * @return
	 */
	public static boolean hasEmpty(Object... obj) {
		if (obj == null || obj.length == 0) return true;
		for (int i = 0; i < obj.length; i++) {
			if (isEmpty(obj[i])) return true;
		}
		return false;
	}

	/**
	 * 是否是数字
	 *
	 * @param obj 对象
	 * @return
	 */
	public static boolean isNumber(Object obj) {
		return NumberUtils.isDigits(toString(obj));
	}

	/**
	 * 首字母小写
	 *
	 * @param str
	 * @return
	 */
	public static String unCapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1))
				.toString();
	}

	/**
	 * lf
	 * 2017-08-17 11:43:57
	 * 将对象转换为map
	 *
	 * @param obj        对象
	 * @param nullRemove 是否清楚value为null的值
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj, boolean nullRemove) {
		if (obj == null) return EMPTY_MAP;
		try {
			JavaBeanSerializer serializer = SERIALIZER_CACHE.get(obj);
			Map<String, Object> map = serializer.getFieldValuesMap(obj);
			if (map.isEmpty()) return EMPTY_MAP;
			if (nullRemove) {
				return Maps.filterValues(map, Objects::nonNull);
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("将对象转换为map失败！", e);
		}

	}

	/**
	 * lf
	 * 2017-08-17 11:43:57
	 * 将对象转换为map，将value为null的清除掉
	 *
	 * @param obj 对象
	 * @return
	 */
	public static Map<String, Object> toMap(Object obj) {
		return toMap(obj, true);
	}

	/**
	 * lf
	 * 2017-08-17 11:43:57
	 * 将对象转换为map，将value为null的清除掉
	 *
	 * @param map   map
	 * @param clazz 类型
	 * @return
	 */
	public static <T> T toObject(Map<String, Object> map, Class<T> clazz) {
		try {
			final T instance = clazz.newInstance();
			final Method[] methods = getMethod(clazz);
			Arrays.stream(methods).filter(method -> method.getName().startsWith("set")).forEach(method -> {
				String name = unCapitalize(method.getName().substring(3));
				if (map.containsKey(name)) {
					try {
						method.invoke(instance, map.get(name));
					} catch (Exception e) {
						LOGGER.error("使用反射将值设置失败: {}", name);
					}
				}
			});
			return instance;
		} catch (Exception e) {
			throw new RuntimeException("将map转化为object失败！", e);
		}
	}

	/**
	 * 通过反射获取方法
	 *
	 * @param clazz 类
	 * @return
	 * @throws NoSuchMethodException
	 */
	private static <T> Method[] getMethod(final Class<T> clazz) throws NoSuchMethodException {
		String name = clazz.getName();
		Method[] methods = METHOD_CACHE.get(name);
		if (methods != null) {
			return methods;
		} else {
			LOGGER.info("--------------refact------------------");
			methods = MethodUtil.getPublicMethods(clazz);
			METHOD_CACHE.put(name, methods);
			return methods;
		}
	}

}
