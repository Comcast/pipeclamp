package com.pipeclamp.metrics.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.common.base.Predicate;
import com.pipeclamp.api.Function;
import com.pipeclamp.api.Parameter;

/**
 * Provides optional item filtering capability by supplied predicates.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public abstract class AbstractCollectionFunction<I,O extends Object> implements Function<I,O>, Cloneable {

	private Predicate<I> predicate;

	protected AbstractCollectionFunction(Predicate<I> aPred) {
		super();

		predicate = aPred;
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {
		return Collections.emptyMap();
	}

	protected boolean qualifies(I item) {
		return predicate == null || predicate.apply(item);
	}

	protected Predicate<I> predicate() { return predicate; }

	public int countFiltered(Collection<I> values) {

		if (predicate == null) return values.size();

		int count = 0;
		for (I value : values) {
			if (predicate.apply(value)) count++;
		}
		return count;
	}

	public Collection<I> filtered(Collection<I> values) {

		if (predicate == null) return values;

		Collection<I> filtered = new ArrayList<I>(values.size());

		for (I value : values) {
			if (predicate.apply(value)) filtered.add(value);
		}
		return filtered;
	}

	/**
	 * Incorporates the specified predicate to filter out candidate values.
	 * Replaces any pre-existing predicate.
	 *
	 * @param aPred
	 * @return
	 */
	public AbstractCollectionFunction<I, O> with(Predicate<I> aPred) {

		try {
			@SuppressWarnings("unchecked")
			AbstractCollectionFunction<I,O> copy = (AbstractCollectionFunction<I,O>) clone();
			copy.predicate = aPred;
			return copy;
		} catch (CloneNotSupportedException e) {
			return null;	// won't happen
		}
	}
}