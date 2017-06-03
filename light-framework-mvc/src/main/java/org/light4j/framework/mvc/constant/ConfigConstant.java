package org.light4j.framework.mvc.constant;

/**
 * 提供相关配置项常量
 * 
 * @author longjiazuo
 * 
 */
public interface ConfigConstant {
	/**
	 * 配置文件
	 */
	final static String CONFIG_FILE = "light.properties";

	/**
	 * 数据库相关配置
	 */
	final static String JDBC_DRIVER = "light.framework.jdbc.driver";
	final static String JDBC_URL = "light.framework.jdbc.url";
	final static String JDBC_USERNAME = "light.framework.jdbc.username";
	final static String JDBC_PASSWORD = "light.framework.jdbc.password";

	/**
	 * 工程路径相关配置
	 */
	final static String APP_BASE_PACKAGE = "light.framework.app.base_package";
	final static String APP_JSP_PATH = "light.framework.app.jsp_path";
	final static String APP_ASSET_PATH = "light.framework.app.asset_path";
}
