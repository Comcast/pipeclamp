package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public class StringArrayParameter extends AbstractParameter<String[]> {

	private final String regexSplitter;	// could be just " "

	public StringArrayParameter(String theId, String theDescription, String theRegexSplitter) {
		super(theId, theDescription);

		regexSplitter = theRegexSplitter;
	}

	@Override
	public String[] valueIn(String text, Type type) {
		return text == null ? null : text.split(regexSplitter);
	}

}
