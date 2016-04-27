package com.pipeclamp.constraints.multivalue;

import com.pipeclamp.api.RegisteredItem;

/**
 *
 * @author Brian Remedios
 */
public enum Comparison implements RegisteredItem {

	GreaterThan(">", "greater than", new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) > 0; } }),
	LessThan("<",	"less than",	 new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) < 0; } }),
	EqualTo("=",	"equal to",		 new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) == 0; } });

	public final String operator;
	public final String label;
	public final Checker comp;

	/**
	 * 
	 * @param op
	 *
	 * @return Comparison
	 */
	public static Comparison forOperator(String op) {
		
		for (Comparison comp : values()) {
			if (comp.operator.equals(op)) return comp;
		}
		return null;
	}
	
	Comparison(String theOp, String theLabel, Checker theComp) {
		operator = theOp;
		label = theLabel;
		comp = theComp;
	}

	@Override
	public String id() { return operator; }

	@Override
	public String description() { return label; }
}
