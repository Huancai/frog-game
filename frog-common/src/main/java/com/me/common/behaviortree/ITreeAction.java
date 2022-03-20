package com.me.common.behaviortree;

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
	 * @param inputParam
	 * @return
	 */
	void enter(final BTInputParam inputParam);

	/**
	 * 离开节点
	 * 
	 * @param inputParam
	 * @return
	 */
	void exit(final BTInputParam inputParam);

	/**
	 * 
	 * @param inputParam
	 * @return
	 */
	boolean execute(final BTInputParam inputParam);
}
