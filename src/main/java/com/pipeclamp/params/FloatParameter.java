package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public class FloatParameter extends AbstractParameter<Float> {

	public FloatParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Float valueIn(String text, Type type) {
		return Float.parseFloat(text);
	}

}
