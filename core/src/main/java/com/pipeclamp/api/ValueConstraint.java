package com.pipeclamp.api;

/**
 * Specifies the behaviour of a single-value constraint. When dealing with values of an
 * unknown type you would normally invoke the errorFor(Object) method which tests & casts
 * the value accordingly.  It then calls the typedErrorFor(V) method in succession.
 *
 * @author Brian Remedios
 *
 * @param <V>
 */
public interface ValueConstraint<V extends Object> extends Constraint<V> {

	/**
	 * Tests the value and casts it before invoking typedErrorFor()
	 *
	 * @param value
	 *
	 * @return violation
	 */
	Violation errorFor(Object value);

	/**
	 *
	 * @param value
	 *
	 * @return violation
	 */
	Violation typedErrorFor(V value);
}