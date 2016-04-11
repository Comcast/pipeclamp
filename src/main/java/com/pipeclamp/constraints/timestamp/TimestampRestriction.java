package com.pipeclamp.constraints.timestamp;

/**
 *
 * @author Brian Remedios
 */
public enum TimestampRestriction {

	Future("futureOnly"),
	Historical("historicalOnly");

	public final String keyword;

	public static TimestampRestriction fromKeyword(String word) {

		for (TimestampRestriction tr : values()) {
			if (tr.keyword.equals(word)) return tr;
		}
		return null;
	}

	TimestampRestriction(String theKeyword) {
		keyword = theKeyword;
	}



	@Override
	public String toString() { return keyword; }

}
