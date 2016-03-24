package com.pipeclamp.util;

public class HtmlUtil {

	private HtmlUtil() { }

	public static void addHeader(StringBuilder sb, String content) {
		sb.append("<tr>").append(content).append("</tr>");
	}
	
	public static void addColumn(StringBuilder sb, String content) {
		sb.append("<td>").append(content).append("</td>");
	}
	
	public static void addHref(StringBuilder sb, String text, String link) {
		// TODO
	}
}
