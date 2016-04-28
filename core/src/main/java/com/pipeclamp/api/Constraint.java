package com.pipeclamp.api;

/**
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public interface Constraint<V extends Object> extends TaggedItem, ParametricItem, RegisteredItem {

	/**
	 * 
	 * @param aDescription
	 */
	void description(String aDescription);
}