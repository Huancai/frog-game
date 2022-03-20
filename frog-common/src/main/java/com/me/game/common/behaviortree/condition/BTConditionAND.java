package com.me.game.common.behaviortree.condition;

import com.me.game.common.behaviortree.BTInputParam;
import com.me.game.common.behaviortree.ITreeCondition;
import com.me.game.common.behaviortree.MultiCondition;

/**
 * 条件AND,全部为true返回true,否则返回false
 * 
 * @author wu_hc
 *
 */
public final class BTConditionAND extends MultiCondition {

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {
		for (final ITreeCondition condition : conditions) {
			if (!condition.evaluate(dynamicParam))
				return false;
		}
		return true;
	}

}
