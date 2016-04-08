package com.pipeclamp.constraints.collections;

import java.util.ArrayList;
import java.util.List;

import com.pipeclamp.api.Parameter;
import com.pipeclamp.params.StringParameter;

/**
 * Denotes the range of possible restrictions across the items in a collection.
 *
 * @author Brian Remedios
 */
public enum CollectionRestriction {

	Required("required", "all items required"),
	AnyOf("anyOf", "must have one or more of the items"),
	OneOf("oneOf", "must have only one of the items"),
	AllUnique("allUnique", "all items must be unique"),
	NoneOf("noneOf", "must not contain any of the items");

	public final String keyword;
	public final String description;

	public static CollectionRestriction forKeyword(String word) {
		
		for (CollectionRestriction cr : values()) {
			if (cr.keyword.equals(word)) return cr;
		}
		return null;
	}
	
	public static Parameter<?>[] asParameters() {

		List<Parameter<?>> params =  new ArrayList<Parameter<?>>();

		for (CollectionRestriction wr : values()) {
			params.add( new StringParameter(wr.keyword, wr.description));
		}

		return params.toArray(new Parameter[params.size()]);
	}

	CollectionRestriction(String theKeyword, String theDesc) {
		keyword = theKeyword;
		description = theDesc;
	}
	
	@Override
	public String toString() { return keyword; }
}
