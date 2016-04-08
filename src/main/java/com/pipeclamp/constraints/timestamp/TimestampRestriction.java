package com.pipeclamp.constraints.timestamp;

import java.util.ArrayList;
import java.util.List;

import com.pipeclamp.api.Parameter;
import com.pipeclamp.params.StringParameter;

/**
 * 
 * @author Brian Remedios
 */
public enum TimestampRestriction {

	Future("futureOnly"),
	Historical("historicalOnly");

	public final String keyword;

	public static TimestampRestriction fromKeyword(String word) {
		
		for (TimestampRestriction tr : values()) {
			if (tr.keyword.equals(word)) return tr;
		}
		return null;
	}
	
	public static Parameter<?>[] asParameters() {

		List<Parameter<?>> params =  new ArrayList<Parameter<?>>();

		for (TimestampRestriction wr : values()) {
			params.add( new StringParameter(wr.keyword, "?"));
		}

		return params.toArray(new Parameter[params.size()]);
	}

	TimestampRestriction(String theKeyword) {
		keyword = theKeyword;
	}
	
	
	
	@Override
	public String toString() { return keyword; }

}
