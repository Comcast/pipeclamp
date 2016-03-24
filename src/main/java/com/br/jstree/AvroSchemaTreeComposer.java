package com.br.jstree;

import static com.pipeclamp.avro.AvroUtil.childIntNodeAsString;
import static com.pipeclamp.avro.AvroUtil.defaultFor;
import static com.pipeclamp.avro.AvroUtil.isNullableSingleType;
import static com.pipeclamp.avro.AvroUtil.nonNullSchemaIn;
import static com.pipeclamp.avro.AvroUtil.nullableSingleTypeIn;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.avro.AvroConfiguration;
import com.pipeclamp.avro.AvroConstraintUtil;
import com.pipeclamp.avro.AvroMetricUtil;
import com.pipeclamp.metrics.MetricDescriptor;

/**
 * Converts an AVRO schema into text that JsTree can render on the browser.
 *
 * @author Brian Remedios
 */
public class AvroSchemaTreeComposer extends AbstractJsTreeComposer {

	private String newIdFor(String parentId, String childId) {
	//	String result =  childId + "_" + parentId;
	//	String result = parentId + "." + childId;

	//	return result;
		return Integer.toString(rowCount++);
	}
	
	private int rowCount = 0;
	
	private final boolean showFullNames;
	private final boolean showConstraints;
	private final boolean showMetrics;

	private final Stack<Schema> containers = new Stack<>();
	
	protected static final String NullableTypeSuffix = "-OR-NULL";
	
	private static final ConstraintFactory<Schema.Type> Factory = AvroConfiguration.ConstraintFactory;
	
	protected static Type preferredTypeFor(Field fld) {
		
		Type type = fld.schema().getType();
		
		if (type == Type.UNION) {
			List<Schema> types = fld.schema().getTypes();
			if (isNullableSingleType(types)) {
				Schema sch = nonNullSchemaIn(types);
				return sch.getType();
			}
		}
		return type;
	}

	protected static String labelFor(Field fld) {
		
		Type type = preferredTypeFor(fld);
		
		return fld.name() + "   " + formatType(type.getName());
	}
	
	protected static String typeFor(Schema sc, boolean isNullable) {
		
		if (sc.getType() == Type.ARRAY) {
			return sc.getElementType().getName().toUpperCase();
		}
	
		return sc.getType().name() + (isNullable ? NullableTypeSuffix : "");
	}

	public AvroSchemaTreeComposer() {
		this(false, true, true);
	}

	public AvroSchemaTreeComposer(boolean fullNamesFlag, boolean showConstraintsFlag, boolean showMetricsFlag) {
		super(DefaultIndentText);
		
		showFullNames = fullNamesFlag;
		showConstraints = showConstraintsFlag;
		showMetrics = showMetricsFlag;
	}

	public String render(String avroSchema) {
		
		Schema schema = new Schema.Parser().parse(avroSchema);
		return render(schema);
	}
	
	public String render(Schema schema) {

		render(schema, "#", false);
		return "[" + bufferAt(1) + "\n]";	// skip leading comma
	}
	
	private boolean push(Schema childType) {
		
		if (containers.isEmpty()) {
			containers.push(childType);
			return true;
		}
		
		// test for recursion
		if (containers.contains(childType)) {
			return false;
			} else {
				containers.push(childType);
			}
		return true;
	}
	
	private void pop() { containers.pop(); }
	
	private String labelFor(Schema sc) {
		
		Type typ = (sc.getType() == Type.ARRAY) ?
				sc.getElementType().getType() : 
				sc.getType();
		
		String typeText = formatType(typ.getName());
		String name = showFullNames ? sc.getFullName() : sc.getName();
		
		boolean isNullable = sc.getType() == Type.UNION && isNullableSingleType(sc.getTypes());
		
		return isNullable ?
			"<i>" + name + "</i>  " + typeText :
			name + "  " + typeText;
	}

	private void render(Schema sc, String parent, boolean isNullable) {

		String childId = newIdFor(parent, sc.getName());
		
		startNewLine();
		appendIds(childId, parent);
		append("type", typeFor(sc, isNullable), true);
		append("text", labelFor(sc), false);
		appendDoc(sc.getDoc());
		endLine();
		
		if (showConstraints) showConstraints(sc, childId);
		if (showMetrics) showMetrics(sc, childId);
		
		incrementDepth();
		
		if (sc.getType() == Type.RECORD) {
			for (Field fld : sc.getFields()) { 
				render(fld, childId); 
				}
			}

		if (sc.getType() == Type.ARRAY) {
			render(sc.getElementType(), childId, false);
			}
		
		if (sc.getType() == Type.UNION) {
			System.out.println("####!!!!!####");
			}
		
		decrementDepth();
	}
	
