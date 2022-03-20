package com.me.game.common.behaviortree.leaf;

import com.me.game.common.behaviortree.AbstractBTNode;
import com.me.game.common.behaviortree.BTInputParam;

/**
 * 叶子节点,评估全部应为true
 * 
 * @author wu_hc
 *
 */
public abstract class AbstractLeafBTNode extends AbstractBTNode {

	@Override
	public final boolean doEvaluate(BTInputParam params) {
		return true;
	}

}
