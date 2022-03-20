package com.me.game.common.db.sqlite;

import java.sql.Connection;

import com.me.game.common.db.DBAcceptor;

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
