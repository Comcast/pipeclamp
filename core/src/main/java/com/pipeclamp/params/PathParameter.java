package com.pipeclamp.params;

import org.apache.avro.Schema.Type;

import com.pipeclamp.path.Path;

/**
 * 
 * @author Brian Remedios
 */
public class PathParameter extends AbstractParameter<Path<?,?>> {

	public PathParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Path<?,?> valueIn(String text, Type type) {
		
		return null;	// need regex delimiter & path builder (out of scope here)
	}
}
