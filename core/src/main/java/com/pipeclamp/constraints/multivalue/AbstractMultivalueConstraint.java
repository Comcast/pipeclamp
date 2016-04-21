package com.pipeclamp.constraints.multivalue;

import com.pipeclamp.constraints.AbstractConstraint;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractMultivalueConstraint<T extends Object> extends AbstractConstraint implements MultiValueConstraint<T> {

	protected AbstractMultivalueConstraint(String theId, boolean allowsNulls) {
		super(theId, allowsNulls);
	}
}