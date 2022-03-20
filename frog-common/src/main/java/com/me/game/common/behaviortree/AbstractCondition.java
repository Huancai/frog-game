package com.me.game.common.behaviortree;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wu_hc
 *
 */
public abstract class AbstractCondition implements ITreeCondition {

	/**
	 * 条件列表
	 */
	protected final List<ITreeCondition> conditions = new ArrayList<>(2);

	/**
	 * 条件参数
	 */
	private BTXMLParam xmlParams;

	@Override
	public boolean prepare() {
		return true;
	}

	/**
	 * 添加条件
	 * 
	 * @param condition
	 * @return
	 */
	public boolean addCondition(final ITreeCondition condition) {
		return conditions.add(condition);
	}

	/**
	 * 
	 * @return
	 */
	public BTXMLParam getXmlParams() {
		return xmlParams;
	}

	/**
	 * 
	 * @param xmlParams
	 */
	public void setXmlParams(BTXMLParam xmlParams) {
		this.xmlParams = xmlParams;
	}

}
