package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 *
 */
public class StringParameter extends AbstractParameter<String> {

	public StringParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public String valueIn(String text, Type type) {
		return text;
	}

}
