package com.pipeclamp.constraints.number;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.BooleanParameter;
import com.pipeclamp.params.NumberParameter;
import com.pipeclamp.params.StringParameter;

/**
 * Provides min/max limit checking for numeric values.
 *
 * @author Brian Remedios
 */
public class NumericConstraint extends AbstractNumericConstraint {

	private final Number min;
	private final Boolean includeMin;	// TODO
	private final Number max;
	private final Boolean includeMax;	// TODO
//	private final String rangeId;
	
	public static final String Docs = "Provides min/max limit checking for numeric values";

	private static final String TypeTag = "range";

	public static final NumberParameter MIN_VALUE	= new NumberParameter("min", "minimum value");
	public static final BooleanParameter MIN_INCLUSIVE = new BooleanParameter("includeMin", "include the minimum value");
	public static final NumberParameter MAX_VALUE	= new NumberParameter("max", "maximum value");
	public static final BooleanParameter MAX_INCLUSIVE = new BooleanParameter("includeMax", "include the maximum value");
	public static final StringParameter RANGE_ID	= new StringParameter("rangeId", null);

	public static final ConstraintBuilder<Number> Builder = new BasicConstraintBuilder<Number>(TypeTag, NumericConstraint.class, Docs, MIN_VALUE, MIN_INCLUSIVE, MAX_VALUE, MAX_INCLUSIVE, RANGE_ID) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Number minValue = numberValueIn(values, MIN_VALUE, type);
			Boolean includeMin = booleanValueIn(values, MIN_INCLUSIVE);
			Number maxValue = numberValueIn(values, MAX_VALUE, type);
			Boolean includeMax = booleanValueIn(values, MAX_INCLUSIVE);
			
			if (minValue != null || maxValue != null) {
				return Arrays.<Constraint<?>>asList(
						new NumericConstraint("", nullsAllowed, minValue, includeMin, maxValue, includeMax)
						);
			}

			String rangeId = stringValueIn(values, RANGE_ID);
			if (rangeId != null) {
				NamedRange range = NamedRange.ALL.get(rangeId);
				if (range != null) {
					return Arrays.<Constraint<?>>asList(
							new NumericConstraint(range.id(), nullsAllowed, range.min, true, range.max, true)
							);
				} else {
					throw new IllegalArgumentException("Unrecognized range: " + rangeId);
				}
			}

			return null;
		}
	};

	public NumericConstraint(String theId, boolean nullAllowed, Number theMin, Boolean includeMinFlag, Number theMax, Boolean includeMaxFlag) {
		super(theId, nullAllowed);

		if (theMin != null && theMax != null && theMin.doubleValue() > theMax.doubleValue())
			throw new IllegalArgumentException("Minimum value limit cannot be greater than the maximum");

		min = theMin;
		includeMin = includeMinFlag;

		max = theMax;
		includeMax = includeMaxFlag;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		// TODO include the rangeId somehow

		Map<Parameter<?>, Object> params = new HashMap<>(5);
		if (min != null) params.put(MIN_VALUE, min);
		if (includeMin != null) params.put(MIN_INCLUSIVE, includeMin);
		if (max != null) params.put(MAX_VALUE, max);
		if (includeMax != null) params.put(MAX_INCLUSIVE, includeMax);
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
