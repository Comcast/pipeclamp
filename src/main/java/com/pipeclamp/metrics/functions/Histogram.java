package com.pipeclamp.metrics.functions;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.collectors.HistogramCollector;

/**
 * Counts the number of unique items received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class Histogram<I extends Object> extends AbstractCollectionFunction<I, Map<I, Integer>> {

	public static final String Id = "histogram";

	public Histogram() {
		super(null);
	}

	@Override
	public Collector<I> createCollector() {	return new HistogramCollector<I>(predicate()); }

	@Override
	public Map<I,Integer> compute(Collector<I> collector) {

		Map<I, Integer> values = new HashMap<>();
		for (I value : collector.all()) {
			values.put(value, collector.instancesOf(value));
		}
		return values;
	}

	public static final Histogram<Long> LongHistogram = new Histogram<Long>();
	public static final Histogram<String> StringHistogram = new Histogram<String>();
	public static final Histogram<Integer> IntegerHistogram = new Histogram<Integer>();

	public static final Map<Class<?>, Function<?,?>> HistogramsByType = ImmutableMap.<Class<?>, Function<?,?>>builder().
		      put(Long.class, LongHistogram).
		      put(Integer.class, IntegerHistogram).
		      put(String.class, StringHistogram).
		      build();
}
