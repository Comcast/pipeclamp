package com.pipeclamp.metrics.collectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class HistogramCollector<I extends Object> extends AbstractCollector<I> {

	protected Map<I, Integer> countsPerItem = new HashMap<>();

	public HistogramCollector(Predicate<I> aPredicate) {
		super(aPredicate);
	}

	@Override
	public boolean add(I item) {
		if (!super.add(item)) return false;

		Integer count = countsPerItem.get(item);
		if (count == null) count = 0;
		countsPerItem.put(item, count+1);
		return true;
	}

	@Override
	public Collection<I> all() {
		return countsPerItem.keySet();
	}

	@Override
	public int instancesOf(I item) {
		Integer count = countsPerItem.get(item);
		return count == null ? 0 : count;
	}

	@Override
	public void clear() {
		super.clear();

		countsPerItem.clear();
	}

}
