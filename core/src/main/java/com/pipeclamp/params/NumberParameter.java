package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public class NumberParameter extends AbstractParameter<Number> {

	public NumberParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Number valueIn(String text, Type type) {

		try {
			switch (type) {
				case INT : return Integer.parseInt(text);
				case LONG : return Long.parseLong(text);
				case FLOAT : return Float.parseFloat(text);
				case DOUBLE : return Double.parseDouble(text);
				default: return null;
				}
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
