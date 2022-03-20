package com.me.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtil {

	/**
	 * 
	 * @param dbObject
	 * @return
	 */
	public static String dBObjectConverToSQL(DBObject dbObject) {
		return null;
	}

	/**
	 * 关闭Connection、Statem和ResultSet
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void closeConn(final Connection conn, final Statement stmt, ResultSet rs) {
		try {
			if (rs == null || rs.isClosed()) {
				return;
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(conn, stmt);
		}

	}

	/**
	 * 关闭Conne和Statement
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void closeConn(final Connection conn, Statement stmt) {
		try {
			if (stmt == null || stmt.isClosed()) {
				return;
			}
			if (stmt instanceof PreparedStatement) {
				((PreparedStatement) stmt).clearParameters();
			}
			stmt.close();
			stmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * 关闭Connection
	 * 
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		try {
			if (conn == null || conn.isClosed()) {
				return;
			}
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
