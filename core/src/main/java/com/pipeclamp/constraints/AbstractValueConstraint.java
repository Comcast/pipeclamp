package com.pipeclamp.constraints;

import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.params.BooleanParameter;
import com.pipeclamp.params.IntegerParameter;
import com.pipeclamp.params.NumberParameter;
import com.pipeclamp.params.StringParameter;
import com.pipeclamp.params.TimestampParameter;

/**
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public abstract class AbstractValueConstraint<V extends Object> extends AbstractConstraint implements ValueConstraint<V>{

	protected static Boolean booleanValueIn(Map<String, String> values, BooleanParameter bp) {
		if (values.containsKey(bp.id())) {
			String value = values.remove(bp.id());
			return bp.valueIn(value, Type.BOOLEAN);
		}
		return null;
	}
	
	protected static Number numberValueIn(Map<String, String> values, NumberParameter ip, Type type) {
		if (values.containsKey(ip.id())) {
			String value = values.remove(ip.id());
			return ip.valueIn(value, type);
		}
		return null;
	}

//	protected static byte[][] byteArrayValuesIn(Map<String, String> values, ByteArrayParameter bap) {
//		if (values.containsKey(bap.id())) {
//			String value = values.remove(bap.id());
//			return bap.valueIn(value, Type.BYTES);
//		}
//		return null;
//	}

	protected static Integer intValueIn(Map<String, String> values, IntegerParameter ip) {
		if (values.containsKey(ip.id())) {
			String value = values.remove(ip.id());
			return ip.valueIn(value, Type.INT);
		}
		return null;
	}

//	protected static Long longValueIn(Map<String, String> values, LongParameter ip) {
//		if (values.containsKey(ip.id())) {
//			String value = values.remove(ip.id());
//			return ip.valueIn(value, Type.LONG);
//		}
//		return null;
//	}
//
//	protected static Float floatValueIn(Map<String, String> values, FloatParameter ip) {
//		if (values.containsKey(ip.id())) {
//			String value = values.remove(ip.id());
//			return ip.valueIn(value, Type.FLOAT);
//		}
//		return null;
//	}

	protected static String stringValueIn(Map<String, String> values, StringParameter ip) {
		if (values.containsKey(ip.id())) {
			String value = values.remove(ip.id());
			return ip.valueIn(value, Type.STRING);
		}
		return null;
	}

	protected static Long timestampValueIn(Map<String, String> values, TimestampParameter ip) {
		if (values.containsKey(ip.id())) {
			String value = values.remove(ip.id());
			return ip.valueIn(value, Type.LONG);
		}
		return null;
	}

//	protected static String[] arrayValueIn(Map<String, String> values, StringArrayParameter sap) {
//		if (values.containsKey(sap.id())) {
//			String value = values.remove(sap.id());
//			return sap.valueIn(value, null);
//		}
//		return null;
//	}
//
//	protected static String[] arrayValueIn(Map<String, String> values, String key) {
//		if (values.containsKey(key)) {
//			String value = values.remove(key);
//			return value.split(" ");
//		}
//		return null;
//	}

	protected AbstractValueConstraint(String theId, boolean nullAllowed) {
		super(theId, nullAllowed);
	}

	protected abstract V cast(Object value);

	@Override
	public Violation errorFor(Object value) {

		if (value == null) {
			return nullsAllowed ?
				null :
				new Violation(this, "Missing value");
		}

		V typed = null;

		try {
			typed = value == null ? null : cast(value);
			return typedErrorFor(typed);
			} catch (ClassCastException cce) {
				return new Violation(this, cce.getMessage());
			}
	}
}
