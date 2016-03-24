package com.pipeclamp.predicates;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 */
public class Numbers {

	public static final Predicate<Number> IsZero = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && number.toString().equals("0");
			}
	};

	public static Predicate<Number> inRange(final long min, final long max) {

		return new Predicate<Number>() {
			public boolean apply(Number number) {
				return number != null &&
					number.longValue() >= min &&
					number.longValue() <= max;
				}
		};
	};
}
