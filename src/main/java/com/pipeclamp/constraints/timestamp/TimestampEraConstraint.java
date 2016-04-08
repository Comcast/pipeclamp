package com.pipeclamp.constraints.timestamp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.params.StringParameter;
import com.pipeclamp.util.TimeUtil;

/**
 *
 * @author Brian Remedios
 */
public class TimestampEraConstraint extends AbstractValueConstraint<Long> {

	private final TimestampRestriction restriction;

	public static final String TypeTag = "timestampEra";

	public static final StringParameter Era = new StringParameter("era", "era");

	public static final ConstraintBuilder<Long> Builder = new ConstraintBuilder<Long>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			String restrictionId = values.remove(Era.id());
			TimestampRestriction tr = TimestampRestriction.fromKeyword(restrictionId);
			if (tr != null)
					return Arrays.<ValueConstraint<?>>asList(
							new TimestampEraConstraint("", nullsAllowed, tr)
							);

			return null;
		}

		@Override
		public Parameter<?>[] parameters() { return new Parameter<?>[] { Era  }; }
	};

	public TimestampEraConstraint(String theId, boolean nullAllowed, TimestampRestriction theRestriction) {
		super(theId, nullAllowed);

		restriction = theRestriction;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Violation typedErrorFor(Long value) {

		switch (restriction) {
			case Future 	: return checkFuture(value);
			case Historical : return checkHistorical(value);
			}
		return null;
	}

	private Violation checkHistorical(long timestamp) {
		if (TimeUtil.isPast(timestamp)) return null;
		return new Violation(this, "non-historical timestamp");
	}

	private Violation checkFuture(long timestamp) {
		if (TimeUtil.isFuture(timestamp)) return null;
		return new Violation(this, "non-future timestamp");
	}

	@Override
	protected Long cast(Object value) {
		return (Long)value;
	}

}
