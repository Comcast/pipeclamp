package com.pipeclamp.api;

import java.util.Map;

/**
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