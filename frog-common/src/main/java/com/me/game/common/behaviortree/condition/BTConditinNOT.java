package com.me.game.common.behaviortree.condition;

import com.me.game.common.behaviortree.AbstractCondition;
import com.me.game.common.behaviortree.BTInputParam;
import com.me.game.common.behaviortree.ITreeCondition;

public final class BTConditinNOT extends AbstractCondition {

	@Override
	public boolean evaluate(BTInputParam dynamicParam) {
		final ITreeCondition condition = conditions.get(0);
		return null == condition ? true : condition.evaluate(dynamicParam);
	}
}
