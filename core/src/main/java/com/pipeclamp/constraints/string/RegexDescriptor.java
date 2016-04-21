package com.pipeclamp.constraints.string;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Objects;
import com.pipeclamp.AbstractRegisteredItem;

/**
 * Characterizes a well-known datatype as a regular expression.
 *
 * @author Brian Remedios
 */
public class RegexDescriptor extends AbstractRegisteredItem {

	public final String regex;
	private final Pattern pattern;
	public final String example;

	public static final RegexDescriptor IPAddress 	 = new RegexDescriptor("ipAddress", "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", "127.0.0.1", null);
	public static final RegexDescriptor Hostname 	 = new RegexDescriptor("hostName", "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$", "cnn.com", null);
	public static final RegexDescriptor EMailAddress = new RegexDescriptor("emailAddress", "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", "brian@email.com", "simple email valiation only");
	public static final RegexDescriptor IPV4		 = new RegexDescriptor("IPV4", "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", "10.0.0.5", null);
//	public static final RegexDescriptor IPV6		 = new RegexDescriptor("IPV6", "\\A(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}\\Z", "2001:db8:a0b:12f0", null);
//	public static final RegexDescriptor URL			 = new RegexDescriptor("url", "/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$/", "https://www.cnn.com/index", null);

	public static final Map<String, RegexDescriptor> ALL = asMap(
		IPAddress, Hostname, EMailAddress, IPV4
//		, URL, IPV6
		);

	public static Map<String, RegexDescriptor> asMap(RegexDescriptor ...theDescriptors) {

		Map<String, RegexDescriptor> map = new HashMap<String, RegexDescriptor>();
		for (RegexDescriptor rd : theDescriptors) map.put(rd.id(), rd);
		return Collections.unmodifiableMap(map);
	}

	private RegexDescriptor(String theId, String theRegex, String theExample, String theDescription) {
		super(theId, theDescription);

		regex = theRegex;
		pattern = Pattern.compile(theRegex);
		example = theExample;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!super.equals(other)) return false;
		if (!(other instanceof RegexDescriptor)) return false;
		
		RegexDescriptor otherRD = RegexDescriptor.class.cast(other);
		return Objects.equal(regex,  otherRD.regex) &&
				Objects.equal(pattern,  otherRD.pattern) &&
				Objects.equal(example,  otherRD.example);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(regex, pattern, example);
	}

	public boolean matches(String text) {
		return pattern.matcher(text).matches();
	}

//	public static void main(String[] args) {
//
//		String id = args[0];
//		String text = args[1];
//
//		for (RegexDescriptor rd : ALL.values()) {
//			if (id.equals(rd.id())) {
//				boolean matched = rd.matches(text);
//				if (matched) {
//					System.out.println("pattern " + id + " matches " + text) ;
//					} else {
//						System.out.println("pattern " + id + " does not match " + text) ;
//					}
//			}
//		}
//	}

}
