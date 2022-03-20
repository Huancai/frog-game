package com.me.game.common.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.me.game.common.db.entity.PersonEntity;
import com.me.game.common.db.AbstractDAO;
import com.me.game.common.db.DBHelper;
import com.me.game.common.db.DBObject;

public class PersonDAO extends AbstractDAO<PersonEntity> {

	private static final PersonDAO INSTANCE = new PersonDAO();

	private PersonDAO() {
		super(DBHelper.getInstance());
	}

	public static PersonDAO getInstance() {
		return PersonDAO.INSTANCE;

	}

	@Override
	protected PersonEntity rsToEntity(ResultSet rs) throws SQLException {
		PersonEntity entity = new PersonEntity();
		entity.setId(rs.getLong("id"));
		entity.setName(rs.getString("name"));
		entity.setAge(rs.getInt("age"));
		return entity;
	}

	@Override
	public PersonEntity get(long id) {
		DBObject dbObject = DBObject.of("SELECT * FROM t_g_player WHERE `id` = ?");
		dbObject.addParam(Types.BIGINT, id);
		return retrieve(dbObject);
	}
}
