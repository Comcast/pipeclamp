package com.pipeclamp.constraints.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.StringParameter;

/**
 * Applies a specified constraint against all values held by a map.
 *
 * @author Brian Remedios
 */
public class MapKeyConstraint extends AbstractMapConstraint {

	public static final String TypeTag = "mapKeys";
	
	public static final String Docs = "Applies a specified constraint against all keys held by a map.";

	public static final StringParameter KEY_TYPE = new StringParameter("keyType", "map key value type (lower case)");

	public static  ConstraintBuilder<Object[]> builderWith(final ConstraintFactory<?> cstFactory) {

		return new BasicConstraintBuilder<Object[]>(TypeTag, MapKeyConstraint.class, Docs, KEY_TYPE, CONSTRAINT_ID) {
	
			public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {
				
				ConstraintBuilder<?> cb = builderFor(cstFactory, values, KEY_TYPE);
				if (cb == null) return null;
				
				Collection<Constraint<?>> constraints = cb.constraintsFrom(null, nullsAllowed, values);
				if (constraints == null || constraints.isEmpty()) return null;
			
				return Arrays.<Constraint<?>>asList(new MapKeyConstraint("", nullsAllowed, (ValueConstraint<?>)constraints.iterator().next()));
			}
		};
	};
	
	public MapKeyConstraint(String theId, boolean nullAllowed, ValueConstraint<?> theConstraint) {
		super(theId, nullAllowed, theConstraint);
	}

	@Override
	protected Collection<?> valuesIn(Map<?,?> source) { return source.keySet(); };
	
	@Override
	public String typeTag() { return TypeTag; }
}
