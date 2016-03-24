package com.pipeclamp.constraints.bytes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.params.ByteArrayParameter;

/**
 *
 * @author Brian Remedios
 */
public class BytePrefixConstraint extends AbstractValueConstraint<byte[]> {

	private final byte[][] prefixes;

	private static final String TypeTag = "prefixes";

	private static final ByteArrayParameter PREFIXES = new ByteArrayParameter("arrays", "legal prefixes", " ");

	public static final ConstraintBuilder<byte[]> Builder = new ConstraintBuilder<byte[]>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			byte[][] prefixes = byteArrayValuesIn(values, PREFIXES);

			if (prefixes == null) return null;

			return Arrays.<ValueConstraint<?>>asList(
					new BytePrefixConstraint("", nullsAllowed, prefixes)
					);
		}

		public Parameter<?>[] parameters() { return new Parameter[] { PREFIXES };	}
	};

	protected BytePrefixConstraint(String theId, boolean nullAllowed, byte[][] thePrefixes) {
		super(theId, nullAllowed);

		prefixes = thePrefixes;
	}

	@Override
	public byte[] cast(Object value) {
		return (byte[])value;
	}

	@Override
	public Violation typedErrorFor(byte[] values) {

		// TODO
		return null;
	}

	@Override
	public String typeTag() { return TypeTag; }

}