package com.pipeclamp.api;

import java.io.PrintStream;
import java.util.Map;

import com.pipeclamp.path.Path;

/**
 * Specifies a utility that performs computations on values collected from a series of records.
 * For a given record type D, a number of paths to specific internal values can be registered 
 * along with a computation function that operates on all collected values.
 *
 * @author Brian Remedios
 */
public interface Aggregator<D extends Object> extends Receiver<D> {

	String StartTimeKey = "__startTime";
	String EndTimeKey = "__endTime";
	String TotalRecordsKey = "__totalRecords";

	/**
	 * Registers a new value extraction path along with a function that computes and operation over
	 * them, a unique identifier, and an optional unit label.
	 *
	 * @param path
	 * @param operation
	 * @param id
	 * @param unitLabel
	 */
	void register(Path<D, ?> path, Function<? extends Object, ? extends Object> operation, String id, String unitLabel);

	/**
	 * Remove all current registrations
	 */
	void unregisterAll();

	/**
	 * Causes all registered functions to scan their collected values and returns the results in a 
	 * map keyed by the ids.  Note that the accumulated values are NOT cleared after computation. 
	 * This allows you to collect ongoing metrics over the whole dataset (memory permitting).
	 *
	 * @param debugOut
	 *
	 * @return Map<String, Object>
	 */
	Map<String, Object> compute(PrintStream debugOut);
}
