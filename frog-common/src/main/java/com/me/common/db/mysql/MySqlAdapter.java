package com.me.common.db.mysql;

import java.sql.Connection;

import com.me.common.db.DBAcceptor;

public final class MySqlAdapter implements DBAcceptor {

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public boolean initialize() {
		return false;
	}

	@Override
	public void cleanUp() {
	}
}