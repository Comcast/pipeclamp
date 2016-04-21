package com.pipeclamp.api;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 */
public interface Parameter<T extends Object> extends DescriptiveItem {

	/**
	 * Expected to be unique among any of its siblings.
	 *
	 * @return String
	 */
	String id();

	/**
	 *
	 * @param text
	 * @param type
	 *
	 * @return T
	 * 
	 * TODO  unwanted AVRO (Type) dependency breaks generic form
	 */
	T valueIn(String text, Type type);
	
	Parameter<?>[] EMPTY_ARRAY = new Parameter<?>[0];
}
