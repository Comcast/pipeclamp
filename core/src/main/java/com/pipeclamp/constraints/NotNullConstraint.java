package com.pipeclamp.constraints;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;

/**
 * Catches fields that have no values.
 *
 * @author Brian Remedios
 */
public class NotNullConstraint extends AbstractValueConstraint<Object> {

	private static final String TypeTag = "notNull";
	
	private static final String Docs = "Catches fields that have no values";	// TODO expand
	
	public static final ConstraintBuilder<Object> Builder = new BasicConstraintBuilder<Object>(TypeTag, NotNullConstraint.class, Docs) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			return Arrays.<ValueConstraint<?>>asList( new NotNullConstraint("") );
		}
	};
	
	public NotNullConstraint(String theId) {
		super(theId, false);
	}

	@Override
	public Violation typedErrorFor(Object value) {
		
		return value == null ? new Violation(this, "value is null") : null;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	protected Object cast(Object value) { return value; }
}
