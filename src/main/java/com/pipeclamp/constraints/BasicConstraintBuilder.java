package com.pipeclamp.constraints;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;

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
	
	/**
	 * 
	 * @param theId
	 * @param theExecutionType
	 * @param theParams
	 */
	public BasicConstraintBuilder(String theId, Class<?> theExecutionType, Parameter<?>... theParams) {
		id = theId;
		executionType = theExecutionType;
		parameters = theParams;
	}

	@Override
	public String id() { return id; }

	@Override
	public Parameter<?>[] parameters() { return parameters; }

	/**
	 * You are expected to implement this method.
	 */
	@Override
	public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {
		throw new RuntimeException("Method not implemented");
	}

	@Override
	public Class<?> executionType() { return executionType; }
}
