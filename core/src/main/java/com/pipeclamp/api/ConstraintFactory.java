package com.pipeclamp.api;

import java.io.PrintStream;
import java.util.Set;

/**
 * 
 * @author Brian Remedios
 *
 * @param <T>
 */
public interface ConstraintFactory<T extends Object> {

	/**
	 * 
	 * @return Set<T>
	 */
	Set<T> registeredTypes();
	
	/**
	 * 
	 * @param type
	 * @param builders
	 */
	void register(T type, ConstraintBuilder<?>... builders);


	/**
	 * 
	 * @param rawType
	 * @param id
	 * @return
	 */
	ConstraintBuilder<?> builderFor(String rawType, String id);
	
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	ConstraintBuilder<?> builderFor(T type, String id);

	/**
	 * 
	 * @param out
	 */
	void showOn(PrintStream out);
}