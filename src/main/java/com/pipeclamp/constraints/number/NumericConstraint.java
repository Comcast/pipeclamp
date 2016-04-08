package com.pipeclamp.constraints.number;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.params.NumberParameter;
import com.pipeclamp.params.StringParameter;

/**
 * Provides min/max limit checking for numeric values.
 *
 * @author Brian Remedios
 */
public class NumericConstraint extends AbstractNumericConstraint {

	private final Number min;
	private final boolean minExclusive;	// TODO
	private final Number max;
	private final boolean maxExclusive;	// TODO

	private static final String TypeTag = "range";

	public static final NumberParameter MIN_VALUE	= new NumberParameter("min", "minimum value");
	public static final NumberParameter MAX_VALUE	= new NumberParameter("max", "maximum value");
	public static final StringParameter RANGE_ID	= new StringParameter("rangeId", null);

	public static final ConstraintBuilder<Number> Builder = new ConstraintBuilder<Number>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Number minValue = numberValueIn(values, MIN_VALUE, type);
			Number maxValue = numberValueIn(values, MAX_VALUE, type);

			if (minValue != null || maxValue != null) {
				return Arrays.<ValueConstraint<?>>asList(
						new NumericConstraint("", nullsAllowed, minValue, true, maxValue, true)
						);
			}

			String rangeId = stringValueIn(values, RANGE_ID);
			if (rangeId != null) {
				NamedRange range = NamedRange.ALL.get(rangeId);
				if (range != null) {
					return Arrays.<ValueConstraint<?>>asList(
							new NumericConstraint(range.name, nullsAllowed, range.min, true, range.max, true)
							);
				} else {
					throw new IllegalArgumentException("Unrecognized range: " + rangeId);
				}
			}

			return null;
		}

		public Parameter<?>[] parameters() { return new Parameter[] { MIN_VALUE, MAX_VALUE, RANGE_ID };	}
	};

	public NumericConstraint(String theId, boolean nullAllowed, Number theMin, boolean includeMin, Number theMax, boolean includeMax) {
		super(theId, nullAllowed);

		if (theMin != null && theMax != null && theMin.doubleValue() > theMax.doubleValue())
			throw new IllegalArgumentException("Minimum value limit cannot be greater than the maximum");

		min = theMin;
		minExclusive = includeMin;

		max = theMax;
		maxExclusive = includeMax;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		// TODO include the rangeId somehow

		Map<Parameter<?>, Object> params = new HashMap<>(3);
		params.put(MIN_VALUE, min);
		params.put(MAX_VALUE, max);
		return params;
	}

	@Override
	public Violation typedErrorFor(Number value) {

		double realNumber = value.doubleValue();

		if (min != null && min.doubleValue() > realNumber) return new Violation(this, value + " is too small, minimum is " + min);
		if (max != null && max.doubleValue() < realNumber) return new Violation(this, value + " is too big, maximum is " + max);

		return null;
	}

	@Override
	public String toString() {
		return "NumberConstraint  min: " + min + " max: " + max;
	}
}
