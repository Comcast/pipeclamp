package com.pipeclamp.metrics.functions;

/**
 * 
 * @author Brian Remedios
 */
public interface Classifier {

	/**
	 * Returns a string that classifies the item in a user-specified manner.
	 *
	 * @param item
	 *
	 * @return String
	 */
	String classify(Object item);
}
