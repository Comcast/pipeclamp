package com.pipeclamp.constraints.number;

import com.pipeclamp.constraints.AbstractValueConstraint;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractNumericConstraint extends AbstractValueConstraint<Number> {

	public static boolean isInteger(Number num) {
		return num instanceof Integer || num instanceof Long || num instanceof Short;
	}

	protected AbstractNumericConstraint(String theId, boolean nullAllowed) {
		super(theId, nullAllowed);
	}

	@Override
	public Number cast(Object value) {
		return (Number)value;
	}

}