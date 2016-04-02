package com.pipeclamp.util;

/**
 * 
 * @author Brian Remedios
 */
public class ByteUtil {

	private ByteUtil() { }


	public static boolean matchesStart(byte[] source, byte[] prefix) {
		
		if (source.length < prefix.length) return false;
		
		for (int i=0; i<prefix.length; i++) {
			if (prefix[i] != source[i]) return false;
		}
		return true;
	}
}
