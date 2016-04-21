package com.pipeclamp.metrics.collectors;

import java.util.Collection;

import com.google.common.base.Predicate;

/**
 *
 * @author bremed200
 *
 * @param <I>
 */
public class CounterCollector<I extends Object> extends AbstractCollector<I> {

	public CounterCollector(Predicate<I> aPredicate) {
		super(aPredicate);
	}

	@Override
	public Collection<I> all() { return null; }
}
