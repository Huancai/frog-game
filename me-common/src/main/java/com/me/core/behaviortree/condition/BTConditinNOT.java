package com.me.core.behaviortree.condition;

import com.me.core.behaviortree.AbstractCondition;
import com.me.core.behaviortree.BTInputParam;
import com.me.core.behaviortree.ITreeCondition;

public final class BTConditinNOT extends AbstractCondition {

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {
		final ITreeCondition condition = conditions.get(0);
		return null == condition ? true : condition.evaluate(dynamicParam);
	}
}
