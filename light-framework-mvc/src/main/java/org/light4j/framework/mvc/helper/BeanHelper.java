package org.light4j.framework.mvc.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.light4j.framework.util.ReflectionUtil;

/**
 * Bean助手类
 * 
 * @author longjiazuo
 * 
 */
public final class BeanHelper {

	/**
	 * 定义Bean映射(用于存放Bean类与Bean实例的映射关系)
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for (Class<?> beanClass : beanClassSet) {
			Object obj = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}

	/**
	 * 获取Bean映射
	 * 
	 * @return
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取Bean实例
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			throw new RuntimeException("can not get bean by class:" + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}
}
