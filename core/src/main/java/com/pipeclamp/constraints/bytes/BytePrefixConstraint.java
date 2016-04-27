package com.pipeclamp.constraints.bytes;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.SignatureMatcher;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.constraints.BasicConstraintBuilder;

/**
 * A simple match that looks for known byte prefixes on binary data.
 *
 * @author Brian Remedios
 */
public class BytePrefixConstraint extends AbstractValueConstraint<byte[]> {

	private final SignatureMatcher[] matchers;

	private static final String TypeTag = "prefixes";

	public static final ByteMatcherParameter MATCHERS = new ByteMatcherParameter("matchers", "legal matcher");

	public static final String Docs = "A simple match that looks for known byte prefixes on binary data";
	
	private static SignatureMatcher[] matchersIn(Map<String, String> values, ByteMatcherParameter param) {

		String matcherId = values.remove(param.id());
		return new SignatureMatcher[] {
			param.valueIn(matcherId, null)
			};
		}

	public static final ConstraintBuilder<byte[]> Builder = new BasicConstraintBuilder<byte[]>(TypeTag, BytePrefixConstraint.class, Docs, MATCHERS) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			SignatureMatcher[] matchers = matchersIn(values, MATCHERS);

			if (matchers == null) return null;

			return Arrays.<Constraint<?>>asList(
					new BytePrefixConstraint("", nullsAllowed, matchers)
					);
		}
	};

	protected BytePrefixConstraint(String theId, boolean nullAllowed, SignatureMatcher... theMatchers) {
		super(theId, nullAllowed);

		matchers = theMatchers;
	}

	@Override
	public byte[] cast(Object value) {
		return (byte[])value;
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		params.put(MATCHERS, matchers);
		return params;
	}
	
	@Override
	public Violation typedErrorFor(byte[] values) {

		for (SignatureMatcher matcher : matchers) {
			if (matcher.matches(values)) return null;
		}

		return new Violation(this, "Unable to match existing prefixes");
	}

	@Override
	public String typeTag() { return TypeTag; }
}