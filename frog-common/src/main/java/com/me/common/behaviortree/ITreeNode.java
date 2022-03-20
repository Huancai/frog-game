package com.me.common.behaviortree;

/**
 * 行为节点行为
 * 
 * @author wu_hc
 *
 */
public interface ITreeNode {

	/**
	 * 准备
	 * 
	 * @return
	 */
	boolean prepare();

	/**
	 * 添加子节点
	 * 
	 * @param child
	 * @return
	 */
	boolean addChild(ITreeNode child);

	/**
	 * 子节点驱动
	 * 
	 * @param inputParam
	 * @return
	 */
	boolean tick(final BTInputParam inputParam);

	/**
	 * 启动
	 * 
	 * @param inputParam
	 * @return
	 */
	boolean startup(final BTInputParam inputParam);

	/**
	 * 
	 * @param inputParam
	 * @return
	 */
	boolean evaluate(final BTInputParam inputParam);

	/**
	 * 行为树状态
	 * 
	 *
	 */
	enum BTStatus {
		PENDING, // 正在执行
		FINISH, // 成功
		FAIL // 失败
	}
}
