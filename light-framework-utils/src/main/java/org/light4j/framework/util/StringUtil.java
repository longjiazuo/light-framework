package org.light4j.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * 
 * @author longjiazuo
 * 
 */
public final class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str != null) {
			str = str.trim();
		}
		return StringUtils.isEmpty(str);
	}

	/**
	 * 判断字符串是否非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 分割字符串
	 * 
	 * @param str
	 * @param separatorChars
	 * @return
	 */
	public static String[] splitString(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}
}
