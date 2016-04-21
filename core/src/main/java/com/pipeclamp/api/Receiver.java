package com.pipeclamp.api;

/**
 *
 * @author Brian Remedios
 *
 * @param <D>
 */
public interface Receiver<D extends Object> {

	/**
	 * Consumes the record to extract and accumulate one or more internal values.
	 * Returns the number of records processed so far.
	 *
	 * @param record
	 *
	 * @return int
	 */
	int accept(D record);

	/**
	 * Returns the number of accumulated values.
	 *
	 * @return int
	 */
	int count();

	/**
	 * Clears out any accumulated values we might have collected.
	 */
	void clear();

	/**
	 *
	 * @param expression
	 * @param jobType
	 *
	 */
	void schedule(int seconds, int start, Runnable runnable);

	/**
	 *
	 */
	void cancelScheduler();
}