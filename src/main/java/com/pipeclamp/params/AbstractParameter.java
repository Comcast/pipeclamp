package com.pipeclamp.params;

import com.pipeclamp.AbstractRegisteredItem;
import com.pipeclamp.api.Parameter;

/**
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public abstract class AbstractParameter<T extends Object> extends AbstractRegisteredItem implements Parameter<T> {

	protected AbstractParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public String toString() {

		return id + "\t" + description;
	}
}
