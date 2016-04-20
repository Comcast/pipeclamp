package com.pipeclamp.avro;

import java.util.Map;

import org.apache.avro.Schema;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.constraints.BasicConstraintFactory;
import com.pipeclamp.constraints.NotNullConstraint;
import com.pipeclamp.constraints.bytes.ByteArraySizeConstraint;
import com.pipeclamp.constraints.bytes.BytePrefixConstraint;
import com.pipeclamp.constraints.collections.CollectionContentConstraint;
import com.pipeclamp.constraints.collections.CollectionSizeConstraint;
import com.pipeclamp.constraints.collections.MapKeyConstraint;
import com.pipeclamp.constraints.collections.MapValueConstraint;
import com.pipeclamp.constraints.number.MathConstraint;
import com.pipeclamp.constraints.number.NumericConstraint;
import com.pipeclamp.constraints.string.IllegalCharacterConstraint;
import com.pipeclamp.constraints.string.RegexConstraint;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.constraints.string.WhitespaceConstraint;
import com.pipeclamp.constraints.string.WordSetConstraint;
import com.pipeclamp.constraints.timestamp.TimestampRangeConstraint;

/**
 *
 * @author Brian Remedios
 */
public class AvroConfiguration {

	private static final Map<String, Schema.Type> SchemaTypesByString = ImmutableMap.<String, Schema.Type>builder().
		      put("int", Schema.Type.INT).
		      put("long", Schema.Type.LONG).
		      put("string", Schema.Type.STRING).
		      put("float", Schema.Type.FLOAT).
		      put("double", Schema.Type.DOUBLE).
		      put("boolean", Schema.Type.BOOLEAN).
		      put("bytes", Schema.Type.BYTES).
		      put("map", Schema.Type.MAP).
		      put("record", Schema.Type.RECORD).
		      build();
	
	public static final ConstraintFactory<Schema.Type> ConstraintFactory = new BasicConstraintFactory<>(SchemaTypesByString);

	private static final Map<Schema.Type, Class<?>> JavaTypesBySchemaType = ImmutableMap.<Schema.Type, Class<?>>builder().
		      put(Schema.Type.INT, Integer.class).
		      put(Schema.Type.LONG, Long.class).
		      put(Schema.Type.STRING, String.class).
		      put(Schema.Type.FLOAT, Float.class).
		      put(Schema.Type.DOUBLE, Double.class).
		      put(Schema.Type.BOOLEAN, Boolean.class).
		      put(Schema.Type.BYTES, Byte[].class).
		      put(Schema.Type.MAP, Map.class).
		      put(Schema.Type.RECORD, Object.class).
		      build();

	static {
		setup();
	}

	private AvroConfiguration() { }


	private static void setup() {
		ConstraintFactory.register(Schema.Type.BYTES, 	BytePrefixConstraint.Builder, ByteArraySizeConstraint.Builder );
		ConstraintFactory.register(Schema.Type.INT,		NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.LONG,	NumericConstraint.Builder, MathConstraint.Builder, TimestampRangeConstraint.Builder );
		ConstraintFactory.register(Schema.Type.FLOAT,	NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.DOUBLE,	NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.ARRAY,	CollectionSizeConstraint.Builder, CollectionContentConstraint.Builder );
		ConstraintFactory.register(Schema.Type.STRING,	StringLengthConstraint.Builder, IllegalCharacterConstraint.Builder, RegexConstraint.Builder, WhitespaceConstraint.Builder, WordSetConstraint.Builder );
		ConstraintFactory.register(Schema.Type.MAP,     MapKeyConstraint.builderWith(ConstraintFactory), MapValueConstraint.builderWith(ConstraintFactory));
		ConstraintFactory.register(Schema.Type.RECORD, 	NotNullConstraint.Builder );
	}


	public static Class<?> javaTypeFor(Schema.Type type) { return JavaTypesBySchemaType.get(type); }
}
