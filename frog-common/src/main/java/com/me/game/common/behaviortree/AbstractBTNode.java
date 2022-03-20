package com.me.game.common.behaviortree;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为节点抽象类
 * 
 * @author wu_hc
 *
 */
public abstract class AbstractBTNode implements ITreeNode {

	/**
	 * 子节点
	 */
	protected final List<ITreeNode> childs = new ArrayList<>(16);

	/**
	 * 行为节点的条件
	 */
	private ITreeCondition condtion;

	/**
	 * 节点的配置参数
	 */
	protected BTXMLParam xmlParams;

	@Override
	public boolean prepare() {
		return true;
	}

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {
		return doEvaluate(dynamicParam) && (null == condtion || condtion.evaluate(dynamicParam));
	}

	/**
	 * 设置条件
	 * 
	 * @param condtion
	 */
	public void setCondtion(ITreeCondition condtion) {
		this.condtion = condtion;
	}

	/**
	 * 设置参数
	 * 
	 */
	public void setXmlParams(BTXMLParam xmlParams) {
		this.xmlParams = xmlParams;
	}

	@Override
	public boolean addChild(ITreeNode child) {
		return childs.add(child);
	}

	/**
	 * 启动
	 */
	@Override
	public final boolean startup(BTInputParam inputParam) {
		return tick(inputParam);
	}

	/**
	 * 内在前提的校验
	 * 
	 * @param params
	 * @return
	 */
	public abstract boolean doEvaluate(BTInputParam params);
}
