package com.pipeclamp.constraints.string;

import java.util.ArrayList;
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

/**
 * Evaluates strings to ensure that they do or don't contain any of the specified words.
 *
 * @author Brian Remedios
 */
public class WordSetConstraint extends AbstractStringConstraint {

	private final String[] words;
	private final WordRestriction restriction;

	public static final String TypeTag = "wordSet";

	public static final ConstraintBuilder<String> Builder = new ConstraintBuilder<String>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			Collection<ValueConstraint<?>> constraints = new ArrayList<ValueConstraint<?>>();

			for (WordRestriction wr : WordRestriction.values()) {
				String[] words = arrayValueIn(values, wr.operator);
				if (words == null) continue;
				constraints.add( new WordSetConstraint("", nullsAllowed, words, wr) );
			}
			return constraints.isEmpty() ? null : constraints;
		}

		@Override
		public Parameter<?>[] parameters() { return WordRestriction.asParameters(); }
	};

	public WordSetConstraint(String theId, boolean nullAllowed, String[] theWords, WordRestriction theRestriction) {
		super(theId, nullAllowed);

		if (theWords.length == 0) throw new IllegalArgumentException("No words specified");

		words = theWords;
		restriction = theRestriction;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>();
		// TODO
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
				}
			return null;
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
