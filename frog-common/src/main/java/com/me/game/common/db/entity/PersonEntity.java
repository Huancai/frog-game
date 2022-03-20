package com.me.game.common.db.entity;

import java.sql.Types;

import com.me.game.common.db.AbstractEntity;
import com.me.game.common.db.DBObject;
import com.me.game.common.db.dao.PersonDAO;

/**
 * 
 * @author Vincent
 *
 */
public final class PersonEntity extends AbstractEntity {

	private long id;
	private String name;
	private int age;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	protected PersonEntity clone() {
		PersonEntity entity = new PersonEntity();
		entity.setAge(this.age);
		entity.setId(this.id);
		entity.setName(this.name);
		return entity;
	}

	@Override
	public DBObject dbObject() {
		if (EntityOperation.C == operation) {
			DBObject o = DBObject.of("INSERT INTO t_g_person(`id`,`name`,`age`) VALUES (?,?,?)");
			o.addParam(Types.BIGINT, this.id);
			o.addParam(Types.VARCHAR, this.name);
			o.addParam(Types.INTEGER, this.age);
			return o;
		} else if (EntityOperation.U == operation) {
			DBObject o = DBObject.of("UPDATE t_g_person SET `name`=? ,`age`=? WHERE `id`=?");
			o.addParam(Types.VARCHAR, this.name);
			o.addParam(Types.INTEGER, this.age);
			o.addParam(Types.BIGINT, this.id);
			return o;
		}
		return null;
	}

	@Override
	public void syncToDB() {
		if (this.operation == EntityOperation.C)
			PersonDAO.getInstance().create(this);
		else if (this.operation == EntityOperation.U)
			PersonDAO.getInstance().update(this);
		else if (this.operation == EntityOperation.D)
			PersonDAO.getInstance().delete(this);
		else
			System.out.println("error");
	}

	@Override
	public String syncSQL() {
		DBObject o = dbObject();
		String tmp = o.getSql().replace("?", "%s");
		return String.format(tmp, o.getParams().toArray());
	}

}
