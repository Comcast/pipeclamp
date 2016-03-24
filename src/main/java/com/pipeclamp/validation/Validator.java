package com.pipeclamp.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.generic.GenericRecord;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.util.StringUtil;

/**
 * A validator used to evaluate Avro records, specifically the implementers
 * of GenericRecord. It is quick and thread-safe as long as the accompanying
 * constraints are.
 *
 * @author Brian Remedios
 *
 * @deprecated
 */
@Deprecated
public class Validator {

	private final Map<String[], Collection<ValueConstraint<?>>> constraintsByPath;

	public static final String ArraySuffix = "[]";

	private static Object valueFor(GenericRecord record, String key) {

		if (key.endsWith(ArraySuffix)) {
			return record.get(key.substring(0,  key.length()-2));
			}
		return record.get(key);
	}

	private static Object valueFor(GenericRecord record, String[] path) {

		Object value = valueFor(record, path[0]);
		if (path.length == 1) return value;

		GenericRecord child = (GenericRecord)value;
		for (int i=1; i<path.length; i++) {
			Object temp = valueFor(child, path[i]);
			if (temp instanceof GenericRecord)
				child = (GenericRecord)temp;
			else
				return temp;	// can't dive any deeper regardless
		}

		return child;
	}

	private static Collection<Violation> violationsFor(Collection<ValueConstraint<?>> constraints, Object value) {

		Collection<Violation> violations = new ArrayList<Violation>(constraints.size());

		for (ValueConstraint<?> constraint : constraints) {
			Violation violation = constraint.errorFor(value);
			if (violation != null) violations.add(violation);
			}
		return violations;
	}

	private static String[] withIndex(String[] path, int index) {

		String[] newArray = new String[path.length+1];
		System.arraycopy(path, 0, newArray, 0, path.length);
		newArray[path.length] = "[" + index + "]";
		return newArray;
	}

	private static boolean denotesCollection(String[] path) {

		for (String part : path) {
			if (part.endsWith(ArraySuffix)) return true;
		}
		return false;
	}

	/**
	 * Use this if you don't have an enhanced schema. You'll just have to register
	 * your constraints manually.
	 */
	public Validator() {
		constraintsByPath = new HashMap<String[], Collection<ValueConstraint<?>>>();
	}

	/**
	 * Walks the schema provided to extract the constraint parameters so that
	 * constraints can be created and registered.
	 *
	 * @param schema
	 * @param flagUnknowns
	 */
//	public Validator(Schema schema, boolean flagUnknowns) {
//		this(ConstraintUtil.constraintsIn(schema, flagUnknowns));
//	}

	/**
	 * Alternate entry point if the constraints are coming from an non-schema source
	 * like an XML file.
	 *
	 * @param theConstraintsByPath
	 * @param theMultivalues
	 */
	public Validator(Map<String[], Collection<ValueConstraint<?>>> theConstraintsByPath) {
		constraintsByPath = theConstraintsByPath;
	}

	/**
	 * Register a new constraint against the specified path.
	 *
	 * @param constraint
	 * @param path
	 */
	public void register(ValueConstraint<?> constraint, String... path) {

		Collection<ValueConstraint<?>> constraints = constraintsByPath.get(path);
		if (constraints == null) {
			constraints = new ArrayList<ValueConstraint<?>>();
			constraintsByPath.put(path, constraints);
		}
		constraints.add(constraint);
	}

	/**
	 * Walks the path for every registered constraint to extract the value
	 * and evaluates them. Any violations are held and returned in a map
	 * that denotes the path.
	 *
	 * @param record
	 *
	 * @return Map<String[], Collection<Violation>>
	 */
	public Map<String[], Collection<Violation>> validate(GenericRecord record) {

		Map<String[], Collection<Violation>> issues = new HashMap<String[], Collection<Violation>>();

		for (Entry<String[], Collection<ValueConstraint<?>>> entry : constraintsByPath.entrySet()) {

			final String[] path = entry.getKey();

			if (denotesCollection(path)) {

				List<?> values = (List<?>) valueFor(record, path);

				for (int i=0; i<values.size(); i++) {
					Collection<Violation> violations = violationsFor(entry.getValue(), values.get(i));
					if (violations.isEmpty()) continue;
					issues.put(withIndex(path, i), violations);
					}
				} else {
					Object value = valueFor(record, path);
					Collection<Violation> violations = violationsFor(entry.getValue(), value);
					if (violations.isEmpty()) continue;
					issues.put(path, violations);
					}
		}

		checkMultivaluedConstraints(record, issues);

		return issues;
	}

	private void checkMultivaluedConstraints(GenericRecord record, Map<String[], Collection<Violation>> issues) {

//		for (Entry<String[], Collection<MultiValueConstraint>> entry : multivalueConstraints.entrySet()) {
//			for (MultiValueConstraint mvc : entry.getValue()) {
//				if (hasConflict(mvc, issues)) continue;
//			}

//			JsonNode node = nodeFor(record, entry.getKey());
//			if (node == null) continue;
//		}
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (Entry<String[], Collection<ValueConstraint<?>>> entry : constraintsByPath.entrySet()) {
			sb.append(
					StringUtil.render(entry.getKey(), "->")
					).append('\n');
			for (ValueConstraint<?> cnst : entry.getValue())
				sb.append('\t').append(cnst).append('\n');
		}
		return sb.toString();
	}
}
