package com.pipeclamp.api;

import java.util.Map;

/**
 * Denotes a type that can return its operating parameters & values in the form of a map.
 *
 * @author Brian Remedios
 */
public interface ParametricItem {

	/**
	 *
	 * @return Map<Parameter<?>, Object>
	 */
	Map<Parameter<?>, Object> parameters();
}