package com.zc.common.core.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库信息工具
 * 
 * @author zhangkaoqing
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2012-4-24 上午10:17:13
 * 
 */
public class DataBaseUtils {
	private DataBaseUtils() {
	}

	/**
	 * 获取数据库表的表名列表
	 * 
	 * @param connection
	 * @return
	 */
	public static List<String> getDataBaseTables(Connection connection) {
		List<String> list = new ArrayList<String>();
		ResultSet resultSet = null;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			resultSet = databaseMetaData.getTables(null, null, null, null);
			while (resultSet.next()) {
				list.add(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(resultSet, connection);
		}
		return list;
	}

	/**
	 * 获取数据库的版本号
	 * 
	 * @param connection
	 * @return
	 */
	public static String getDataBaseVerson(Connection connection) {
		String result = null;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			result = databaseMetaData.getDatabaseMajorVersion() + "."
					+ databaseMetaData.getDatabaseMinorVersion();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, connection);
		}
		return result;
	}

	/**
	 * 获取数据库的名称和产品的版本号
	 * 
	 * @param connection
	 * @return
	 */
	public static String getProductVersion(Connection connection) {
		String result = null;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			result = databaseMetaData.getDatabaseProductName()
					+ databaseMetaData.getDatabaseProductVersion();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, connection);
		}
		return result;
	}

	/**
	 * 判断数据库是否支持事务，支持的事务级别是什么
	 * 
	 * @param connection
	 * @return
	 */
	public static int getTransactionLevel(final Connection connection) {
		int result = -1;
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if (databaseMetaData.supportsTransactions()) {
				if (databaseMetaData
						.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED)) {
					result = Connection.TRANSACTION_READ_COMMITTED;
				} else if (databaseMetaData
						.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED)) {
					result = Connection.TRANSACTION_READ_UNCOMMITTED;
				} else if (databaseMetaData
						.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)) {
					result = Connection.TRANSACTION_REPEATABLE_READ;
				} else if (databaseMetaData
						.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
					result = Connection.TRANSACTION_SERIALIZABLE;
				}
			} else {
				result = 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, connection);
		}

		return result;
	}

	private static void close(ResultSet resultSet, Connection connection) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
