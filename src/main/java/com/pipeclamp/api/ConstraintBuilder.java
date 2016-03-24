package com.pipeclamp.api;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

/**
 * Returns one or more functional constraints derived from the values provided or null if none can be built.
 *
 * @author Brian Remedios
 */
public interface ConstraintBuilder<V extends Object> {

	/**
	 *
	 * @return String
	 */
	String id();

	/**
	 *
	 * @return Parameter<?>[]
	 */
	Parameter<?>[] parameters();

	/**
	 * Note that values used to build a constraint are removed from
	 * the value map to highlight any ones left unused.
	 *
	 * @param type
	 * @param nullsAllowed
	 * @param values
	 *
	 * @return Collection<ValueConstraint<?>> or null
	 */
	Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values);
}
