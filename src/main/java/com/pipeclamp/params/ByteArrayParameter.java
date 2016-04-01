package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

import com.pipeclamp.util.StringUtil;

/**
 *
 * @author Brian Remedios
 */
public class ByteArrayParameter extends AbstractParameter<byte[][]> {

	private final String regexSplitter;	// could be just " "

	public ByteArrayParameter(String theId, String theDescription, String theRegexSplitter) {
		super(theId, theDescription);

		regexSplitter = theRegexSplitter;
	}

	@Override
	public byte[][] valueIn(String text, Type type) {

		if (text == null) return null;

		String[] values = text.split(regexSplitter);
		if (values.length == 0) return null;

		byte[][] arrays = new byte[values.length][];

		for (int i=0; i<values.length; i++) {
			arrays[i] = StringUtil.asByteArray(values[i]);
			if (arrays[i] == null) return null;
		}

		return arrays;

	}

}
