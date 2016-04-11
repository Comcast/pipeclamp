package com.pipeclamp.metrics.collectors;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.base.Predicate;
import com.pipeclamp.api.Collector;

/**
 *
 * @author bremed200
 *
 * @param <I>
 */
public abstract class AbstractCollector<I extends Object> implements Collector<I> {

	private int collected;
	private final Predicate<I> predicate;

	/**
	 * Return a fixed set of items wrapped in a collector.
	 *
	 * @param items
	 *
	 * @return Collector<I>
	 */
	public static <I extends Object> Collector<I> asFinalCollector(final Collection<I> items) {

		return new Collector<I>() {
			public boolean add(I item) { return false; }
			public Collection<I> all() { return items; }
			public I get(String identifier) { return null;	}
			public int collected() { return items.size(); }
			public int instancesOf(I item) { return -1; }
			public void clear() { }
			public Set<String> classifications() { return Collections.emptySet(); }
			public int countsOf(String classification) { return 0; }
		};
	}

	protected AbstractCollector(Predicate<I> aPredicate) {
		predicate = aPredicate;
	}

	protected boolean qualifies(I item) {
		return predicate == null || predicate.apply(item);
	}

	@Override
	public boolean add(I item) {

		if (qualifies(item)) {
			collected++;
			return true;
		}
		return false;
	}

	@Override
	public I get(String identifier) { return null; }

	@Override
	public int instancesOf(I item) { return -1; }

	@Override
	public int collected() { return collected; }

	@Override
	public void clear() { collected = 0; }

	@Override
	public Set<String> classifications() {	return Collections.emptySet(); }

	@Override
	public int countsOf(String classification) { return 0; }
}
