package com.pipeclamp.api;

/**
 * 
 * @author Brian Remedios
 */
public interface ByteSignatureMatcher {

	/**
	 * 
	 * @param data
	 *
	 * @return boolean
	 */
	boolean matches(byte[] data);
}
