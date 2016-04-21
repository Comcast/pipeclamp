package com.pipeclamp.constraints.timestamp;

import com.pipeclamp.api.RegisteredItem;

/**
 *
 * @author Brian Remedios
 */
public enum TimestampRestriction implements RegisteredItem {

	Future("futureOnly", "todo"),
	Historical("historicalOnly", "todo");

	public final String keyword;
	public final String description;
	
	public static TimestampRestriction fromKeyword(String word) {

		for (TimestampRestriction tr : values()) {
			if (tr.keyword.equals(word)) return tr;
		}
		return null;
	}

	TimestampRestriction(String theKeyword, String theDesc) {
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
