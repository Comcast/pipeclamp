package com.pipeclamp.api;

/**
 *
 * @author Brian Remedios
 */
public interface SignatureMatcher extends RegisteredItem {

	/**
	 * 
	 * @param data
	 *
	 * @return boolean
	 */
	boolean matches(byte[] data);
}
