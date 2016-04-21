package com.pipeclamp.constraints.collections;

import java.util.List;

import com.pipeclamp.constraints.AbstractValueConstraint;
/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractCollectionConstraint extends AbstractValueConstraint<Object[]> {

	public static final String ArrayElementDelimiterRegex = "\\|";

	protected AbstractCollectionConstraint(String theId, boolean nullAllowed) {
		super(theId, nullAllowed);
	}

	@Override
	public Object[] cast(Object value) {
		return ((List<?>)value).toArray();
	}

}