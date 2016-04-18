package com.pipeclamp.constraints.number;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.NumberParameter;

/**
 * TODO provide a function operator to let this be more generic
 *
 * @author Brian Remedios
 */
public class MathConstraint extends AbstractNumericConstraint {

	private final Number factor;

	private static final String TypeTag = "math";

	static final NumberParameter MULTIPLE_OF = new NumberParameter("multipleOf", "multiple of the specified divisor");
	
	public static final ConstraintBuilder<Number> Builder = new BasicConstraintBuilder<Number>(TypeTag, MathConstraint.class, MULTIPLE_OF) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Number factor = numberValueIn(values, MULTIPLE_OF, type);

			if (factor == null) return null;

			return Arrays.<ValueConstraint<?>>asList(new MathConstraint("", nullsAllowed, factor));
		}
	};

	public MathConstraint(String theId, boolean nullAllowed, Number theFactor) {
		super(theId, nullAllowed);

		factor = theFactor;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Violation typedErrorFor(Number value) {

		if (value == null) return null;

		if (isInteger(value)) {
			if (value.longValue() % factor.longValue() != 0) return new Violation(this, value + " is not a multiple of " + factor);
		}

		return null;
	}

	@Override
	public String toString() {
		return "MathConstraint  factor: " + factor;
	}
}
