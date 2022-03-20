package com.me.common.behaviortree.condition;

import com.me.common.behaviortree.BTInputParam;
import com.me.common.behaviortree.ITreeCondition;
import com.me.common.behaviortree.MultiCondition;

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
