package com.pipeclamp.constraints.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.StringArrayParameter;

/**
 * Enforces one or more restrictions on the contents of a collection.
 *
 * @author Brian Remedios
 */
public class CollectionContentConstraint extends AbstractCollectionConstraint {

	private final CollectionRestriction restriction;
	private final Set<String> options;

	public static final String TypeTag = "content";

	public static final String Docs = "Enforces one or more restrictions on the contents of a collection";

	public static final CollectionRestrictionParameter Function = new CollectionRestrictionParameter("function", "to do");	// TODO
	public static final StringArrayParameter CHOICES = new StringArrayParameter("choices", "to do", " ");	// TODO

	public static final ConstraintBuilder<Object[]> Builder = new BasicConstraintBuilder<Object[]>(TypeTag, CollectionContentConstraint.class, Docs, Function, CHOICES) {

		public Collection<Constraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			CollectionRestriction restriction = Function.valueIn(values.remove(Function.id()), null);
			if (restriction == null) return null;
			String[] opts = CHOICES.valueIn(values.remove(CHOICES.id()), null);

			Set<String> optSet = opts == null ? null : new HashSet<String>(Arrays.asList(opts));
			
			return Arrays.<Constraint<?>>asList(
					new CollectionContentConstraint("", nullsAllowed, restriction, optSet)
					);
		}
	};

	public CollectionContentConstraint(String theId, boolean nullAllowed, CollectionRestriction theRestriction, Set<String> theOptions) {
		super(theId, nullAllowed);

		restriction = theRestriction;
		options = theOptions;
	}

	@Override
	public String typeTag() { return TypeTag; }

	private Violation checkRequired(Object[] values) {
		Set<Object> valueSet = new HashSet<Object>(Arrays.asList(values));
		for (String option : options) {
			if (!valueSet.contains(option)) {
				return new Violation(this, "missing: " + option);
			}
		}
		return null;
	}

	private Violation checkAnyOf(Object[] values) {

		for (Object value : values) {
			if (options.contains(value)) return null;
		}

		return new Violation(this, "no options found");
	}

	private Violation checkOneOf(Object[] values) {

		int matches = 0;
		for (Object value : values) {
			if (options.contains(value)) matches++;
		}

		switch (matches) {
			case 0 : return new Violation(this, "no option found");
			case 1 : return null;
			default: return new Violation(this, "multiple options found");
			}
	}

	private Violation checkAllUnique(Object[] values) {

		Set<Object> set = new HashSet<Object>(Arrays.asList(values));
		if (set.size() == values.length) return null;

		// TODO better error reporting (show dupes)
		return new Violation(this, "has duplicates");
	}

	private Violation checkNoneOf(Object[] values) {

		for (Object value : values) {
			if (options.contains(value)) {
				return new Violation(this, "contains: " + value);
			}
		}
		return null;
	}

	@Override
	public Violation typedErrorFor(Object[] values) {

		switch (restriction) {
			case Required:	return checkRequired(values);
			case AnyOf :	return checkAnyOf(values);
			case OneOf :	return checkOneOf(values);
			case AllUnique:	return checkAllUnique(values);
			case NoneOf:	return checkNoneOf(values);
				   default: return null;
			}
	}
	
	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		params.put(Function, restriction);
		if (options != null) params.put(CHOICES, options);
		return params;
	}

	@Override
	public String toString() {
		return "CollectionContentConstraint restriction: " + restriction + " options: " + options;

	}
}
