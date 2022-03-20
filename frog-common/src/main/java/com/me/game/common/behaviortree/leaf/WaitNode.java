package com.me.game.common.behaviortree.leaf;

import com.me.game.common.behaviortree.BTInputParam;

public final class WaitNode extends AbstractLeafBTNode {

	private int wait;

	@Override
	public boolean prepare() {
		wait = (int) xmlParams.getVariable("param1");
		return true;
	}

	@Override
	public boolean tick(BTInputParam inputParam) {
		return System.currentTimeMillis() > wait;
	}
}
