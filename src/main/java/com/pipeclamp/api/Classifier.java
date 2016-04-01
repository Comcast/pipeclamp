package com.pipeclamp.api;

/**
 * 
 * @author Brian Remedios
 */
public interface Classifier<I extends Object> extends DescriptiveItem {

	/**
	 * 
	 * @return String
	 */
	String id();
	
	/**
	 * Returns a string that classifies the item in a user-specified manner.
	 *
	 * @param item
	 *
	 * @return String
	 */
	String classify(I item);
}
