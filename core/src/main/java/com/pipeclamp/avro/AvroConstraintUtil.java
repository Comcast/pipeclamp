package com.pipeclamp.avro;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.path.Path;

import static com.pipeclamp.avro.AvroUtil.nullableSingleTypeIn;
import static com.pipeclamp.avro.AvroUtil.isNullableSingleType;

/**
 *
 * @author Brian Remedios
 */
public class AvroConstraintUtil extends QAUtil {

	public static final String TypeKey = "_type";
	public static final String AllowNullKey = "allowNull";

	private static final String ConstraintKey = "constraints";
	private static final String ConstraintFunctionKey = "function";

	private AvroConstraintUtil() {
		super();
	}

	public static void add(Schema schema, ValueConstraint<?> constraint) {

		ArrayNode cstArrayNode = getOrCreateArrayNode(schema, ConstraintKey);

		// TODO remove any pre-existing constraint that matches the new one

		ObjectNode cstNode = JsonNodeFactory.instance.objectNode();
		cstNode.put(ConstraintFunctionKey, constraint.typeTag());

		cstArrayNode.add(cstNode);

		Map<Parameter<?>, Object> params = constraint.parameters();
		if (params.isEmpty()) return;

		addParameters(cstNode, params);
	}

	public static boolean hasLocalConstraints(Schema schema) {
		return schema.getJsonProp(ConstraintKey) != null;
	}

	public static Collection<Constraint<?>> localConstraintsIn(Schema schema, ConstraintFactory<Schema.Type> factory) {
		return allConstraintsIn(schema.getJsonProp(ConstraintKey), schema, factory);
	}

	public static boolean hasLocalConstraints(Field field) {
		return field.getJsonProp(ConstraintKey) != null;
	}

	public static Collection<Constraint<?>> localConstraintsIn(Field field, ConstraintFactory<Schema.Type> factory) {
		return allConstraintsIn(field.getJsonProp(ConstraintKey), field.schema(), factory);
	}

	/**
	 *
	 * @param issuesByPath
	 * @param out
	 */
	public static void showIssues(Map<Path<GenericRecord,?>, Collection<Violation>> issuesByPath, PrintStream out) {

		for (Entry<Path<GenericRecord,?>, Collection<Violation>> entry : issuesByPath.entrySet()) {
			out.println(entry.getKey());
			for (Violation v : entry.getValue()) {
				out.println("\t" + v);
			}
		}
	}

	/**
	 * Walk the schema tree to collect the parameters and then convert these to actual constraints.
	 *
	 * @param schema
	 * @param flagUnknowns
	 *
	 * @return Map<String[], Collection<Constraint<?>>>
	 */
	public static Map<Path<GenericRecord,?>, Collection<Constraint<?>>> constraintsIn(Schema schema, boolean flagUnknowns, ConstraintFactory<Schema.Type> factory) {

		return constraintParamsIn(schema, factory);
	}

	private static Map<Path<GenericRecord,?>, Collection<Constraint<?>>> constraintParamsIn(Schema schema, ConstraintFactory<Schema.Type> factory) {

		Map<Path<GenericRecord,?>, Collection<Constraint<?>>> constraintsByPath = new HashMap<>();

		Stack<String> path = new Stack<>();

		collectConstraints(path, schema, constraintsByPath, factory);

		return constraintsByPath;
	}

	private static Collection<Constraint<?>> allConstraintsIn(JsonNode cstNode, Schema schema, ConstraintFactory<Schema.Type> factory) {

		if (Type.UNION == schema.getType() && schema.getTypes().size() > 2) throw new RuntimeException("Cannot currently handle more than two major datatypes in a UNION");

		Collection<Constraint<?>> constraints = new ArrayList<>(cstNode.size() + 2);
		Type type = AvroUtil.primaryTypeIn(schema);
		boolean allowsNulls = AvroUtil.allowsNull(schema);

		Iterator<JsonNode> iter = cstNode.getElements();
		while (iter.hasNext()) {
			Collection<Constraint<?>> csts = constraintsIn(iter.next(), type, allowsNulls, factory);
			if (csts != null) constraints.addAll(csts);
		}
		return constraints;
	}

