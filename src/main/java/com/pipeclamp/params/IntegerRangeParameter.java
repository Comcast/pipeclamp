package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public class IntegerRangeParameter extends AbstractParameter<Integer[]> {

	private final String regexSplitter;	// could be just " "

	public IntegerRangeParameter(String theId, String theDescription, String theRegexSplitter) {
		super(theId, theDescription);

		regexSplitter = theRegexSplitter;
	}

	@Override
	public Integer[] valueIn(String text, Type type) {

		if (text == null || text.length() == 0) return null;

		String[] parts = text.split(regexSplitter);
		if (parts.length != 2) return null;

		return new Integer[] {
				Integer.parseInt(parts[0]),
				Integer.parseInt(parts[1])
				};
	}

}
