package com.pipeclamp.constraints.timestamp;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.params.TimestampParameter;

/**
 *
 * @author Brian Remedios
 */
public class TimestampRangeConstraint extends AbstractValueConstraint<Long> {

	private final Long minTimestamp;
	private final Long maxTimestamp;

	public static final String TypeTag = "timestampRange";

	static final TimestampParameter MIN_TIMESTAMP = new TimestampParameter("minTime", "earliest timestamp (UTC)", "yyyy-MM-dd HH:mm:ss");
	static final TimestampParameter MAX_TIMESTAMP = new TimestampParameter("maxTime", "latest timestamp (UTC)", "yyyy-MM-dd HH:mm:ss");

	public static final ConstraintBuilder<Long> Builder = new ConstraintBuilder<Long>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Long minValue = timestampValueIn(values, MIN_TIMESTAMP);
			Long maxValue = timestampValueIn(values, MAX_TIMESTAMP);

			if (minValue != null || maxValue != null) {
				return Arrays.<ValueConstraint<?>>asList(
						new TimestampRangeConstraint("", nullsAllowed, minValue, maxValue)
						);
			}

			return null;
		}

		public Parameter<?>[] parameters() { return new Parameter[] { MIN_TIMESTAMP, MAX_TIMESTAMP };	}
	};

	public TimestampRangeConstraint(String theId, boolean nullAllowed, String theMin, String theMax) {
		this(theId, nullAllowed, MIN_TIMESTAMP.valueIn(theMin, null), MAX_TIMESTAMP.valueIn(theMax, null));
	}

	public TimestampRangeConstraint(String theId, boolean nullAllowed, Long theMin, Long theMax) {
		super(theId, nullAllowed);

		minTimestamp = theMin;
		maxTimestamp = theMax;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>();
		params.put(MIN_TIMESTAMP, minTimestamp);
		params.put(MAX_TIMESTAMP, maxTimestamp);
		return params;
	}

	@Override
	public Violation typedErrorFor(Long value) {

		if (minTimestamp != null && minTimestamp > value) {
			return new Violation(this, MIN_TIMESTAMP.asString(value) + " is too early, earliest is " + MIN_TIMESTAMP.asString(minTimestamp));
		}
		if (maxTimestamp != null && maxTimestamp < value) {
			return new Violation(this, MAX_TIMESTAMP.asString(value) + " is too late, latest is " + MAX_TIMESTAMP.asString(maxTimestamp));
		}

		return null;
	}

	@Override
	protected Long cast(Object value) {
		return (Long)value;
	}

}
