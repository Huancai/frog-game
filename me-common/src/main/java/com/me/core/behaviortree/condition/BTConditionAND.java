package com.me.core.behaviortree.condition;

import com.me.core.behaviortree.BTInputParam;
import com.me.core.behaviortree.ITreeCondition;
import com.me.core.behaviortree.MultiCondition;

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
