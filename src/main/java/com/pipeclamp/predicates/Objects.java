package com.pipeclamp.predicates;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 */
public interface Objects {

	Predicate<Object> IsNull = new Predicate<Object>() {
		public boolean apply(Object value) { return value == null; }
	};

	Predicate<Object> NotNull = new Predicate<Object>() {
		public boolean apply(Object value) { return value != null; }
	};
}
