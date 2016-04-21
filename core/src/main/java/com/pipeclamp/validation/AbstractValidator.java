package com.pipeclamp.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.multivalue.MultiValueConstraint;
import com.pipeclamp.path.Path;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractValidator<T extends Object> {

	private final Map<Path<T, ?>, Collection<ValueConstraint<?>>> constraintsByPath;
	private final Map<Path<T, ?>, Collection<MultiValueConstraint<T>>> multivalueConstraints;

	protected static Collection<Violation> violationsFor(Collection<ValueConstraint<?>> constraints, Object value) {

		Collection<Violation> violations = new ArrayList<Violation>(constraints.size());

		for (ValueConstraint<?> constraint : constraints) {
			Violation violation = constraint.errorFor(value);
			if (violation != null) violations.add(violation);
			}
		return violations;
	}

	protected AbstractValidator() {

		constraintsByPath = new HashMap<Path<T, ?>, Collection<ValueConstraint<?>>>();
		multivalueConstraints = new HashMap<Path<T, ?>, Collection<MultiValueConstraint<T>>>();
	}

	protected AbstractValidator(Map<Path<T, ?>, Collection<ValueConstraint<?>>> theConstraintsByPath, Map<Path<T, ?>, Collection<MultiValueConstraint<T>>> theMultivalues) {
		constraintsByPath = theConstraintsByPath;
		multivalueConstraints = theMultivalues;
	}

	public abstract Map<Path<T, ?>, Collection<Violation>> validate(T record);

	protected Set<Entry<Path<T, ?>, Collection<ValueConstraint<?>>>> valueConstraints() {
		return constraintsByPath.entrySet();
	}

	protected Set<Entry<Path<T, ?>, Collection<MultiValueConstraint<T>>>> multiValueConstraints() {
		return multivalueConstraints.entrySet();
	}

	/**
	 * Register a new constraint against the specified path.
	 *
	 * @param constraint
	 * @param path
	 */
	public void register(ValueConstraint<?> constraint, Path<T, ?> path) {

		Collection<ValueConstraint<?>> constraints = constraintsByPath.get(path);
		if (constraints == null) {
			constraints = new ArrayList<ValueConstraint<?>>();
			constraintsByPath.put(path, constraints);
		}
		constraints.add(constraint);
	}

	/**
	 * Lists each path and then the constraint(s) associated with them.
	 *
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (Entry<Path<T, ?>, Collection<ValueConstraint<?>>> entry : constraintsByPath.entrySet()) {
			sb.append(entry.getKey()).append('\n');
			for (ValueConstraint<?> cnst : entry.getValue())
				sb.append('\t').append(cnst).append('\n');
		}
		return sb.toString();
	}
}