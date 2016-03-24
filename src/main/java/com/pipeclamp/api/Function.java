package com.pipeclamp.api;

/**
 *
 * @author Brian Remedios
 */
public interface Function<I extends Object, O extends Object> extends ParametricItem {

	/**
	 *
	 * @param collector
	 *
	 * @return O
	 */
	O compute(Collector<I> collector);

	/**
	 * Creates and returns a space-efficient collector intended for the specific function.
	 *
	 * @return Collector<I>
	 */
	Collector<I> createCollector();
}
