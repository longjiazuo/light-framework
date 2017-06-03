package org.light4j.framework.mvc.bean;

import java.util.Map;

import org.light4j.framework.util.CastUtil;

/**
 * 请求参数对象
 * 
 * @author longjiazuo
 * 
 */
public class Param {

	private Map<String, Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 根据参数名获取long型参数值
	 * 
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		return CastUtil.castLong(paramMap.get(name));
	}

	/**
	 * 获取所有字段信息
	 * 
	 * @return
	 */
	public Map<String, Object> getMap() {
		return paramMap;
	}
}
