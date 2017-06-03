package org.light4j.framework.mvc.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.light4j.framework.util.CollectionUtil;
import org.light4j.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作助手类
 * 
 * @author longjiazuo
 * 
 */
public final class DataBaseHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataBaseHelper.class);
	private static final QueryRunner QUERY_RUNNER;
	private static final BasicDataSource DATA_SOURCE;
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		Properties conf = PropsUtil.loadProps("config.properties");
		String driver = conf.getProperty("jdbc.driver");
		String url = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");

		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(driver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				conn = DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				LOGGER.error("get connection failure", e);
				throw new RuntimeException(e);
			} finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}

	/**
	 * 根据实体名称获取表名
	 * 
	 * @param entityClass
	 * @return
	 */
	public static String getTableName(Class<?> entityClass) {
		if (entityClass != null) {
			return entityClass.getSimpleName();
		}
		return null;
	}

	/**
	 * 获取客户列表
	 * 
	 * @param entityClass
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass, String sql,
			Object... params) {
		List<T> entityList;
		try {
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(
					entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity list  failure", e);
			throw new RuntimeException(e);
		}
		return entityList;
	}

	/**
	 * 查询客户实体
	 * 
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass, String sql,
			Object... params) {
		T entity;
		try {
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(
					entityClass), params);
		} catch (SQLException e) {
			LOGGER.error("query entity   failure", e);
			throw new RuntimeException(e);
		}
		return entity;
	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String, Object>> executeQuery(String sql,
			Object... params) {
		List<Map<String, Object>> result;
		Connection conn = getConnection();
		try {
			result = QUERY_RUNNER
					.query(conn, sql, new MapListHandler(), params);
		} catch (SQLException e) {
			LOGGER.error("execute query failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 执行更新语句(包括update,insert,delete)
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int executeUpdate(String sql, Object... params) {
		int rows = 0;
		Connection conn = getConnection();
		try {
			rows = QUERY_RUNNER.update(conn, sql,params);
		} catch (SQLException e) {
			LOGGER.error("execute update failure", e);
			throw new RuntimeException(e);
		}
		return rows;
	}

	/**
	 * 插入实体
	 * 
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass,
			Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}

		String sql = "INSERT INTO " + getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for (String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}

		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql += columns + "VALUES" + values;

		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 更新实体
	 * 
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass, long id,
			Map<String, Object> fieldMap) {
		if (CollectionUtil.isEmpty(fieldMap)) {
			LOGGER.error("can not update entity:fieldMap is empty");
			return false;
		}

		String sql = "UPDATE " + getTableName(entityClass) + " SET ";
		StringBuilder columns = new StringBuilder();
		for (String fieldName : fieldMap.keySet()) {
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";

		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();

		return executeUpdate(sql, params) == 1;
	}

	/**
	 * 删除实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
		String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
		return executeUpdate(sql, id) == 1;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param filePath
	 */
	public static void executeSqlFile(String filePath) {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String sql;
			while ((sql = reader.readLine()) != null) {
				DataBaseHelper.executeUpdate(sql);
			}
		} catch (IOException e) {
			LOGGER.error("execute sql file failure", e);
			throw new RuntimeException(e);
		}
	}
}