	/**
	 * Returns whether nulls are permitted per the datatype definition (i.e. Long -> yes, long -> no)
	 * or if yes, whether the arguments has an explicit allowance for it.
	 * 
	 * @param typeCanBeNull
	 * @param arguments
	 * 
	 * @return boolean
	 */
	private static boolean nullsPermitted(boolean typeCanBeNull, Map<String, String> arguments) {
		
		String explicitNullAllowance = arguments.get(AllowNullKey);
		if (explicitNullAllowance == null) return typeCanBeNull;
		
		return Boolean.valueOf(explicitNullAllowance);
	}
	
	private static Collection<Constraint<?>> constraintsIn(JsonNode node, Type type, boolean allowsNulls, ConstraintFactory<Schema.Type> factory) {

		if (!node.has(ConstraintFunctionKey)) return null;

		String cstId = node.get(ConstraintFunctionKey).asText();

		ConstraintBuilder<?> cb = factory.builderFor(type, cstId);
		if (cb == null) {	// TODO
			return null;
		}

		Map<String, String> args = argumentsOn(node);

		boolean valueCanBeNull = nullsPermitted(allowsNulls, args);
		
		addDocsTo(args, node);	// include any doc as a value under a reserved key
		
		return cb.constraintsFrom(type, valueCanBeNull, args);
	}

	private static void addDocsTo(Map<String, String> args, JsonNode node) {
		
		if (!node.has(DocKey)) return;

		String docs = node.get(DocKey).asText();
		args.put(BasicConstraintBuilder.LocalDocKey, docs);
	}
	
	/**
	 * Recursive descent through the tree via the field list to collect the constraint parameters.
	 *
	 * @param path
	 * @param schema
	 * @param constraintsByPath
	 * @param factory
	 */
	private static void collectConstraints(Stack<String> path, Schema schema, Map<Path<GenericRecord,?>, Collection<Constraint<?>>> constraintsByPath, ConstraintFactory<Schema.Type> factory) {

		JsonNode cstNode = schema.getJsonProp(ConstraintKey);
		if (cstNode != null) {
			Collection<Constraint<?>> constraints = allConstraintsIn(cstNode, schema, factory);
			if (constraints != null && constraints.size() > 0) {
				SimpleAvroPath<?> avroPath = new SimpleAvroPath(path);
				constraintsByPath.put(avroPath, constraints);
				} else {
					System.err.println("unable to interpret constraints at " + cstNode);
				}
		}

		if (Type.RECORD != schema.getType()) return;

		List<Field> fields = schema.getFields();

		for (Field fld : fields) {
			cstNode = fld.getJsonProp(ConstraintKey);
			if (cstNode != null) {
				path.push(fld.name());
				SimpleAvroPath<?> avroPath = new SimpleAvroPath(path);
				constraintsByPath.put(
					avroPath, allConstraintsIn(cstNode, fld.schema(), factory)
					);
				path.pop();
			}
			Schema childSchema = fld.schema();
			switch (childSchema.getType()) {
				case RECORD : {
					path.push(fld.name());
					collectConstraints(path, childSchema, constraintsByPath, factory);
					path.pop();
					break;
					}
				case ARRAY :  {
					path.push(fld.name() + "[]");
					collectConstraints(path, childSchema, constraintsByPath, factory);
					path.pop();
					break;
				}
				case UNION : {
					path.push(fld.name());
					List<Schema> kidTypes = childSchema.getTypes();
					if (isNullableSingleType(kidTypes)) {
						Schema realType = nullableSingleTypeIn(kidTypes);
						collectConstraints(path, realType, constraintsByPath, factory);
					}
					path.pop();
					break;
				}
			}
		}
	}
}
