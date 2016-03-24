package com.pipeclamp.params;

import com.pipeclamp.api.Parameter;

/**
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public abstract class AbstractParameter<T extends Object> implements Parameter<T> {

	public final String id;
	public final String description;

	protected AbstractParameter(String theId, String theDescription) {

		id = theId;
		description = theDescription;
	}

	@Override
	public String id() { return id; }

	@Override
	public String description() { return description; }

	@Override
	public String toString() {

		return id + "\t" + description;
	}
}
