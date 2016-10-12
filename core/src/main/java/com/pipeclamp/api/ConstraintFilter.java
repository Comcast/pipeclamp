package com.pipeclamp.api;

/**
 * Just like java.io.FileFilter but for constraints.
 * 
 * @author bremed200
 */
public interface ConstraintFilter {

	/**
	 * 
	 * @param aConstraint
	 * 
	 * @return boolean
	 */
	boolean accept(Constraint<?> aConstraint);
}
