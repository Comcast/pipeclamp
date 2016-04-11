package com.pipeclamp.constraints.multivalue;

import java.util.Map;

import com.pipeclamp.api.Violation;
import com.pipeclamp.path.Path;

/**
 *
 * @author Brian Remedios
 */
public class ComparativeConstraint<T extends Object> extends AbstractMultivalueConstraint<T> {

	private final Path<T,?> op1Path;
	private final Path<T,?> op2Path;
	private final Comparison comparison;

	public static final String TypeTag = "comparison";

	public ComparativeConstraint(String id, Path<T,?> op1, Path<T,?> op2, Comparison comp) {
		super(id, false);

		op1Path = op1;
		op2Path = op2;
		comparison = comp;
	}

	@Override
	public String typeTag() { return TypeTag; }

	@Override
	public Path[] valuePaths() {
		return new Path[] { op1Path, op2Path };
	}

	@Override
	public Violation evaluate(Map<Path<T,?>, Object> values) {

		Comparable a = (Comparable)values.get(op1Path);
		Comparable b = (Comparable)values.get(op2Path);

		if (a== null || b == null) return null;	// unable to compare

		if (comparison.comp.check(a, b)) return null;

	//	return new Violation(this, a + " is not " + comparison.label + " " + b);

		// TODO
		return null;
	}

}
