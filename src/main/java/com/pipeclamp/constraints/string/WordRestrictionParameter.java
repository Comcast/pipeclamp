package com.pipeclamp.constraints.string;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractParameter;

/**
 * 
 * @author Brian Remedios
 */
public class WordRestrictionParameter extends AbstractParameter<WordRestriction> {

	public WordRestrictionParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public WordRestriction valueIn(String text, Type type) {

		return WordRestriction.forKeyword(text);
	}

}
