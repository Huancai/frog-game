package com.me.core.db.sqlite;

import java.sql.Connection;

import com.me.core.db.DBAcceptor;

/**
 * 
 * @author vincent.wu
 *
 */
public final class SqliteAdapter implements DBAcceptor {

	@Override
	public boolean initialize() {
		return false;
	}

	@Override
	public void cleanUp() {

	}

	@Override
	public Connection getConnection() {
		return null;
	}

}
