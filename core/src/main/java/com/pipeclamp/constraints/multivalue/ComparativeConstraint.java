package com.pipeclamp.constraints.multivalue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.PathBuilder;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.BasicConstraintBuilder;
import com.pipeclamp.params.PathParameter;
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

	public static final PathParameter Path1 = new PathParameter("path1", "p1");
	public static final PathParameter Path2 = new PathParameter("path2", "p2");
	public static final ComparisonParameter CompParam = new ComparisonParameter("comp", "todo");
	
	/**
	 * 
	 * @param regexDelimiter
	 * @param builder
	 *
	 * @return ConstraintBuilder<Object[]>
	 */
	public static ConstraintBuilder<Object[]> builderFor(final String regexDelimiter, final PathBuilder builder) {
		
		return new BasicConstraintBuilder<Object[]>("theid", ComparativeConstraint.class, "", Path1, Path2, CompParam) {

			@Override
			public Collection constraintsFrom(Type type, boolean nullsAllowed, Map<String, String> values) {

				Path p1 = pathIn(values, Path1, regexDelimiter, builder);
				Path p2 = pathIn(values, Path2, regexDelimiter, builder);
				if (p1 == null || p2 == null) return null;

				Comparison comp = comparisonIn(values, CompParam);
				if (comp == null) return null;

				return Arrays.<MultiValueConstraint<?>>asList(
						new ComparativeConstraint("asdf", p1, p2, comp)
						);
			}
		};
	}

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

		return new Violation(this, a + " is not " + comparison.label + " " + b);
	}

}
