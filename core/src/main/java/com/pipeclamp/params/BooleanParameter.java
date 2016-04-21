package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public class BooleanParameter extends AbstractParameter<Boolean> {

	public BooleanParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Boolean valueIn(String text, Type type) {
		return Boolean.valueOf(text);
	}

}
