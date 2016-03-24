package com.pipeclamp.avro;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.codehaus.jackson.JsonNode;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.MetricDescriptor;
import com.pipeclamp.metrics.MetricFactory;
import com.pipeclamp.path.Path;
import com.pipeclamp.path.SimpleAvroPath;

/**
 *
 * @author Brian Remedios
 */
public class AvroMetricUtil extends QAUtil {

	private static final String MetricsKey = "metrics";
	private static final String MetricFunctionKey = "function";

	private static final MetricFactory Factory = MetricFactory.Instance;

	private AvroMetricUtil() {
		super();
	}

	/**
	 * Walk the schema tree to collect the parameters and then convert these to actual metric functions.
	 *
	 * @param schema
	 *
	 * @return Map<Path<GenericRecord,?>, Map<String, MetricDescriptor>>
	 */
	public static Map<Path<GenericRecord,?>, Map<String, MetricDescriptor>> metricsIn(Schema schema) {

		Map<Path<GenericRecord,?>, Map<String, MetricDescriptor>> metricsByPath = new HashMap<>();

		Stack<String> path = new Stack<String>();

		collectMetrics(path, schema, metricsByPath);

		return metricsByPath;
	}

	private static Map<String, MetricDescriptor> allMetricsIn(JsonNode cstNode, Schema schema) {

		if (Type.UNION == schema.getType() && schema.getTypes().size() > 2) throw new RuntimeException("Cannot currently handle more than two major datatypes in a UNION");

		Map<String, MetricDescriptor> metrics = new HashMap<>(cstNode.size() + 2);
		Type type = AvroUtil.primaryTypeIn(schema);
		boolean allowsNulls = AvroUtil.allowsNull(schema);

		Iterator<JsonNode> iter = cstNode.getElements();
		while (iter.hasNext()) {
			Map<String, MetricDescriptor> csts = metricsIn(iter.next(), type, allowsNulls);
			if (csts != null) metrics.putAll(csts);
		}
		return metrics;
	}

	private static String childTextFrom(JsonNode node, String childId) {
		JsonNode kid = node.get(childId);
		return kid == null ? null : kid.asText();
	}
	
	private static Map<String, MetricDescriptor> metricsIn(JsonNode node, Type type, boolean allowsNulls) {

		if (!node.has(MetricFunctionKey)) return null;

		if (!node.has(IdKey)) {
			System.err.println("Missing id for " + node);
			return null;
		}

		String metricFn = node.get(MetricFunctionKey).asText();
		String metricId = node.get(IdKey).asText();
		String desc = childTextFrom(node, DocKey);

		Class<?> javaType = AvroConfiguration.javaTypeFor(type);
		if (javaType == null) return null;

		Function<?,?> fn = Factory.functionFor(metricFn, javaType);
		if (fn == null) {	// TODO
			return null;
		}

		return ImmutableMap.<String, MetricDescriptor>builder().put(
				metricId, new MetricDescriptor(metricId, "", fn, "", desc)
				)
				.build();

//		Map<String, String> args = argumentsOn(node);
//
//		return mb.metricsFrom(type, allowsNulls, args);
	}

	public static boolean hasLocalMetrics(Schema schema) {
		return schema.getJsonProp(MetricsKey) != null;
	}

	public static Map<String, MetricDescriptor> localMetricsIn(Schema schema) {
		return allMetricsIn(schema.getJsonProp(MetricsKey), schema);
	}

	public static boolean hasLocalMetrics(Field field) {
		return field.getJsonProp(MetricsKey) != null;
	}

	public static Map<String, MetricDescriptor> localMetricsIn(Field field) {
		return allMetricsIn(field.getJsonProp(MetricsKey), field.schema());
	}

	/**
	 * Recursive descent through the tree via the field list to collect the metric parameters.
	 *
	 * @param path
	 * @param fields
	 * @param metricsByPath
	 */
	private static void collectMetrics(Stack<String> path, Schema schema, Map<Path<GenericRecord,?>, Map<String, MetricDescriptor>> metricsByPath) {

		JsonNode cstNode = schema.getJsonProp(MetricsKey);
		if (cstNode != null) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			SimpleAvroPath<?> avroPath = new SimpleAvroPath(path);
			metricsByPath.put(avroPath, allMetricsIn(cstNode, schema));
		}

		if (Type.RECORD != schema.getType()) return;

		List<Field> fields = schema.getFields();

		for (Field fld : fields) {
			cstNode = fld.getJsonProp(MetricsKey);
			if (cstNode != null) {
				path.push(fld.name());
				SimpleAvroPath<?> avroPath = new SimpleAvroPath(path);
				metricsByPath.put(
					avroPath, allMetricsIn(cstNode, fld.schema())
					);
				path.pop();
			}
			Schema childSchema = fld.schema();
			switch (childSchema.getType()) {
				case RECORD : {
					path.push(fld.name());
					collectMetrics(path, childSchema, metricsByPath);
					path.pop();
					break;
					}
				case ARRAY :  {
					path.push(fld.name() + "[]");
					collectMetrics(path, childSchema, metricsByPath);
					path.pop();
					break;
				}
				case UNION : {
					// TODO
				}
			}
		}
	}
}
