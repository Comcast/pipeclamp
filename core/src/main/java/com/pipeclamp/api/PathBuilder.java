package com.pipeclamp.api;

import com.pipeclamp.path.Path;

/**
 * Constructs technology-specific paths from a sequence of strings.
 *
 * @author Brian Remedios
 */
public interface PathBuilder {

	/**
	 * 
	 * @param parts
	 *
	 * @return Path
	 */
	Path<?,?> pathFor(String[] parts);
}
