package com.pipeclamp.constraints.string;

import com.pipeclamp.constraints.AbstractValueConstraint;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractStringConstraint extends AbstractValueConstraint<String> {

	protected AbstractStringConstraint(String theId, boolean nullAllowed) {
		super(theId, nullAllowed);
	}

	@Override
	public String cast(Object value) {
		
		return value instanceof String ?
			(String)value :
			value.toString();	// handles Avro UTF8 string types
	}

}