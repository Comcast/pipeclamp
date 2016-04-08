package com.pipeclamp.predicates;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Predicate;
import com.pipeclamp.util.StringUtil;

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
	
	Predicate<String> IsAlpha = new Predicate<String>() {
		public boolean apply(String value) {
			return StringUtils.isAlpha(value);
			}
	};
	
	Predicate<String> IsNumeric = new Predicate<String>() {
		public boolean apply(String value) {
			return StringUtils.isNumeric(value);
			}
	};
	
	Predicate<String> IsAlphanumeric = new Predicate<String>() {
		public boolean apply(String value) {
			return StringUtils.isAlphanumeric(value);
			}
	};
	
	Predicate<String> HasBoundingWhitespace = new Predicate<String>() {
		public boolean apply(String value) {
			return StringUtil.hasBoundingWhitespace(value);
			}
	};

	Predicate<String> HasNewlineChar = new Predicate<String>() {
		public boolean apply(String value) {
			return value != null && value.contains("\n");
			}
	};
}
