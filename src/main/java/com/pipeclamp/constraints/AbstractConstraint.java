package com.pipeclamp.constraints;

import java.util.Collections;
import java.util.Map;

import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.TaggedItem;
import com.pipeclamp.avro.AvroConstraintUtil;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractConstraint implements TaggedItem {

	public final String id;
	public final boolean nullsAllowed;

	protected static String typeIn(Map<String, String> params) {
		return params.get(AvroConstraintUtil.TypeKey);
	}

	protected AbstractConstraint(String theId, boolean nullsAllowedFlag) {
		id = theId;
		nullsAllowed = nullsAllowedFlag;
	}

	@Override
	public String id() { return id; }

	public Map<Parameter<?>, Object> parameters() { return Collections.emptyMap(); }
}
