package com.example.demo.util;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 基础工具类
 * 
 * @author lf
 * 
 */
public class Common {

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
	 * 判读对象是否为空
	 * lf
	 * 2017-5-25 16:41:28
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return StringUtils.isBlank(toString(obj));
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


}
