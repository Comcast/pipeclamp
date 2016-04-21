package com.pipeclamp.constraints.string;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractEnumerationParameter;

/**
 *
 * @author Brian Remedios
 */
public class WordRestrictionParameter extends AbstractEnumerationParameter<WordRestriction> {

	public WordRestrictionParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public WordRestriction valueIn(String text, Type type) {

		return WordRestriction.forKeyword(text);
	}

	@Override
	public WordRestriction[] values() { return WordRestriction.values(); }

}
