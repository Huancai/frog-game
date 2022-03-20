package com.me.common.behaviortree.composite;

import com.me.common.behaviortree.BTInputParam;

/**
 * 选择节点(Selector)：属于组合节点，顺序执行子节点，只要碰到一个子节点返回true，则停止继续执行，并返回true，否则返回false，类似于程序中的逻辑或。
 * 描述：从头到尾，按顺序选择第一个执行条件为真的子节点，遇到True停止。
 * 处理流程：当执行本类型Node时，它将从begin到end迭代执行自己的Child Node：如遇到一个Child
 * Node执行后返回True，那停止迭代，本Node向自己的Parent Node也返回True；否则所有Child
 * Node都返回False，那本Node向自己的Parent Node返回False。
 * 
 * @author wu_hc
 *
 */
public class SelectorNode extends AbstractCompositeBTNode {

	@Override
	public boolean doEvaluate(BTInputParam params) {
		return false;
	}

	@Override
	public boolean tick(BTInputParam inputParam) {
		return false;
	}

}
