package com.me.core.db;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

/**
 * SQL语句包
 * 
 * @author vincent.wu
 *
 */
public final class DBObject {

	/**
	 * sql语句
	 */
	private final String sql;

	/**
	 * 参数##注意添加顺序
	 */
	private final List<DBparams> params = new ArrayList<>();

	private DBObject(final String sql) {
		this.sql = sql;
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static DBObject of(final String sql) {
		return new DBObject(sql);
	}

	/**
	 * 
	 * @param type
	 * @param value
	 */
	public void addParam(int dbType, Object value) {
		params.add(DBparams.of(dbType, value));
	}

	/**
	 * SQL
	 * 
	 * @return
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 
	 * @return
	 */
	public List<DBparams> getParams() {
		return unmodifiableList(params);
	}
}