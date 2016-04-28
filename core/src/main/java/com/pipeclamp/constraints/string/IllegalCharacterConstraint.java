package com.pipeclamp.constraints.string;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.StringParameter;

/**
 * An efficient way to test for unwanted characters.
 *
 * @author Brian Remedios
 */
public class IllegalCharacterConstraint extends AbstractStringConstraint {

	private final boolean blacklist[];
	
	public static final String TypeTag = "illegalChars";

	public static final String Docs = "Tests strings for unwanted characters";

	public static final StringParameter BAD_CHARS = new StringParameter("badChars", "unwanted characters");

	public static final ConstraintBuilder<String> Builder = new BasicConstraintBuilder<String>(TypeTag, IllegalCharacterConstraint.class, Docs, BAD_CHARS) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			String baddies = stringValueIn(values, BAD_CHARS);

			if (baddies == null) return null;

			return withExtras(new IllegalCharacterConstraint("", nullsAllowed, baddies), values);
		}

	};
	
	private static boolean[] asBooleanMap(char[] theChars) {
		
		boolean[] map = new boolean[128];
		for (char ch : theChars) {
			map[(int) ch] = true;
		}
		return map;
	}
	
	public IllegalCharacterConstraint(String theId, boolean nullAllowed, char...  theChars) {
		super(theId, nullAllowed);

		blacklist = asBooleanMap(theChars);
	}
	
	public IllegalCharacterConstraint(String theId, boolean nullAllowed, String theIllegals) {
		this(theId, nullAllowed, theIllegals.toCharArray());
	}

	@Override
	public Violation typedErrorFor(String value) {
		
		int len = value.length();
		if (len == 0) return null;
		
		for (int i=0; i<len; i++) {
			char ch = value.charAt(i);
			if (isIllegal(ch)) {
				return new Violation(this, "Illegal character: " + ch);
			}
		}
		return null;
	}

	private boolean isIllegal(char ch) {
		return (ch < 128) && blacklist[(int)ch];
	}
	
	@Override
	public String typeTag() { return TypeTag; }
}
