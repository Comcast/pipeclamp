package com.pipeclamp.constraints;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;
import org.apache.commons.lang.StringUtils;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;

/**
 * 
 * @author Brian Remedios
 *
 * @param <V>
 */
public class BasicConstraintBuilder<V extends Object> implements ConstraintBuilder<V> {

	public final String id;
	public final Class<?> executionType;
	public final Parameter<?>[] parameters;
	public final String documentation;
	
	public static final String LocalDocKey = "__docs";
	
	public static Collection<Constraint<?>> withExtras(Constraint<?> constraint, Map<String, String> values) {
		
		String desc = values.get(LocalDocKey);
		if (StringUtils.isNotEmpty(desc)) {
			constraint.description(desc);
		}
		
		return Arrays.<Constraint<?>>asList(constraint);
	}
	
	private static Parameter<?>[] paramsWith(Parameter<?>[] params, Parameter<?> newParam) {
		
		Parameter<?>[] plusOne = new Parameter<?>[params.length+1];
		System.arraycopy(params, 0, plusOne, 0, params.length);
		plusOne[params.length] = newParam;
		return plusOne;
	}
	
	/**
	 * 
	 * @param theId
	 * @param theExecutionType
	 * @param theParams
	 */
	public BasicConstraintBuilder(String theId, Class<?> theExecutionType, String theDocs, Parameter<?>... theParams) {
		id = theId;
		executionType = theExecutionType;
		documentation = theDocs;
		parameters = paramsWith(theParams, AbstractValueConstraint.ALLOW_NULLS);
	}

	@Override
	public String id() { return id; }

	@Override
	public String docs() { return documentation; }

	@Override
	public Parameter<?>[] parameters() { return parameters; }

	/**
	 * You are expected to implement this method.
	 */
	@Override
	public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {
		throw new RuntimeException("Method not implemented");
	}

	@Override
	public Class<?> executionType() { return executionType; }
}
