package com.pipeclamp.api;

import java.util.Collection;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 *
 * TODO provide a space cost-type indicator like OlogN
 */
public interface Collector<I extends Object> {

	/**
	 * Adds the item to the receiver. Returns true if it was
	 * accepted or not (filtered by optional predicate)
	 *
	 * @param item
	 * @return boolean
	 */
	boolean add(I item);

	/**
	 * Returns the (context-specific) relevant items.
	 * (not necessarily all of them)
	 *
	 * @return Collection<I>
	 */
	Collection<I> all();

	/**
	 * Return the item specified by the identifier.
	 *
	 * @param identifier
	 * @return I
	 */
	I get(String identifier);

	/**
	 * Return the total number of accepted items.
	 *
	 * @return int
	 */
	int collected();

	/**
	 * Returns the total number of items that match the one
	 * specified.
	 *
	 * @param item
	 * @return int
	 */
	int instancesOf(I item);

	/**
	 * Removes any collected items and resets associated metrics.
	 */
	void clear();
}
