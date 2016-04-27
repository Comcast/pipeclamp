package com.pipeclamp.constraints.multivalue;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractParameter;

/**
 * 
 * @author Brian Remedios
 */
public class ComparisonParameter extends AbstractParameter<Comparison> {

	protected ComparisonParameter(String theId, String theDescription) {
		super(theId, theDescription);
	}

	@Override
	public Comparison valueIn(String text, Type type) {
		return Comparison.forOperator(text);
	}

}
