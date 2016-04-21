package com.pipeclamp.constraints.multivalue;

import java.util.Map;

import com.pipeclamp.api.TaggedItem;
import com.pipeclamp.api.Violation;
import com.pipeclamp.path.Path;

/**
 * Compares the values found at two or more paths and returns a violation
 * if necessary. All values have to be be error-free as determined
 *
 * @author Brian Remedios
 */
public interface MultiValueConstraint<T extends Object> extends TaggedItem {

	/**
	 *
	 * @return
	 */
	Path<T,?>[] valuePaths();

	/**
	 *
	 * @param values
	 *
	 * @return Violation
	 */
	Violation evaluate(Map<Path<T,?>, Object> values);
}
