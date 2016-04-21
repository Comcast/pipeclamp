package com.pipeclamp.constraints.string;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.StringArrayParameter;

/**
 * Evaluates strings to ensure that they do or don't contain any of the specified words.
 *
 * @author Brian Remedios
 */
public class WordSetConstraint extends AbstractStringConstraint {

	private final String[] words;
	private final WordRestriction restriction;

	public static final String TypeTag = "wordSet";

	public static final String Docs = "Evaluates strings to ensure that they do or don't contain any of the specified words";

	public static final WordRestrictionParameter Function = new WordRestrictionParameter("function", "to do");	// TODO
	public static final StringArrayParameter WORDS = new StringArrayParameter("options", "to do", "The words of interest");

	public static final ConstraintBuilder<String> Builder = new BasicConstraintBuilder<String>(TypeTag, WordSetConstraint.class, Docs, Function, WORDS) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			WordRestriction restriction = Function.valueIn(values.remove(Function.id()), null);
			String[] words = WORDS.valueIn(values.remove(WORDS.id()), null);
			if (restriction == null || words == null || words.length == 0) return null;

			return Arrays.<ValueConstraint<?>>asList( new WordSetConstraint("", nullsAllowed, words, restriction) );
		}
	};

	public WordSetConstraint(String theId, boolean nullAllowed, String[] theWords, WordRestriction theRestriction) {
		super(theId, nullAllowed);

		words = theWords;
		restriction = theRestriction;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		params.put(Function, restriction.operator);
		params.put(WORDS, words);
		return params;
	}

	@Override
	public Violation typedErrorFor(String value) {

		Set<String> matches = new HashSet<String>();

		for (String word : words) {
			if (value.contains(word)) matches.add(word);
		}

		if (matches.isEmpty()) {
			switch (restriction) {
				case MustHaveOne :
				case MustHaveAll : return new Violation(this, "missing required word(s)");
				default: return null;
				}
		}

		switch (restriction) {
			case MustHaveOne : return null;
			case MustHaveAll : if (matches.size() < words.length) return new Violation(this, "missing words"); break;
			case CannotHave : return new Violation(this, "Illegal words found: " + matches);
			}

		return null;
	}

	@Override
	public String toString() {
		return "WordSetConstraint  words: " + words;
	}
}
