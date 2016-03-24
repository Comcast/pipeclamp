package com.pipeclamp.api;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public interface Parameter<T extends Object> {

	/**
	 * Expected to be unique among any of its siblings.
	 *
	 * @return String
	 */
	String id();

	/**
	 * Something anyone can reasonably make sense of.
	 *
	 * @return String
	 */
	String description();

	/**
	 *
	 * @param text
	 * @param type
	 *
	 * @return T
	 */
	T valueIn(String text, Type type);
}
