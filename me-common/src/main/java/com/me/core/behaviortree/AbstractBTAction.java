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
	public void enter(BTInputParam intputParam) {
	}

	@Override
	public void exit(BTInputParam intputParam) {
	}

	@Override
	public boolean excute(BTInputParam intputParam) {
		enter(intputParam);
		tick(intputParam);
		exit(intputParam);
		return true;
	}

	/**
	 * 子类实现
	 * 
	 * @param intputParam
	 * @return
	 */
	public abstract boolean tick(BTInputParam intputParam);
}
