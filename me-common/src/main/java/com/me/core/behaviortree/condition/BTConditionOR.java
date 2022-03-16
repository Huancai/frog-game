package com.me.core.behaviortree.condition;

import com.me.core.behaviortree.BTInputParam;
import com.me.core.behaviortree.ITreeCondition;
import com.me.core.behaviortree.MultiCondition;

/**
 * 条件OR,只有有一个满足则返回true
 * 
 * @author wu_hc
 *
 */
public final class BTConditionOR extends MultiCondition {

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {

		for (final ITreeCondition condition : conditions) {
			if (condition.evaluate(dynamicParam))
				return true;
		}
		return false;
	}

}
