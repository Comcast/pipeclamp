package com.pipeclamp.constraints.string;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.BooleanParameter;

/**
 * An efficient way to test for whitespace characters in the wrong places.
 *
 * @author Brian Remedios
 */
public class WhitespaceConstraint extends AbstractStringConstraint {

	private final boolean checkLeading;
	private final boolean checkTrailing;
	
	public static final String TypeTag = "whitespace";
	
	public static final BooleanParameter NO_LEADING = new BooleanParameter("noLead", "no starting whitespace characters");
	public static final BooleanParameter NO_TRAILING = new BooleanParameter("noTail", "no ending whitespace characters");

	public static final ConstraintBuilder<String> Builder = new BasicConstraintBuilder<String>(TypeTag, WhitespaceConstraint.class, NO_LEADING, NO_TRAILING) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Boolean noLead = booleanValueIn(values, NO_LEADING);
			if (noLead == Boolean.FALSE) noLead = null;
			Boolean noTrail = booleanValueIn(values, NO_TRAILING);
			if (noTrail == Boolean.FALSE) noTrail = null;
			
			if (noLead == null && noTrail == null) return null;

			return Arrays.<ValueConstraint<?>>asList(new WhitespaceConstraint("", nullsAllowed, noLead, noTrail));
		}
	};
	
	public WhitespaceConstraint(String theId, boolean nullAllowed, Boolean noLeadingFlag, Boolean noTrailingFlag) {
		super(theId, nullAllowed);

		checkLeading = Boolean.TRUE == noLeadingFlag;
		checkTrailing = Boolean.TRUE == noTrailingFlag;
	}

	@Override
	public Violation typedErrorFor(String value) {
		
		int len = value.length();
		if (len == 0) return null;
	
		if (checkLeading) {
			char ch = value.charAt(0);
			if (Character.isWhitespace(ch)) {
				return new Violation(this, "Illegal starting whitespace: " + ch);
			}
		}
		
		if (checkTrailing) {
			char ch = value.charAt(len-1);
			if (Character.isWhitespace(ch)) {
				return new Violation(this, "Illegal ending whitespace: " + ch);
			}
		}
	
		return null;
	}

	@Override
	public String typeTag() { return TypeTag; }
}
