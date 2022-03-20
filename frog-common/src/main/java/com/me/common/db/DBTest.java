package com.me.common.db;

import java.sql.Types;
import java.util.List;

public final class DBTest {

	public static void main(String[] args) {
		// PersonEntity entity = new PersonEntity();
		// entity.setAge(23);
		// entity.setId(23);
		//
		// PersonDao.getInstance().create(entity);

		DBObject o = DBObject.of("insert into t_s_player values (?,?,?,?)");
		o.addParam(Types.BIGINT, 2000004345);
		o.addParam(Types.BIGINT, 2000004345);
		o.addParam(Types.BIGINT, 2000004345);
		o.addParam(Types.BIGINT, 2000004345);

		String tmp = o.getSql().replace("?", "%s");
		System.out.println(tmp);
		List<DBparams> p = o.getParams();
		Object[] v = new Object[p.size()];
		for (int i = 0; i < p.size(); i++) {
			v[i] = p.get(i).value;
		}
		System.out.println(String.format(tmp, v));
	}

}
