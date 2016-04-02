package com.pipeclamp.constraints.bytes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ByteSignatureMatcher;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.params.ByteArrayParameter;

/**
 * A simple match that looks for known byte prefixes on binary data.
 *
 * @author Brian Remedios
 */
public class BytePrefixConstraint extends AbstractValueConstraint<byte[]> {

	private final ByteSignatureMatcher[] matchers;

	private static final String TypeTag = "prefixes";

	private static final ByteArrayParameter PREFIXES = new ByteArrayParameter("arrays", "legal prefixes", " ");

	public static final ConstraintBuilder<byte[]> Builder = new ConstraintBuilder<byte[]>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			byte[][] prefixes = byteArrayValuesIn(values, PREFIXES);

			if (prefixes == null) return null;

			return Arrays.<ValueConstraint<?>>asList(
					new BytePrefixConstraint("", nullsAllowed, new SimplePrefixMatcher(prefixes))
					);
		}

		public Parameter<?>[] parameters() { return new Parameter[] { PREFIXES };	}
	};
	
	protected BytePrefixConstraint(String theId, boolean nullAllowed, ByteSignatureMatcher... theMatchers) {
		super(theId, nullAllowed);

		matchers = theMatchers;
	}

	@Override
	public byte[] cast(Object value) {
		return (byte[])value;
	}

	@Override
	public Violation typedErrorFor(byte[] values) {

		for (ByteSignatureMatcher matcher : matchers) {
			if (matcher.matches(values)) return null;
		}

		return new Violation(this, "Unable to match existing prefixes");
	}

	@Override
	public String typeTag() { return TypeTag; }
}