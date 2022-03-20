package com.me.common.db;

/**
 * Entity
 * 
 * @author vincent.wu
 *
 */
public abstract class AbstractEntity implements Cloneable {

	/**
	 * 指令
	 */
	protected byte operation = EntityOperation.N;

	/**
	 * 设置操作指令
	 * 
	 * @param operation
	 */
	public final void setOperation(final byte operation) {
		if ((this.operation == EntityOperation.C) && (operation == EntityOperation.U)) {
			return;
		}
		this.operation = operation;
	}

	/**
	 * 1,获取SQL以及参数数据【eg:insert into t_s_player values(?,?,?)】
	 * 
	 * @return
	 */
	public abstract DBObject dbObject();

	/**
	 * 2,获取SQL语句【eg:insert into t_s_player values(1,'vincent.wu',23)】
	 * 
	 * @return
	 */
	public abstract String syncSQL();

	/**
	 * 3,同步数据使用DAO
	 */
	public void syncToDB() {

	}

	/**
	 * <p>
	 * C reate new records
	 * </p>
	 * <p>
	 * R etrieve existing records
	 * </p>
	 * <p>
	 * U pdate existing records
	 * </p>
	 * <p>
	 * Delete existing records.
	 * </p>
	 *
	 */
	public interface EntityOperation {
		byte N = 1 << 1; // unknow
		byte C = 1 << 2; // create
		byte U = 1 << 3; // update
		byte D = 1 << 4; // delete
	}
}
