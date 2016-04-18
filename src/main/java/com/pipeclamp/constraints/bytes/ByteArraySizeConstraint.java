package com.pipeclamp.constraints.bytes;

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
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.IntegerParameter;

/**
 *
 * @author Brian Remedios
 */
public class ByteArraySizeConstraint extends AbstractValueConstraint<byte[]> {

	private final Integer minSize;
	private final Integer maxSize;

	private static final String TypeTag = "range";

	public static final IntegerParameter MIN_SIZE	= new IntegerParameter("min", "minimum size");
	public static final IntegerParameter MAX_SIZE	= new IntegerParameter("max", "maximum size");
//	public static final StringParameter RANGE_ID	= new StringParameter("rangeId", null);

	public static final ConstraintBuilder<byte[]> Builder = new BasicConstraintBuilder<byte[]>(TypeTag, ByteArraySizeConstraint.class, MIN_SIZE, MAX_SIZE) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Integer minValue = intValueIn(values, MIN_SIZE);
			Integer maxValue = intValueIn(values, MAX_SIZE);

			if (minValue != null || maxValue != null) {
				return Arrays.<ValueConstraint<?>>asList(
						new ByteArraySizeConstraint("", nullsAllowed, minValue, maxValue)
						);
			}

//			String rangeId = stringValueIn(values, RANGE_ID);
//			if (rangeId != null) {
//				NamedRange range = NamedRange.ALL.get(rangeId);
//				if (range != null) {
//					return Arrays.<ValueConstraint<?>>asList(
//							new ByteArrayConstraint(range.name, nullsAllowed, range.min, range.max)
//							);
//				} else {
//					throw new IllegalArgumentException("Unrecognized range: " + rangeId);
//				}
//			}

			return null;
		}
	};

	protected ByteArraySizeConstraint(String theId, boolean nullAllowed, Integer theMinSize, Integer theMaxSize) {
		super(theId, nullAllowed);

		minSize = theMinSize;
		maxSize = theMaxSize;
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		if (minSize != null) params.put(MIN_SIZE, minSize);
		if (maxSize != null) params.put(MAX_SIZE, maxSize);
		return params;
	}
	
	@Override
	public byte[] cast(Object value) {
		return (byte[])value;
	}

	@Override
	public Violation typedErrorFor(byte[] values) {

		if (minSize != null && minSize > values.length) return new Violation(this, " is too small, minimum size is " + minSize);
		if (maxSize != null && maxSize < values.length) return new Violation(this, " is too big, maximum size is " + maxSize);

		return null;
	}

	@Override
	public String typeTag() { return TypeTag; }

}