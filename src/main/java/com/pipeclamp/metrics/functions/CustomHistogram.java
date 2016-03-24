package com.pipeclamp.metrics.functions;

import java.util.HashMap;
import java.util.Map;

import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.collectors.HistogramCollector;

/**
 * Counts the number of unique items received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class CustomHistogram<I extends Object> extends AbstractCollectionFunction<I, Map<I, Integer>> {

	private Classifier classifier;
	
	public static final String Id = "customHistogram";

	public CustomHistogram() {
		super(null);
	}

	@Override
	public Collector<I> createCollector() {	
		return new HistogramCollector<I>(predicate()); 
	}

	@Override
	public Map<I,Integer> compute(Collector<I> collector) {

		Map<I, Integer> values = new HashMap<>();
		for (I value : collector.all()) {
			values.put(value, collector.instancesOf(value));
		}
		return values;
	}

}
