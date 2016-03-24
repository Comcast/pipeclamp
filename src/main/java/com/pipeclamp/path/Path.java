package com.pipeclamp.path;

import java.util.Collection;


/**
 * Provides access to single or multiple values from a source context.
 *
 * @author Brian Remedios
 *
 * @param <T>
 * @param <V>
 */
public interface Path<T extends Object, V extends Object> {

	/**
	 *
	 * @return int
	 */
	int depth();

	/**
	 *
	 * @return boolean
	 */
	boolean hasLoop();

	/**
	 * 
	 * @return boolean
	 */
	boolean denotesCollection();
	
	/**
	 *
	 * @param source
	 *
	 * @return V
	 */
	V valueVia(T source);

	/**
	 *
	 * @param source
	 *
	 * @return Collection<V>
	 */
	Collection<V> valuesVia(T source);

	/**
	 *
	 * @param segment
	 * @param index
	 *
	 * @return Path<T,V>
	 */
	Path<T,V> withIndex(int segment, String index);
}
