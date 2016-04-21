package com.pipeclamp.constraints;

import static org.junit.Assert.assertEquals;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractConstraintTest {

	public static byte[] readResource(String filename) {
		
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

	protected AbstractConstraintTest() {
		super();
	}
	
	protected abstract ConstraintBuilder<?> sampleBuilder();
	
	protected abstract ValueConstraint<?> sampleConstraint();
	
//	@Test
//	public void testParameters() {
//		
//		ConstraintBuilder<?> builder = sampleBuilder();
//		Parameter<?>[] params = builder.parameters();
//		
//		ValueConstraint<?> constraint = sampleConstraint();
//		
//		if (params.length != constraint.parameters().size()) {
//			System.out.println("oops");
//		}
//	}
}