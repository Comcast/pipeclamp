package com.pipeclamp.constraints.string;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Characterizes a well-known datatype as a regular expression.
 *
 * @author Brian Remedios
 */
public class RegexDescriptor {

	public final String id;
	public final String regex;
	public final String description;
	private final Pattern pattern;

	public static final RegexDescriptor IPAddress 	 = new RegexDescriptor("ipAddress", "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", null);
	public static final RegexDescriptor Hostname 	 = new RegexDescriptor("hostName", "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$", null);
	public static final RegexDescriptor EMailAddress = new RegexDescriptor("emailAddress", "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", "simple email valiation only");
	public static final RegexDescriptor IPV4		 = new RegexDescriptor("IPV4", "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", null);
	public static final RegexDescriptor IPV6		 = new RegexDescriptor("IPV6", "\\A(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}\\Z", null);
	public static final RegexDescriptor URL			 = new RegexDescriptor("url", "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/", null);

	public static final Map<String, RegexDescriptor> ALL = asMap(
		IPAddress, Hostname, EMailAddress, IPV4, IPV6, URL
		);

	public static Map<String, RegexDescriptor> asMap(RegexDescriptor ...theDescriptors) {

		Map<String, RegexDescriptor> map = new HashMap<String, RegexDescriptor>();
		for (RegexDescriptor rd : theDescriptors) map.put(rd.id, rd);
		return Collections.unmodifiableMap(map);
	}

	private RegexDescriptor(String theId, String theRegex, String theDescription) {

		id = theId;
		regex = theRegex;
		description = theDescription;

		pattern = Pattern.compile(theRegex);
	}

	public boolean matches(String text) {
		return pattern.matcher(text).matches();
	}

	public static void main(String[] args) {

		String id = args[0];
		String text = args[1];

		for (RegexDescriptor rd : ALL.values()) {
			if (id.equals(rd.id)) {
				boolean matched = rd.matches(text);
				if (matched) {
					System.out.println("pattern " + id + " matches " + text) ;
					} else {
						System.out.println("pattern " + id + " does not match " + text) ;
					}
			}
		}

	}
}
