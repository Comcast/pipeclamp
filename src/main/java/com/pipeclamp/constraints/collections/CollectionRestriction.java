package com.pipeclamp.constraints.collections;

import com.pipeclamp.api.RegisteredItem;

/**
 * Denotes the range of possible restrictions across the items in a collection.
 *
 * @author Brian Remedios
 */
public enum CollectionRestriction implements RegisteredItem {

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

	CollectionRestriction(String theKeyword, String theDesc) {
		keyword = theKeyword;
		description = theDesc;
	}

	@Override
	public String toString() { return keyword; }
	
	@Override
	public String id() { return keyword; }
	
	@Override
	public String description() { return description; }
}
