package com.pipeclamp.metrics.aggregator;

import java.util.Map;

/**
 * Specifies how or when the an aggregator will terminate an accumulation session and compute the results.
 * The callback is invoked when
 *
 * @author Brian Remedios
 */
public class AggregationPolicy<T extends Object> {

	private final Integer maxItems;
	private final Integer periodSeconds;
	private final boolean clearAfter;

	interface Callback {
		void results(Map<String, Object> values);
	}

	/**
	 *
	 * @param theMaxItems
	 * @param aPeriodSeconds
	 * @param clearFlag
	 */
	public AggregationPolicy(Integer theMaxItems, Integer aPeriodSeconds, boolean clearFlag) {
		maxItems = theMaxItems;
		periodSeconds = aPeriodSeconds;
		clearAfter = clearFlag;
	}

//	public void accumulate(Aggregator<T> agg, T record) {
//
//		int accumulated = agg.accept(record);
//
//		if (maxItems != null && maxItems <= accumulated) {
//			Map<String, Object> results = agg.compute(null);
//			callback.results(results);
//
//			if (clearAfter) agg.clear();
//		}
//	}
}
