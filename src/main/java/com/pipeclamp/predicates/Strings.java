package com.pipeclamp.predicates;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Predicate;

/**
 *
 * @author Brian Remedios
 */
public interface Strings {

	Predicate<String> IsEmpty = new Predicate<String>() {
		public boolean apply(String value) {
			return value == null ||
				StringUtils.isEmpty(value) ||
				StringUtils.isBlank(value);
			}
	};

	Predicate<String> IsNotEmpty = new Predicate<String>() {
		public boolean apply(String value) {
			return !IsEmpty.apply(value);
			}
	};
}
