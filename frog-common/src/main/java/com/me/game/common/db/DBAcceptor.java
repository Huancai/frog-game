package com.me.game.common.db;

import java.sql.Connection;

/**
 * 
 * @author vincent.wu
 *
 */
public interface DBAcceptor {

	/**
	 * 初始化
	 * 
	 * @return
	 */
	boolean initialize();

	/**
	 * 清理工作
	 * 
	 * @return
	 */
	void cleanUp();

	/**
	 * 获得数据库链接
	 * 
	 * @return
	 */
	Connection getConnection();
}
