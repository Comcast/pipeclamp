package com.pipeclamp.constraints.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;

/**
 * Enforces one or more restrictions on the contents of a collection.
 *
 * @author Brian Remedios
 */
public class CollectionContentConstraint extends AbstractCollectionConstraint {

	private final CollectionRestriction restriction;
	private final Set<String> options;

	public static final String TypeTag = "content";

	private static String[] arrayValueIn(Map<String, String> values, CollectionRestriction rest) {
		if (values.containsKey(rest.keyword)) {
			String value = values.remove(rest.keyword);
			if (value == null) return new String[0];
			return value.split(ArrayElementDelimiterRegex);
		}
		return null;
	}

	public static final ConstraintBuilder<Object[]> Builder = new ConstraintBuilder<Object[]>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Collection<ValueConstraint<?>> constraints = new ArrayList<ValueConstraint<?>>();

			for (CollectionRestriction rest : CollectionRestriction.values()) {
				String[] opts = arrayValueIn(values, rest);
				if (opts != null) {
					Set<String> optSet = new HashSet<String>(Arrays.asList(opts));
					constraints.add(
							new CollectionContentConstraint("", nullsAllowed, rest, optSet)
							);
					}
				}

			return constraints.isEmpty() ? null : constraints;
		}

		public Parameter<?>[] parameters() { return CollectionRestriction.asParameters(); }

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
			default:
				break;
			}

		return null;
	}

	@Override
	public String toString() {
		return "ArrayContentConstraint  options: " + options;
	}
}
