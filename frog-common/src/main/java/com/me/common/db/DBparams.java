package com.me.common.db;

public final class DBparams {
	/**
	 * 类型
	 * 
	 * @see java.sql.Types
	 */
	public final int dbType;

	/**
	 * 值
	 */
	public final Object value;

	private DBparams(int dbType, Object value) {
		this.dbType = dbType;
		this.value = value;
	}

	public static DBparams of(int dbType, Object value) {
		return new DBparams(dbType, value);
	}
}
