package com.me.core.behaviortree;

/**
 * 动作节点，对于行为树来说，此节点没有条件，只管执行
 * 
 * @author wu_hc
 *
 */
public abstract class AbstractBTAction implements ITreeAction {

	/**
	 * 动作节点配置参数
	 */
	protected BTXMLParam xmlParam;

	/**
	 * 
	 * @return
	 */
	public BTXMLParam getXmlParam() {
		return xmlParam;
	}

	/**
	 * 
	 * @param xmlParam
	 */
	public void setXmlParam(BTXMLParam xmlParam) {
		this.xmlParam = xmlParam;
	}

	@Override
	public void enter(BTInputParam inputParam) {
	}

	@Override
	public void exit(BTInputParam inputParam) {
	}

	@Override
	public boolean execute(BTInputParam inputParam) {
		enter(inputParam);
		tick(inputParam);
		exit(inputParam);
		return true;
	}

	/**
	 * 子类实现
	 * 
	 * @param inputParam
	 * @return
	 */
	public abstract boolean tick(BTInputParam inputParam);
}
