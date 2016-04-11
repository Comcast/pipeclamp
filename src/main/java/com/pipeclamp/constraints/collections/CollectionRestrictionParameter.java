package com.pipeclamp.constraints.collections;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractParameter;

/**
 *
 * @author Brian Remedios
 */
public class CollectionRestrictionParameter extends AbstractParameter<CollectionRestriction> {

	public CollectionRestrictionParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public CollectionRestriction valueIn(String text, Type type) {

		return CollectionRestriction.forKeyword(text);
	}

}
