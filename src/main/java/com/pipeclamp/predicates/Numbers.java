package com.pipeclamp.predicates;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 */
public class Numbers {

	public static final Predicate<Number> IsZero = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && "0".equals(number.toString());
			}
	};
	
	public static final Predicate<Number> IsPositive = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && number.longValue() > 0;
			}
	};
	
	public static final Predicate<Number> IsNegative = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && number.longValue() < 0;
			}
	};
	
	public static final Predicate<Number> IsEven = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && number.longValue() % 2 == 0;
			}
	};
	
	public static final Predicate<Number> IsOdd = new Predicate<Number>() {
		public boolean apply(Number number) {
			return number != null && number.longValue() % 2 == 1;
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
