package com.me.game.common.behaviortree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author vincent.wu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BTAnnotation {

	/**
	 * 类别
	 * 
	 * @return
	 */
	int category();

	/**
	 * 名称
	 * 
	 * @return
	 */
	String name();

	/**
	 * 描述
	 * 
	 * @return
	 */
	String desc();
}