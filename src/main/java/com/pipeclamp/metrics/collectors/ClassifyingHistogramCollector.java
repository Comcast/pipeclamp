package com.pipeclamp.metrics.collectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;
import com.pipeclamp.metrics.functions.Classifier;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class ClassifyingHistogramCollector<I extends Object> extends AbstractCollector<I> {

	private Classifier classifier;
	
	protected Map<String, Integer> countsPerClass = new HashMap<>();

	public ClassifyingHistogramCollector(Predicate<I> aPredicate) {
		super(aPredicate);
	}

	public void classifier(Classifier theClassifier) { classifier = theClassifier; }
	
	@Override
	public boolean add(I item) {
		if (!super.add(item)) return false;

		String theClass = classifier.classify(item);
		
		Integer count = countsPerClass.get(item);
		if (count == null) count = Integer.valueOf(0);
		countsPerClass.put(theClass, count+1);
		return true;
	}

	@Override
	public Collection<I> all() {
		//return countsPerClass.keySet();
		// TODO
		return null;
	}

	@Override
	public int instancesOf(I item) {
		Integer count = countsPerClass.get(item);
		return count == null ? 0 : count;
	}

	@Override
	public void clear() {
		super.clear();

		countsPerClass.clear();
	}

}
