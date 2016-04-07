package com.pipeclamp.classifiers;

import com.pipeclamp.AbstractRegisteredItem;
import com.pipeclamp.api.Classifier;

/**
 * 
 * @author Brian Remedios
 *
 * @param <I>
 */
public class BasicClassifier<I extends Object> extends AbstractRegisteredItem implements Classifier<I> {

	public BasicClassifier(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public String classify(I item) {
		throw new RuntimeException("Classify method not implemented");
	}
	
	@Override
	public String toString() {
		return id + "\t" + description;
	}
}
