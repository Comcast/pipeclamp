package com.pipeclamp.constraints;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.apache.avro.Schema.Type;

import com.pipeclamp.AbstractRegisteredItem;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.RegisteredItem;
import com.pipeclamp.api.TaggedItem;
import com.pipeclamp.avro.AvroConstraintUtil;
import com.pipeclamp.params.BooleanParameter;
import com.pipeclamp.params.IntegerParameter;
import com.pipeclamp.params.NumberParameter;
import com.pipeclamp.params.StringParameter;
import com.pipeclamp.params.TimestampParameter;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractConstraint extends AbstractRegisteredItem implements RegisteredItem, TaggedItem {

	public final boolean nullsAllowed;

	protected static String typeIn(Map<String, String> params) {
		return params.get(AvroConstraintUtil.TypeKey);
	}

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

	protected AbstractConstraint(String theId, boolean nullsAllowedFlag) {
		super(theId, null);
	
		nullsAllowed = nullsAllowedFlag;
	}

	public void description(String aDescription) { pDescription(aDescription); }
	
	public Map<Parameter<?>, Object> parameters() { return Collections.emptyMap(); }
	
	@Override
	public boolean equals(Object other) {
		
		if (!super.equals(other)) return false;
		
		if (!(other instanceof AbstractConstraint)) return false;
		
		AbstractConstraint ari = AbstractConstraint.class.cast(other);
		
		return nullsAllowed == ari.nullsAllowed;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hash(nullsAllowed);
	}
}
