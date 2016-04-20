package com.pipeclamp.constraints.string;

import com.pipeclamp.api.RegisteredItem;

/**
 *
 * @author Brian Remedios
 */
public enum WordRestriction implements RegisteredItem {

	MustHaveAll("mustHave", 	"must have all the words"),
	MustHaveOne("mustHaveOne",	"must have at least one"),
	CannotHave("cannotHave",	"cannot have any");

	public final String operator;
	public final String label;

	public static WordRestriction forKeyword(String word) {

		for (WordRestriction wr : values()) {
			if (wr.operator.equals(word)) return wr;
		}
		return null;
	}

	WordRestriction(String theOp, String theLabel) {
		operator = theOp;
		label = theLabel;
	}

	@Override
	public String toString() { return operator; }
	
	@Override
	public String id() { return operator; }
	
	@Override
	public String description() { return label; }
}
