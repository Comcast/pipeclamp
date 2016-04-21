package com.pipeclamp.classifiers;

import com.pipeclamp.api.Classifier;

/**
 * 
 * @author Brian Remedios
 */
public interface Numbers {

	Classifier<Number> OddEven = new BasicClassifier<Number>("oddEven", "Converts all number types to longs, returns 'even' or 'odd' based on the value") {

		@Override
		public String classify(Number item) {
			return com.pipeclamp.predicates.Numbers.IsOdd.apply(item) ? "odd" : "even";
		}
	};
}
