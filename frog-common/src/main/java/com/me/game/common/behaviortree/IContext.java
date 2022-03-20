
package com.me.game.common.behaviortree;

/**
 * 
 */
public interface IContext {
	/**
	 * Returns the value of a variable whose name is <code>name</code>, or null
	 * if it is not found.
	 * 
	 * @param name
	 *            the name of the variable to retrieve.
	 * 
	 * @return the value of a variable whose name is <code>name</code>, or null
	 *         if it does not exist.
	 */
	public Object getVariable(String name);

	/**
	 * Sets the value of a variable. If the variable already existed, its value
	 * is overwritten. <code>value</code> may be null in order to clear the
	 * value of the variable.
	 * 
	 * @param name
	 *            the name of the variable.
	 * @param value
	 *            the value for the variable.
	 * @return true if a variable with the same name already existed, and false
	 *         otherwise.
	 */
	public boolean setVariable(String name, Object value);

	/**
	 * Clears the set of all the variables of the context.
	 */
	public void clear();

	/**
	 * Clears the value of a variable. This is equivalent to calling
	 * {@link #setVariable(String, Object)} with a value of null for the second
	 * argument.
	 * 
	 * @param name
	 *            the name of a variable.
	 * @return true if the variable existed, and false otherwise.
	 */
	public boolean clearVariable(String name);

}
