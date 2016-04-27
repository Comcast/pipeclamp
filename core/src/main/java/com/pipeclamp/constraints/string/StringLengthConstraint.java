package com.pipeclamp.constraints.string;

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
import com.pipeclamp.params.IntegerParameter;

/**
 * Evaluates strings to ensure that they're within either the min or max length limits or both.
 *
 * @author Brian Remedios
 */
public class StringLengthConstraint extends AbstractStringConstraint {

	private final Integer minLength;
	private final Integer maxLength;

	public static final String TypeTag = "length";

	public static final String Docs = "Ensures string values are within either the min or max length limits or both";

	public static final IntegerParameter MIN_LENGTH = new IntegerParameter("min", "minimum length");
	public static final IntegerParameter MAX_LENGTH = new IntegerParameter("max", "maximum length");

	public static final ConstraintBuilder<String> Builder = new BasicConstraintBuilder<String>(TypeTag, StringLengthConstraint.class, Docs, MIN_LENGTH, MAX_LENGTH) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Integer minLen = intValueIn(values, MIN_LENGTH);
			Integer maxLen = intValueIn(values, MAX_LENGTH);

			if (minLen == null && maxLen == null) return null;

			return Arrays.<Constraint<?>>asList(new StringLengthConstraint("", nullsAllowed, minLen, maxLen));
		}
	};

	public StringLengthConstraint(String theId, boolean nullAllowed, Integer theMin, Integer theMax) {
		super(theId, nullAllowed);

		if (theMin != null && theMax != null && theMin > theMax) throw new IllegalArgumentException("min value " + theMin + " exceeds max value " + theMax);

		minLength = theMin;
		maxLength = theMax;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		if (minLength != null) params.put(MIN_LENGTH, minLength);
		if (maxLength != null) params.put(MAX_LENGTH, maxLength);
		return params;
	}

	@Override
	public Violation typedErrorFor(String value) {

		int length = value == null ? 0 : value.length();

		if (minLength != null && minLength > length) {
			return new Violation(this, '\'' + value + "' is too short, must be at least " + minLength + " chars");
		}
		if (maxLength != null && maxLength < length) {
			return new Violation(this, '\'' + value + "' is too long, must no more than " + maxLength + " chars");
		}

		return null;
	}

	@Override
	public String toString() {
		return "StringLengthConstraint  min: " + minLength + " max: " + maxLength;
	}
}
