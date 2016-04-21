package com.pipeclamp.constraints.collections;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractEnumerationParameter;

/**
 *
 * @author Brian Remedios
 */
public class CollectionRestrictionParameter extends AbstractEnumerationParameter<CollectionRestriction> {

	public CollectionRestrictionParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public CollectionRestriction[] values() { return CollectionRestriction.values(); }

	@Override
	public CollectionRestriction valueIn(String text, Type type) {

		return CollectionRestriction.forKeyword(text);
	}

}
