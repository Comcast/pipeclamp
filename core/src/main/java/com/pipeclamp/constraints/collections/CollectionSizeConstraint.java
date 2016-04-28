package com.pipeclamp.constraints.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.IntegerParameter;

/**
 * Restricts a collection to a minimum and/or maximum item count.
 *
 * @author Brian Remedios
 */
public class CollectionSizeConstraint extends AbstractCollectionConstraint {

	private final Integer minSize;
	private final Integer maxSize;

	public static final String TypeTag = "size";

	public static final String Docs = "Restricts a collection to a minimum and/or maximum item count";
	
	public static final IntegerParameter MIN_ITEMS = new IntegerParameter("minItems", "minimum number of items");
	public static final IntegerParameter MAX_ITEMS = new IntegerParameter("maxItems", "maximum number of items");

	public static final ConstraintBuilder<Object[]> Builder = new BasicConstraintBuilder<Object[]>(TypeTag, CollectionSizeConstraint.class, Docs, MIN_ITEMS, MAX_ITEMS) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Integer min = intValueIn(values, MIN_ITEMS);
			Integer max = intValueIn(values, MAX_ITEMS);

			if (min== null && max == null) return null;

			return withExtras(new CollectionSizeConstraint("", nullsAllowed, min, max), values);
		}
	};

	public CollectionSizeConstraint(String theId, boolean nullAllowed, Integer theMin, Integer theMax) {
		super(theId, nullAllowed);

		minSize = theMin;
		maxSize = theMax;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		if (minSize != null) params.put(MIN_ITEMS, minSize);
		if (maxSize != null) params.put(MAX_ITEMS, maxSize);
		return params;
	}

	@Override
	public Violation typedErrorFor(Object[] value) {

		int len = value == null ? 0 : value.length;

		if (minSize != null && minSize > len) return new Violation(this, "too few items, need at least " + minSize);
		if (maxSize != null && maxSize < len) return new Violation(this, "too many items, limit is " + maxSize);

		return null;
	}

	@Override
	public String toString() {
		return "ArraySizeConstraint  minSize: " + minSize + " maxSize: " + maxSize;
	}
}
