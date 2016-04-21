package com.pipeclamp.avro;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.multivalue.MultiValueConstraint;
import com.pipeclamp.path.Path;
import com.pipeclamp.path.SimpleAvroPath;
import com.pipeclamp.validation.AbstractValidator;

/**
 * A validator used to evaluate Avro records, specifically the implementers
 * of GenericRecord. It is quick and thread-safe as long as the accompanying
 * constraints are.
 *
 * @author Brian Remedios
 */
public class AvroValidator extends AbstractValidator<GenericRecord> {

	private static boolean hasConflict(MultiValueConstraint<GenericRecord> mvc, Map<Path<GenericRecord,?>, Collection<Violation>> issues) {

		for (Path<GenericRecord,?> path : mvc.valuePaths()) {
			if (issues.containsKey(path)) return true;
		}
		return false;
	}

	private static final ConstraintFactory<Schema.Type> Factory = AvroConfiguration.ConstraintFactory;

	/**
	 * Use this if you don't have an enhanced schema. You'll just have to register
	 * your constraints manually.
	 */
	public AvroValidator() {
		super();
	}

	/**
	 * Walks the schema provided to extract the constraint parameters so that
	 * constraints can be created and registered.
	 *
	 * @param schema
	 * @param flagUnknowns
	 */
	public AvroValidator(Schema schema, boolean flagUnknowns) {
		this(AvroConstraintUtil.constraintsIn(schema, flagUnknowns, Factory), new HashMap<Path<GenericRecord, ?>, Collection<MultiValueConstraint<GenericRecord>>>());
	}

	/**
	 * Alternate entry point if the constraints are coming from an non-schema source
	 * like an XML file.
	 *
	 * @param theConstraintsByPath
	 * @param theMultivalues
	 */
	public AvroValidator(Map<Path<GenericRecord, ?>, Collection<ValueConstraint<?>>> theConstraintsByPath, Map<Path<GenericRecord, ?>, Collection<MultiValueConstraint<GenericRecord>>> theMultivalues) {
		super(theConstraintsByPath, theMultivalues);
	}


	public void register(ValueConstraint<?> constraint, String... thePath) {
		register(constraint, new SimpleAvroPath(thePath));
	}

	/**
	 * Walks the path for every registered constraint to extract the value
	 * and evaluates them. Any violations are held and returned in a map
	 * that denotes the path.
	 *
	 * @param record
	 *
	 * @return Map<Path<GenericRecord, ?>, Collection<Violation>>
	 */
	@Override
	public Map<Path<GenericRecord, ?>, Collection<Violation>> validate(GenericRecord record) {

		Map<Path<GenericRecord, ?>, Collection<Violation>> issues = new HashMap<Path<GenericRecord, ?>, Collection<Violation>>();

		for (Entry<Path<GenericRecord, ?>, Collection<ValueConstraint<?>>> entry : valueConstraints()) {

			final Path<GenericRecord, ?> path = entry.getKey();

			if (path.hasLoop()) {

				List<?> values = (List<?>) path.valuesVia(record);

				if (path.denotesCollection()) {
					Collection<Violation> violations = violationsFor(entry.getValue(), values);
					if (violations.isEmpty()) continue;
					issues.put(path, violations);
					continue;
				}

				for (int i=0; i<values.size(); i++) {
					Collection<Violation> violations = violationsFor(entry.getValue(), values.get(i));
					if (violations.isEmpty()) continue;
					Path<GenericRecord, ?> errorPath = path.withIndex(path.depth()-1, Integer.toString(i));
					issues.put(errorPath, violations);
					}
				} else {
					Object value = path.valueVia(record);
					Collection<Violation> violations = violationsFor(entry.getValue(), value);
					if (violations.isEmpty()) continue;
					issues.put(path, violations);
					}
		}

		checkMultivaluedConstraints(record, issues);

		return issues;
	}

	private void checkMultivaluedConstraints(GenericRecord record, Map<Path<GenericRecord, ?>, Collection<Violation>> issues) {

		for (Entry<Path<GenericRecord, ?>, Collection<MultiValueConstraint<GenericRecord>>> entry : multiValueConstraints()) {
			for (MultiValueConstraint<GenericRecord> mvc : entry.getValue()) {
				if (hasConflict(mvc, issues)) continue;
			}

//			JsonNode node = nodeFor(record, entry.getKey());
//			if (node == null) continue;
		}
	}

}
