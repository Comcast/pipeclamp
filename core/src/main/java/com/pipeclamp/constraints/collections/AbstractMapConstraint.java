package com.pipeclamp.constraints.collections;

import java.util.Collection;
import java.util.Map;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractValueConstraint;
import com.pipeclamp.params.StringParameter;

/**
 * 
 * @author Brian Remedios
 */
public abstract class AbstractMapConstraint extends AbstractValueConstraint<Map<?,?>> {

	private final ValueConstraint<?> constraint;

	public static final StringParameter CONSTRAINT_ID = new StringParameter("constraintId", "map (key or value) constraint to be applied");
	
	protected static ConstraintBuilder<?> builderFor(ConstraintFactory<?> cstFactory, Map<String, String> values, StringParameter param) {
		
		String valueType = stringValueIn(values, param);
		if (valueType == null) return null;
		
		String cstId = stringValueIn(values, CONSTRAINT_ID);
		if (cstId == null) return null;
		
		ConstraintBuilder<?> builder = cstFactory.builderFor(valueType, cstId);
		if (builder == null) {
			// TODO log it
			return null;
		}
		return builder;
	}
	
	protected AbstractMapConstraint(String theId, boolean nullAllowed, ValueConstraint<?> theConstraint) {
		super(theId, nullAllowed);
		
		constraint = theConstraint;
	}

	@Override
	public Map<?,?> cast(Object value) {
		return (Map<?,?>)value;
	}
	
	protected abstract Collection<?> valuesIn(Map<?,?> source);
	
	@Override
	public Violation typedErrorFor(Map<?,?> values) {
		
		Violation v;
		for (Object value : valuesIn(values)) {
			v = constraint.errorFor(value);
			if (v != null) return v;
		}
	
		return null;
	}
}