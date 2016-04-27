package com.pipeclamp.metrics.collectors;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Predicate;

/**
 * Retains all items qualified by the predicate.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class FullCollector<I extends Object> extends AbstractCollector<I> {

	private Collection<I> items;

	public FullCollector(Predicate<I> aPredicate) {
		super(aPredicate);

		items = new ArrayList<>();
	}

	@Override
	public boolean add(I item) {

		if (super.add(item)) {
			items.add(item);
			return true;
		}
		return false;
	}

	@Override
	public Collection<I> all() { return items; }

	@Override
	public void clear() {
		super.clear();

		items.clear();
	}

}
