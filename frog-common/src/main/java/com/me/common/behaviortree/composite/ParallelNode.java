package com.me.common.behaviortree.composite;

import com.me.common.behaviortree.BTInputParam;
import com.me.common.behaviortree.ITreeNode;

/**
 * 平行节点(Parallel
 * Node)：提供了平行的概念，无论子节点返回值是什么都会遍历所有子节点。所以不需要像Selector/Sequence那样预判哪个Child
 * Node应摆前，哪个应摆后。Parallel Node增加方便性的同时，也增加实现和维护复杂度。
 * 
 * @author wu_hc
 *
 */
public class ParallelNode extends AbstractCompositeBTNode {

	@Override
	public boolean doEvaluate(BTInputParam params) {
		return false;
	}

	@Override
	public boolean tick(BTInputParam inputParam) {
		for (final ITreeNode child : childs) {
			if (child.evaluate(inputParam))
				child.tick(inputParam);
		}
		return false;
	}

}
