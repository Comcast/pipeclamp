package com.pipeclamp.api;

/**
 * 
 * @author Brian Remedios
 *
 * @param <I>
 */
public interface ValueSource<I extends Object> {

	/**
	 * Retrieve the current value.
	 *
	 * @return <I>
	 */
	I getValue();
}
