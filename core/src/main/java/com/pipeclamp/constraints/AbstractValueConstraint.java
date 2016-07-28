package com.pipeclamp.constraints;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.params.BooleanParameter;

/**
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public abstract class AbstractValueConstraint<V extends Object> extends AbstractConstraint implements ValueConstraint<V>{

//	protected static String[] arrayValueIn(Map<String, String> values, StringArrayParameter sap) {
//		if (values.containsKey(sap.id())) {
//			String value = values.remove(sap.id());
//			return sap.valueIn(value, null);
//		}
//		return null;
//	}
//
//	protected static String[] arrayValueIn(Map<String, String> values, String key) {
//		if (values.containsKey(key)) {
//			String value = values.remove(key);
//			return value.split(" ");
//		}
//		return null;
//	}

	public static final String MissingValueMessage = "Missing value";
	
	public static final BooleanParameter ALLOW_NULLS = new BooleanParameter("allowNull", "(Optional)  Override any null value derivation and enforce null checking");
	
	protected AbstractValueConstraint(String theId, boolean nullAllowed) {
		super(theId, nullAllowed);
	}

	protected abstract V cast(Object value);

	@Override
	public Violation errorFor(Object value) {

		if (value == null) {
			return nullsAllowed ?
				null :
				new Violation(this, MissingValueMessage);
		}

		V typed;

		try {
			typed = value == null ? null : cast(value);
			return typedErrorFor(typed);
			} catch (ClassCastException cce) {
				return new Violation(this, cce.getMessage());
			}
	}
}
