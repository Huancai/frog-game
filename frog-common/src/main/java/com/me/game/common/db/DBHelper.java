package com.me.game.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DB操作工具类
 * 
 * @author vincent.wu
 *
 */
public final class DBHelper {

	/**
	 * 
	 */
	private DBAcceptor dbAcceptor;

	private static class LazyInit {
		private static final DBHelper HELPER = new DBHelper();
	}

	public static DBHelper getInstance() {
		return LazyInit.HELPER;
	}

	public void cleanUp() {
		dbAcceptor.cleanUp();
	}

	/**
	 * 添加
	 * 
	 * @param dbObject
	 * @return
	 */
	public int create(DBObject dbObject) {
		return executeNotSelect(dbObject);
	}

	/**
	 * 修改
	 * 
	 * @param dbObject
	 * @return
	 */
	public int update(DBObject dbObject) {
		return executeNotSelect(dbObject);
	}

	/**
	 * 查询
	 * 
	 * @param dbObject
	 * @return
	 */
	public ResultSet retrieve(DBObject dbObject) {
		Connection c = dbAcceptor.getConnection();
		PreparedStatement statement = null;
		try {
			statement = c.prepareStatement(dbObject.getSql());
			prepareStatement(statement, dbObject.getParams());
			return statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param dbObject
	 * @return
	 */
	public int delete(DBObject dbObject) {
		throw new UnsupportedOperationException("Delete operation not support!!!");
	}

	/**
	 * UPDATE or DELETE or INSERT
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeNotSelect(final DBObject params) {
		int result = -1;
		Connection conn = dbAcceptor.getConnection();
		if (conn == null) {
			return result;
		}
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(params.getSql());
			prepareStatement(pstmt, params.getParams());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println(String.format("执行[%s]脚本出错", params.getSql()));
		} finally {
			DBUtil.closeConn(conn, pstmt);
		}
		return result;
	}

	/**
	 * 组装参数
	 * 
	 * @param pstmt
	 * @param parms
	 * @return
	 * @throws SQLException
	 */
	private static PreparedStatement prepareStatement(final PreparedStatement pstmt, List<DBparams> parms)
			throws SQLException {
		if (pstmt == null || parms == null) {
			return null;
		}
		for (int i = 0; i < parms.size(); i++) {
			final DBparams param = parms.get(i);
			if (null == param.value)
				pstmt.setNull(i + 1, param.dbType);
			else
				pstmt.setObject(i + 1, param.value, param.dbType);
		}
		return pstmt;
	}
}