package com.pipeclamp.metrics.functions;

import com.google.common.base.Predicate;
import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.collectors.CounterCollector;
/**
 * Counts the number of qualified items received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class Counter<I extends Object> extends AbstractCollectionFunction<I,Integer> {

    public Counter(Predicate<I> predicate) {
    	super(predicate);
    }

	@Override
	public Integer compute(Collector<I> collector) {
		return collector.collected();
	}

	@Override
	public Collector<I> createCollector() { return new CounterCollector<I>(predicate()); }
}
