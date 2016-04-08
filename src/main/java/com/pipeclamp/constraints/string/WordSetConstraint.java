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

	public static final WordRestrictionParameter Function = new WordRestrictionParameter("function", "to do");	// TODO
	public static final StringArrayParameter Options = new StringArrayParameter("options", "to do", " ");	// TODO

	public static final ConstraintBuilder<String> Builder = new ConstraintBuilder<String>() {

		public String id() { return TypeTag; };

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			WordRestriction restriction = Function.valueIn(values.remove(Function.id()), null);
			String[] opts = Options.valueIn(values.remove(Options.id()), null);
			if (restriction == null) return null;
		
			Collection<ValueConstraint<?>> constraints = new ArrayList<ValueConstraint<?>>();

			constraints.add( new WordSetConstraint("", nullsAllowed, opts, restriction) );

			return constraints.isEmpty() ? null : constraints;
		}

		@Override
		public Parameter<?>[] parameters() { return new Parameter<?>[] { Function, Options }; }
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
