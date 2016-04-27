package com.pipeclamp.constraints.multivalue;

/**
 *
 * @author Brian Remedios
 *
 */
public interface Checker {

	/**
	 * Returns true if the two arguments compare to each other in a
	 * specific manner.
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	boolean check(Comparable a, Comparable b);
}
