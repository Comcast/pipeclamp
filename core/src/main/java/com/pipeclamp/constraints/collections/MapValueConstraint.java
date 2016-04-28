package com.pipeclamp.constraints.collections;

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
public class MapValueConstraint extends AbstractMapConstraint {

	public static final String TypeTag = "mapValues";
	
	public static final String Docs = "Applies a specified constraint against all values held by a map.";

	public static final StringParameter VALUE_TYPE = new StringParameter("valueType", "map value type");

	public static  ConstraintBuilder<Object[]> builderWith(final ConstraintFactory<?> cstFactory) {
		
		return new BasicConstraintBuilder<Object[]>(TypeTag, MapValueConstraint.class, Docs, VALUE_TYPE, CONSTRAINT_ID) {
	
			public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {
				
				ConstraintBuilder<?> cb = builderFor(cstFactory, values, VALUE_TYPE);
				if (cb == null) return null;
				
				Collection<Constraint<?>> constraints = cb.constraintsFrom(null, nullsAllowed, values);
				if (constraints == null || constraints.isEmpty()) return null;
			
				return withExtras(new MapValueConstraint("", nullsAllowed, (ValueConstraint<?>)constraints.iterator().next()), values);
			}
		};
	};
	
	public MapValueConstraint(String theId, boolean nullAllowed, ValueConstraint<?> theConstraint) {
		super(theId, nullAllowed, theConstraint);
	}

	@Override
	protected Collection<?> valuesIn(Map<?,?> source) { return source.values(); };

	@Override
	public String typeTag() { return TypeTag; }
}
