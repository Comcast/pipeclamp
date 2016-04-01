package com.pipeclamp.util;

import java.io.PrintStream;

/**
 *
 * @author Brian Remedios
 */
public class StringUtil {

	private StringUtil() { }

	public static byte[] asByteArray(String hexString) {
		
		int len = hexString.length();
		if (len % 2 == 1) return null;	// must have even number of chars
		
	    byte[] data = new byte[len / 2];
	    
	    for (int i=0; i<len; i += 2) {
	        data[i/2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
	                             + Character.digit(hexString.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static String digitsIn(String text) {
		
		if (text == null) return null;
		
		int len = text.length();
		if (len == 0) return null;
		
		StringBuilder sb = new StringBuilder(len);
		for (int i=0; i<len; i++) {
			char ch = text.charAt(i);
			if (Character.isDigit(ch)) sb.append(ch);
		}
		return sb.toString();
	}
	
	public static void printLeftJustified(String str, PrintStream out, int width) {

		int len = str.length();
		if (len == width) {
			out.print(str);
			return;
		}
		out.print(str);
		for (int i=width; i>len; i--) out.print(' ');
	}

	public static String render(String[] values, String separator) {

		if (values == null || values.length == 0) return "";
		if (values.length == 1) return values[0];

		StringBuilder sb = new StringBuilder(values[0]);
		for (int i=1; i<values.length; i++)
			sb.append(separator).append(values[i]);

		return sb.toString();
	}
}
