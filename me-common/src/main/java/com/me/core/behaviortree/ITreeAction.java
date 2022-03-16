package com.me.core.behaviortree;

/**
 * 动作节点
 * 
 * @author wu_hc
 *
 */
public interface ITreeAction {

	/**
	 * 进入节点
	 * 
	 * @param intputParam
	 * @return
	 */
	void enter(final BTInputParam intputParam);

	/**
	 * 离开节点
	 * 
	 * @param intputParam
	 * @return
	 */
	void exit(final BTInputParam intputParam);

	/**
	 * 
	 * @param intputParam
	 * @return
	 */
	boolean excute(final BTInputParam intputParam);
}
