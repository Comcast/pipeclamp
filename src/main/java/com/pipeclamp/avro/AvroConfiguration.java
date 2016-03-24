package com.pipeclamp.avro;

import java.util.Map;

import org.apache.avro.Schema;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.constraints.BasicConstraintFactory;
import com.pipeclamp.constraints.bytes.BytePrefixConstraint;
import com.pipeclamp.constraints.collections.CollectionContentConstraint;
import com.pipeclamp.constraints.collections.CollectionSizeConstraint;
import com.pipeclamp.constraints.number.MathConstraint;
import com.pipeclamp.constraints.number.NumericConstraint;
import com.pipeclamp.constraints.string.RegexConstraint;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.constraints.string.WordSetConstraint;
import com.pipeclamp.constraints.timestamp.TimestampRangeConstraint;

/**
 * 
 * @author Brian Remedios
 */
public class AvroConfiguration {

	public static final ConstraintFactory<Schema.Type> ConstraintFactory = new BasicConstraintFactory<>();

	private static final Map<Schema.Type, Class<?>> JavaTypesBySchemaType = ImmutableMap.<Schema.Type, Class<?>>builder().
		      put(Schema.Type.INT, Integer.class).
		      put(Schema.Type.LONG, Long.class).
		      put(Schema.Type.STRING, String.class).
		      put(Schema.Type.FLOAT, Float.class).
		      put(Schema.Type.DOUBLE, Double.class).
		      put(Schema.Type.BOOLEAN, Boolean.class).
		      put(Schema.Type.BYTES, Byte[].class).		// TODO eval
		      build();
	
	static {
		setup();
	}
	
	private AvroConfiguration() { }

	
	private static void setup() {
		ConstraintFactory.register(Schema.Type.BYTES, 	BytePrefixConstraint.Builder );
		ConstraintFactory.register(Schema.Type.INT,		NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.LONG,	NumericConstraint.Builder, MathConstraint.Builder, TimestampRangeConstraint.Builder );
		ConstraintFactory.register(Schema.Type.FLOAT,	NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.DOUBLE,	NumericConstraint.Builder, MathConstraint.Builder );
		ConstraintFactory.register(Schema.Type.ARRAY,	CollectionSizeConstraint.Builder, CollectionContentConstraint.Builder );
		ConstraintFactory.register(Schema.Type.STRING,	StringLengthConstraint.Builder, RegexConstraint.Builder, WordSetConstraint.Builder );
	}
	

	public static Class<?> javaTypeFor(Schema.Type type) { return JavaTypesBySchemaType.get(type); }
}
