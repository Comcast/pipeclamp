package com.pipeclamp.constraints.string;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.Classifier;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.StringArrayParameter;

/**
 *
 * @author Brian Remedios
 */
public class ClassificationConstraint extends AbstractStringConstraint {

	private final Classifier<String> classifier;
	private final Set<String> classes;

	public static final String TypeTag = "classifier";

	public static final String Docs = "ToDo";

	public static final WordRestrictionParameter Function = new WordRestrictionParameter("function", "to do");	// TODO
	public static final StringArrayParameter Options = new StringArrayParameter("options", "to do", " ");	// TODO

	public static final ConstraintBuilder<String> Builder = new BasicConstraintBuilder<String>(TypeTag, ClassificationConstraint.class, Docs, Function, Options) {

		public Collection<ValueConstraint<?>> constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

			WordRestriction restriction = Function.valueIn(values.remove(Function.id()), null);
			String[] opts = Options.valueIn(values.remove(Options.id()), null);
			if (restriction == null) return null;

			Collection<ValueConstraint<?>> constraints = new ArrayList<ValueConstraint<?>>();

		//	constraints.add( new ClassificationConstraint("", nullsAllowed, opts) );

			return constraints.isEmpty() ? null : constraints;
		}

	};

	public ClassificationConstraint(String theId, boolean nullAllowed, Classifier<String> theClassifier, Set<String> theClasses) {
		super(theId, nullAllowed);

		classifier = theClassifier;
		classes = theClasses;
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {

		Map<Parameter<?>, Object> params = new HashMap<>(2);
		if (classifier != null) params.put(Function, classifier);
		if (classes != null) params.put(Options, classes);
		return params;
	}

	@Override
	public Violation typedErrorFor(String value) {

		String result = classifier.classify(value);
		if (classes.contains(result)) return null;

		return new Violation(this, "cannot contain class: " + result);
	}

	@Override
	public String typeTag() { return TypeTag; }

}
