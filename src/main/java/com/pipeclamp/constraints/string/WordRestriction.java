package com.pipeclamp.constraints.string;

import java.util.ArrayList;
import java.util.List;

import com.pipeclamp.api.Parameter;
import com.pipeclamp.params.StringParameter;

/**
 *
 * @author Brian Remedios
 */
public enum WordRestriction {

	MustHaveAll("mustHave", 	"must have all the words"),
	MustHaveOne("mustHaveOne",	"must have at least one"),
	CannotHave("cannotHave",	"cannot have any");

	public final String operator;
	public final String label;

	public static Parameter<?>[] asParameters() {

		List<Parameter<?>> params = new ArrayList<Parameter<?>>();

		for (WordRestriction wr : values()) {
			params.add( new StringParameter(wr.operator, wr.label));
		}

		return params.toArray(new Parameter[params.size()]);
	}

	WordRestriction(String theOp, String theLabel) {
		operator = theOp;
		label = theLabel;
	}
}
