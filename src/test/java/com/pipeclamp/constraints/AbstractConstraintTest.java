package com.pipeclamp.constraints;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractConstraintTest {


	private static final char NameValueDelimiter = '=';

	protected static Map<String,String> asParams(String... keyValuePairs) {

		Map<String,String> paramsByKey = new HashMap<String,String>(keyValuePairs.length);

		for (String kvp : keyValuePairs) {
			addTo(paramsByKey, kvp);
		}
		return paramsByKey;
	}


	/**
	 *
	 * @param target
	 * @param keyAndValue
	 */
	protected static void addTo(Map<String, String> target, String keyAndValue) {
		String[] parts = nameAndValueIn(keyAndValue);
		target.put(parts[0].trim(), parts.length == 2 ? parts[1].trim() : null);
	}

	protected static String[] nameAndValueIn(String parameter) {
		int pos = parameter.indexOf(NameValueDelimiter);
		if (pos < 0) return new String[] { parameter };

		return new String[] {
			parameter.substring(0, pos).trim(),
			parameter.substring(pos+1).trim()
		};
	}

	protected AbstractConstraintTest() {
		super();
	}
}