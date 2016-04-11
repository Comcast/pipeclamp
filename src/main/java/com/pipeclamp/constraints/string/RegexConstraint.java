package com.pipeclamp.constraints.string;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.params.StringParameter;

/**
 * Evaluates string values to ensure they are match the supplied regular expression pattern.
 * You can supply a custom regular expression or specify a predefined one (easier)
 *
 * @author Brian Remedios
 */
public class RegexConstraint extends AbstractStringConstraint {

	private final String regex;
	private final Pattern pattern;

	public static final String TypeTag = "regex";

	public static final ConstraintBuilder<String> Builder = new ConstraintBuilder<String>() {

		final StringParameter PATTERN		= new StringParameter("pattern", "regular expression pattern");
		final StringParameter PATTERN_ID	= new StringParameter("patternId", "regular expression pattern identifier");

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			String regex = stringValueIn(values, PATTERN);

			if (regex != null && regex.trim().length() > 0)
				return Arrays.<ValueConstraint<?>>asList(
						new RegexConstraint("", nullsAllowed, regex)
						);

			String id = stringValueIn(values, PATTERN_ID);

			if (id != null) {
				RegexDescriptor rd = RegexDescriptor.ALL.get(id);
				if (rd == null) throw new IllegalArgumentException("Unknown pattern id: " + id);

				return Arrays.<ValueConstraint<?>>asList(new RegexConstraint(rd.id(), nullsAllowed, rd.regex));
			}

			return null;
		}

		public Parameter<?>[] parameters() { return new Parameter[] { PATTERN, PATTERN_ID };	}
	};

	public RegexConstraint(RegexDescriptor desc) {
		this(desc.id(), false, desc.regex);
	}

	public RegexConstraint(String theId, boolean nullAllowed, String theRegex) {
		super(theId, nullAllowed);

		regex = theRegex;
		pattern = Pattern.compile(theRegex);
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
	//	params.put(PATTERN, regex);		// TODO
		return params;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public String toString() {
		return "RegexConstraint  regex: " + regex;
	}

	private String regexLabel() {
		return id() != null ? id() : regex;
	}

	@Override
	public Violation typedErrorFor(String value) {

		if (pattern.matcher(value).matches()) return null;

		return new Violation(this, '\'' + value + "' does not agree with regex: " + regexLabel());
	}
}
