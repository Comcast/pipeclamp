package com.pipeclamp.constraints.bytes;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.SignatureMatcher;
import com.pipeclamp.params.AbstractParameter;

/**
 * 
 * @author Brian Remedios
 */
public class ByteMatcherParameter extends AbstractParameter<SignatureMatcher> {

	public ByteMatcherParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public SignatureMatcher valueIn(String text, Type type) {

		return Matchers.ByteArrayMatchersById.get(text);
	}

}
