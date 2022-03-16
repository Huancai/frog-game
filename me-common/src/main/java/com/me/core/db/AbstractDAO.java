package com.me.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO
 * 
 * @author huancai.wu
 *
 */
public abstract class AbstractDAO<E extends AbstractEntity> {

	/**
	 * DB操作类
	 */
	protected final DBHelper db;

	protected AbstractDAO(DBHelper db) {
		this.db = db;
	}

	/**
	 * 新增
	 * 
	 * @param e
	 * @return
	 */
	public int create(E e) {
		return db.create(e.dbObject());
	}

	/**
	 * 修改
	 * 
	 * @param e
	 * @return
	 */
	public int update(E e) {
		return db.update(e.dbObject());
	}

	/**
	 * 查询
	 * 
	 * @param dbObject
	 * @return
	 * @throws SQLException
	 */
	public E retrieve(DBObject dbObject) {
		ResultSet rs = db.retrieve(dbObject);
		try {
			if (rs.last()) {
				return rsToEntity(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询多个
	 * 
	 * @param dbObject
	 * @return
	 * @throws SQLException
	 */
	public List<E> retrieveList(DBObject dbObject) {
		ResultSet rs = db.retrieve(dbObject);
		List<E> l = new ArrayList<>();
		try {
			while (rs.next()) {
				l.add(rsToEntity(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * 删除操作【慎用】
	 * 
	 * @param e
	 * @return
	 */
	public int delete(E e) {
		return db.delete(e.dbObject());
	}

	/**
	 * 通过ID获得entity
	 * 
	 * @param id
	 * @return
	 */
	public E get(long id) {
		return null;
	}

	/**
	 * 结果集转成entity对象
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected abstract E rsToEntity(final ResultSet rs) throws SQLException;

}
