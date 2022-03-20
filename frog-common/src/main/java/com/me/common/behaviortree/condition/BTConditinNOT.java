package com.me.common.behaviortree.condition;

import com.me.common.behaviortree.AbstractCondition;
import com.me.common.behaviortree.BTInputParam;
import com.me.common.behaviortree.ITreeCondition;

public final class BTConditinNOT extends AbstractCondition {

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {
		final ITreeCondition condition = conditions.get(0);
		return null == condition ? true : condition.evaluate(dynamicParam);
	}
}
