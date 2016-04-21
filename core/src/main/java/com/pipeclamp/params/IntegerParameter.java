package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 *
 */
public class IntegerParameter extends AbstractParameter<Integer> {

	public IntegerParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Integer valueIn(String text, Type type) {
		return Integer.parseInt(text);
	}

}
