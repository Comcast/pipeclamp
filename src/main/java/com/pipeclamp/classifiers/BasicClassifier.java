package com.pipeclamp.classifiers;

import com.google.common.base.Objects;
import com.pipeclamp.api.Classifier;

/**
 * 
 * @author Brian Remedios
 *
 * @param <I>
 */
public class BasicClassifier<I extends Object> implements Classifier<I> {

	private final String id;
	private final String description;
	
	public BasicClassifier(String theId, String theDescription) {
		id = theId;
		description = theDescription;
	}

	@Override
	public String description() { return description; }

	@Override
	public String id() { return id; }

	@Override
	public String classify(I item) {
		throw new RuntimeException("Classify method not implemented");
	}

	@Override
	public int hashCode() { return Objects.hashCode(id, description); }
	
	@Override
	public boolean equals(Object other) {
		
		if (other == null) return false;
		if (other == this) return true;
		
		if (!(other instanceof BasicClassifier)) return false;
		
		BasicClassifier<?> bc = BasicClassifier.class.cast(other);
		
		return Objects.equal(id, bc.id) && 
				Objects.equal(description, bc.description);
	}
	
	@Override
	public String toString() {
		return id + "\t" + description;
	}
}
