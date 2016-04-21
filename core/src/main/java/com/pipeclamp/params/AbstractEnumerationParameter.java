package com.pipeclamp.params;

import com.pipeclamp.api.RegisteredItem;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractEnumerationParameter<T extends RegisteredItem> extends AbstractParameter<T> {

	protected AbstractEnumerationParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	public abstract T[] values();

}