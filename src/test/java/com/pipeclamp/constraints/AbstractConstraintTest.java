package com.pipeclamp.constraints;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.pipeclamp.api.Parameter;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractConstraintTest {


	private static final char NameValueDelimiter = '=';

	protected static byte[] readResource(String filename) {
		
		URL url = AbstractConstraintTest.class.getClassLoader().getResource(filename);
		File file;
		try {
			file = new File(url.toURI());
		} catch(URISyntaxException e) {
			file = new File(url.getPath());
		}

		byte[] bFile = new byte[(int) file.length()];
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			input.read(bFile);
			return bFile;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			closeQuietly(input);
		}
	}

	private static void closeQuietly(Closeable c) {

		if (c == null) return;
		try {
			c.close();
		} catch (IOException ioe) {
			// ignore
		}
	}

	protected static Map<String,String> asParams(String... keyValuePairs) {

		Map<String,String> paramsByKey = new HashMap<String,String>(keyValuePairs.length);

		for (String kvp : keyValuePairs) {
			addTo(paramsByKey, kvp);
		}
		return paramsByKey;
	}

	protected static Map<String,String> asParams(Parameter<?> param, Object value) {

		Map<String,String> paramsByKey = new HashMap<String,String>(1);
		paramsByKey.put(param.id(), String.valueOf(value));
		return paramsByKey;
	}
	
	protected static Map<String,String> asParams(Parameter<?> param1, Object value1, Parameter<?> param2, Object value2) {

		Map<String,String> paramsByKey = new HashMap<String,String>(2);
		paramsByKey.put(param1.id(), String.valueOf(value1));
		paramsByKey.put(param2.id(), String.valueOf(value2));
		return paramsByKey;
	}
	
	protected static Map<String,String> asParams(Parameter<?> param1, Object value1, Parameter<?> param2, Object value2, Parameter<?> param3, Object value3) {

		Map<String,String> paramsByKey = new HashMap<String,String>(3);
		paramsByKey.put(param1.id(), String.valueOf(value1));
		paramsByKey.put(param2.id(), String.valueOf(value2));
		paramsByKey.put(param3.id(), String.valueOf(value3));
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