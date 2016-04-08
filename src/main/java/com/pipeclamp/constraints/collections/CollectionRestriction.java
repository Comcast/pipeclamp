package com.pipeclamp.constraints.collections;

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

	CollectionRestriction(String theKeyword, String theDesc) {
		keyword = theKeyword;
		description = theDesc;
	}
	
	@Override
	public String toString() { return keyword; }
}
