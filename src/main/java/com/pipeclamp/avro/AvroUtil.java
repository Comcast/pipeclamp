package com.pipeclamp.avro;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.NullNode;
import org.apache.avro.SchemaParseException;

/**
 *
 * @author Brian Remedios
 */
public class AvroUtil {

	private AvroUtil() { }

	public static boolean isRecordType(Schema schema) {
		return schema != null && schema.getType() == Type.RECORD;
	}

	//	public static boolean isArrayType(Schema schema) {
	//		return schema != null && schema.getType() == Type.ARRAY;
	//	}

	public static Type primaryTypeIn(Schema schema) {

		if (schema.getType() == Type.UNION) {
			for (Schema sch : schema.getTypes()) {
				if (sch.getType() == Type.NULL) continue;
				return sch.getType();	// TODO what about more complex UNIONs ?
			}
		}
		return schema.getType();
	}

	public static boolean allowsNull(Schema schema) {

		if (schema.getType() == Type.UNION) {
			for (Schema sch : schema.getTypes()) {
				if (sch.getType() == Type.NULL) return true;
			}
		};
		return false;
	}

	public static Schema parseSchema(String schemaString) {
		try {
			Schema.Parser parser1 = new Schema.Parser();
			return parser1.parse(schemaString);
		} catch (SchemaParseException e) {
			return null;
		}
	}

	public static Schema schemaFrom(URL url) throws UnsupportedEncodingException, IOException, URISyntaxException {

		Path resPath = Paths.get(url.toURI());
		String schemaText = new String(Files.readAllBytes(resPath), "UTF8");
		return parseSchema(schemaText);
	}

	public static String childIntNodeAsString(Field field, String childId) {
		JsonNode childNode = field.getJsonProp(childId);
		if (childNode == null) return null;

		return Integer.toString(childNode.asInt());
	}

	public static String defaultFor(Field field) {

		JsonNode dflt = field.defaultValue();
		if (dflt == null || dflt instanceof NullNode) return null;

		return dflt.asText();
	}

	//		public static boolean hasAnyProperty(Field field, String... names) {
	//			
	//			for (String name : names) {
	//				if (field.getJsonProp(name) != null) return true;
	//			}
	//			return false;
	//		}

	public static boolean denotesPrimitiveValue(Schema schema) {
		switch (schema.getType()) {
		case INT : 
		case BOOLEAN : 
		case ENUM : 
		case BYTES : 
		case LONG : 
		case FIXED : 
		case FLOAT:
		case DOUBLE :
		case STRING : return true;
		default : return false;
		}
	}

	public static boolean isNullableSingleType(Field field) {
		return field.schema().getType() == Type.UNION &&
				isNullableSingleType(field.schema().getTypes());
	}

	public static boolean isNullableSingleType(List<Schema> types) {
		return types.size() == 2 && types.toString().contains("\"null\"");
	}

	public static Schema nonNullSchemaIn(List<Schema> types) {

		for (Schema sch : types) {
			if (sch.getType() == Type.NULL) continue;
			return sch;
		}
		return null;	// won't get here
	}

	public static Schema nullableSingleTypeIn(List<Schema> types) {
		if (isNullableSingleType(types)) {
			return nonNullSchemaIn(types);
		}
		return null;
	}
}
