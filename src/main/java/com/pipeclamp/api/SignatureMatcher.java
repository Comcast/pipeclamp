package com.pipeclamp.api;

/**
 *
 * @author Brian Remedios
 */
public interface SignatureMatcher {

	/**
	 * 
	 * @param data
	 *
	 * @return boolean
	 */
	boolean matches(byte[] data);
}
