package com.pipeclamp.metrics.collectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.pipeclamp.api.Classifier;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class ClassifyingHistogramCollector<I extends Object> extends AbstractCollector<I> {

	private final Classifier<I> classifier;
	
	protected Map<String, Integer> countsPerClass = new HashMap<>();

	public ClassifyingHistogramCollector(Predicate<I> aPredicate, Classifier<I> theClassifier) {
		super(aPredicate);
		
		 classifier = theClassifier; 
	}

	@Override
	public boolean add(I item) {
		if (!super.add(item)) return false;

		String classification = classifier.classify(item);
		
		Integer count = countsPerClass.get(classification);
		if (count == null) count = Integer.valueOf(0);
		countsPerClass.put(classification, count+1);
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
		return 0;	// not recorded like that
	}

	@Override
	public Set<String> classifications() { return countsPerClass.keySet(); }
	
	@Override
	public int countsOf(String classification) {
		Integer count = countsPerClass.get(classification);
		return count == null ? 0 : count;
	}
	
	@Override
	public void clear() {
		super.clear();

		countsPerClass.clear();
	}

}
