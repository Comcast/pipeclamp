package com.pipeclamp.util;

/**
 *
 * @author Brian Remedios
 */
public class ByteUtil {

	private ByteUtil() { }

	/**
	 *
	 * @param source
	 * @param prefix
	 *
	 * @return boolean
	 */
	public static boolean matchesStart(byte[] source, byte[] prefix) {

		if (source == null) return false;

		if (source.length < prefix.length) return false;

		for (int i=0; i<prefix.length; i++) {
			if (prefix[i] != source[i]) return false;
		}
		return true;
	}


	/**
	 *
	 * @param source
	 * @param prefix
	 * @param offset
	 *
	 * @return boolean
	 */
	public static boolean matches(byte[] source, byte[] prefix, int offset) {

		if (source == null) return false;

		if (source.length < prefix.length) return false;

		int end = prefix.length + offset;

		for (int i=offset; i<end; i++) {
			if (prefix[i] != source[i]) return false;
		}
		return true;
	}
}