	private void renderParams(String id, Map<Parameter<?>, Object> params, String parentId) {
		
		incrementDepth();

		for (Entry<Parameter<?>, Object> entry : params.entrySet()) {
			
			String paramId = parentId + "." + entry.getKey().id();

			startNewLine();
			appendIds(paramId, parentId);
			append("type", "PARAMETER", true);
			append("text", entry.getKey().id() + ": " + entry.getValue(), false);
			endLine();
		}
		decrementDepth();
	}

	private void render(Field field, String parent) {

		String childId = newIdFor(parent, field.name());
		
		Type fieldType = field.schema().getType();
		boolean nullable = isNullableSingleType(field);
		
		String typeId = preferredTypeFor(field).toString() + (nullable ? NullableTypeSuffix : "");
		
		startNewLine();
		appendIds(childId, parent);
		append("text", labelFor(field), true);
		append("type", typeId, false);
		addExtras(field);
		endLine();
		
		if (showConstraints) showConstraints(field, childId);
		if (showMetrics)	showMetrics(field, childId);
		
		switch (fieldType) {
			case RECORD :	render(field.schema(), childId, false); return;
			case ARRAY : 	render(field.schema().getElementType(), childId, false); return;
			case UNION :	
				// get primary type
				Schema primary = nullableSingleTypeIn(field.schema().getTypes());
				
				switch (primary.getType()) {
					case RECORD : render(primary, childId, true);  return;
					case ARRAY  : render(primary.getElementType(), childId, true);  return;
					default : return;
					}

			default : return;
			}
	}

	private void addExtras(Field field) {
		
		List<Entry<String, String>> extras = new ArrayList<>(3);
		
		String dflt = defaultFor(field);
		if (dflt != null) {
			extras.add(new AbstractMap.SimpleEntry<String, String>("default", dflt));
		}
		String doc = field.doc();
		if (doc != null) {
			extras.add(new AbstractMap.SimpleEntry<String, String>("doc", doc));
		}
		
		String size = childIntNodeAsString(field, "size");
		if (size!= null) {
			extras.add(new AbstractMap.SimpleEntry<String, String>("size", size));
		}

		appendAsData(extras);
	}

	// ======================= constraints & metrics ==========================

	private void renderMetric(String id, MetricDescriptor desc, String parentId) {
		
		startNewLine();
		appendIds(id, parentId);
		append("type", "METRIC", true);
		append("text", id, false);
		if (desc.description != null) appendAsData("doc", desc.description);
		endLine();
	}
	
	private void renderConstraint(String id, ValueConstraint<?> constraint, String parentId) {
		
		String constraintId = id + rowCount++;
		
		startNewLine();
		appendIds(constraintId, parentId);
		append("type", "CONSTRAINT", true);
		append("text", constraint.typeTag(), false);
		endLine();
		
		Map<Parameter<?>, Object> params = constraint.parameters();
		if (params == null) return;
		
		renderParams(id, params, constraintId);
	}
	
	private void showMetrics(Schema schema, String childId) {
		
		if (AvroMetricUtil.hasLocalMetrics(schema)) {
			Map<String, MetricDescriptor> metrics = AvroMetricUtil.localMetricsIn(schema);
			show(childId, metrics);
		}
	}

	private void showMetrics(Field field, String childId) {
		
		if (AvroMetricUtil.hasLocalMetrics(field)) {
			Map<String, MetricDescriptor> metrics = AvroMetricUtil.localMetricsIn(field);
			show(childId, metrics);
		}
	}

	private void show(String childId, Map<String, MetricDescriptor> metrics) {

		incrementDepth();
		for (Entry<String, MetricDescriptor> entry : metrics.entrySet()) {
			renderMetric(entry.getKey(), entry.getValue(), childId);
		}
		decrementDepth();
	}

	private void showConstraints(Schema schema, String childId) {
		
		if (AvroConstraintUtil.hasLocalConstraints(schema)) {
			Collection<ValueConstraint<?>> constraints = AvroConstraintUtil.localConstraintsIn(schema, Factory);
			show(childId, constraints);
		}
	}

	private void show(String childId, Collection<ValueConstraint<?>> constraints) {

		incrementDepth();
		for (ValueConstraint<?> vc : constraints) {
			renderConstraint(vc.id(), vc, childId);
		}
		decrementDepth();
	}

	private void showConstraints(Field field, String childId) {
		
		if (AvroConstraintUtil.hasLocalConstraints(field)) {
			Collection<ValueConstraint<?>> constraints = AvroConstraintUtil.localConstraintsIn(field, Factory);
			show(childId, constraints);
		}
	}

}
