package com.pipeclamp.util;

import java.io.PrintStream;

/**
 *
 * @author Brian Remedios
 */
public class StringUtil {

	private StringUtil() { }


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
