package com.pipeclamp.api;

import java.io.PrintStream;

/**
 * 
 * @author Brian Remedios
 *
 * @param <T>
 */
public interface ConstraintFactory<T extends Object> {

	/**
	 * 
	 * @param type
	 * @param builders
	 */
	void register(T type, ConstraintBuilder<?>... builders);


	/**
	 * 
	 * @param type
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