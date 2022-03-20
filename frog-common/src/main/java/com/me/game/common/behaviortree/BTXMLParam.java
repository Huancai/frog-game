package com.me.game.common.behaviortree;

import java.util.HashMap;
import java.util.Map;

/**
 * 静态参数，一般理解为配置表里的参数
 * 
 * @author wu_hc
 *
 */
public final class BTXMLParam implements IContext {

	private final Map<String, Object> variables = new HashMap<>();

	@Override
	public Object getVariable(String name) {
		return null;
	}

	@Override
	public boolean setVariable(String name, Object value) {
		return null != variables.put(name, value);
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean clearVariable(String name) {
		return false;
	}

}
