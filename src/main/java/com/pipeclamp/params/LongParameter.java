package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 *
 */
public class LongParameter extends AbstractParameter<Long> {

	public LongParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Long valueIn(String text, Type type) {
		return Long.parseLong(text);
	}

}
