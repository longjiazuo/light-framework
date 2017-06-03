package org.light4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * 
 * @author longjiazuo
 * 
 */
public final class ReflectionUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReflectionUtil.class);

	/**
	 * 创建实例
	 * 
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new instance failure", e);
			throw new RuntimeException();
		}
		return instance;
	}

	/**
	 * 调用方法
	 * 
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object obj, Method method, Object args) {
		Object result;
		method.setAccessible(true);
		try {
			result = method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error("invoke method failure", e);
			throw new RuntimeException();
		}
		return result;
	}

	/**
	 * 设置成员变量的值
	 * 
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error("set field failure", e);
			throw new RuntimeException();
		}
	}
}
