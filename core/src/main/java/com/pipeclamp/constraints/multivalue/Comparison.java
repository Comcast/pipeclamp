package com.pipeclamp.constraints.multivalue;



/**
 *
 * @author Brian Remedios
 */
public enum Comparison {

	GreaterThan(">", "greater than", new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) > 0; } }),
	LessThan("<",	"less than",	 new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) < 0; } }),
	EqualTo("=",	"equal to",		 new Checker() { public boolean check(Comparable a, Comparable b) { return a.compareTo(b) == 0; } });

	public final String operator;
	public final String label;
	public final Checker comp;

	Comparison(String theOp, String theLabel, Checker theComp) {
		operator = theOp;
		label = theLabel;
		comp = theComp;
	}
}
