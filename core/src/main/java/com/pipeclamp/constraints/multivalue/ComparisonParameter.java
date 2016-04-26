package com.pipeclamp.constraints.multivalue;

import org.apache.avro.Schema.Type;

import com.pipeclamp.params.AbstractParameter;

public class ComparisonParameter extends AbstractParameter<Comparison> {

	protected ComparisonParameter(String theId, String theDescription) {
		super(theId, theDescription);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Comparison valueIn(String text, Type type) {
		// TODO Auto-generated method stub
		return null;
	}

}
