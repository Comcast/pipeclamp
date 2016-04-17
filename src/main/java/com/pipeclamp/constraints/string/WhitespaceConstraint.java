package com.pipeclamp.constraints.string;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.params.BooleanParameter;

/**
 * An efficient way to test for whitespace characters in the wrong places.
 *
 * @author Brian Remedios
 */
public class WhitespaceConstraint extends AbstractStringConstraint {

	private final boolean checkLeading;
	private final boolean checkTailing;
	
	public static final String TypeTag = "whitespace";
	
	public static final BooleanParameter NO_LEADING = new BooleanParameter("noLead", "no starting whitespace characters");
	public static final BooleanParameter NO_TAILING = new BooleanParameter("noTail", "no ending whitespace characters");

	public static final ConstraintBuilder<String> Builder = new ConstraintBuilder<String>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Boolean noLead = booleanValueIn(values, NO_LEADING);
			if (noLead == Boolean.FALSE) noLead = null;
			Boolean noTail = booleanValueIn(values, NO_TAILING);
			if (noTail == Boolean.FALSE) noTail = null;
			
			if (noLead == null && noTail == null) return null;

			return Arrays.<ValueConstraint<?>>asList(new WhitespaceConstraint("", nullsAllowed, noLead, noTail));
		}

		@Override
		public Parameter<?>[] parameters() { return new Parameter<?>[] { NO_LEADING, NO_TAILING }; };
	};
	
	public WhitespaceConstraint(String theId, boolean nullAllowed, Boolean noLeadingFlag, Boolean noTailingFlag) {
		super(theId, nullAllowed);

		checkLeading = Boolean.TRUE == noLeadingFlag;
		checkTailing = Boolean.TRUE == noTailingFlag;
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
		
		if (checkTailing) {
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
