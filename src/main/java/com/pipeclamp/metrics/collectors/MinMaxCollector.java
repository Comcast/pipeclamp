package com.pipeclamp.metrics.collectors;

import java.util.Collection;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class MinMaxCollector<I extends Comparable<?>> extends AbstractCollector<I>{

	private I min;
	private I max;

	public static final String MinKey = "min";
	public static final String MaxKey = "max";

	public MinMaxCollector(Predicate<I> aPredicate) {
		super(aPredicate);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean add(I item) {

		if (!super.add(item)) return false;

		if (min == null) {
			min = item;
			max = item;
			return true;
		}

		if (((Comparable)min).compareTo(item) > 0) min = item;
		if (((Comparable)max).compareTo(item) < 0) max = item;
		return true;
	}

	@Override
	public Collection<I> all() { return null; }

	@Override
	public I get(String identifier) {
		if (MinKey.equals(identifier)) return min;
		if (MaxKey.equals(identifier)) return max;
		return null;
	}

	@Override
	public void clear() {
		super.clear();

		min = null;
		max = null;
	}
}