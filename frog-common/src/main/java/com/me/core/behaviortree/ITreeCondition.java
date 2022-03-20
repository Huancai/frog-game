package com.me.core.behaviortree;

/**
 * 
 * @author wu_hc
 *
 */
public interface ITreeCondition {

	/**
	 * 准备
	 * 
	 * @return
	 */
	boolean prepare();

	/**
	 * 条件评估
	 * 
	 * @param dynamicParam
	 * @return
	 */
	boolean evaluate(final BTInputParam dynamicParam);

	/**
	 * 添加条件
	 * 
	 * @param condition
	 * @return
	 */
	boolean addCondition(final ITreeCondition condition);
}
