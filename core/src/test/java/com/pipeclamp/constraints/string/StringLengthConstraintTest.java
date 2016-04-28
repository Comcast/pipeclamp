package com.pipeclamp.constraints.string;


import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.number.NumericConstraint;

public class StringLengthConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(StringLengthConstraint.MIN_LENGTH, 3);

		Collection<Constraint<?>> vc = StringLengthConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		assertNotNull(vc);
		assertTrue(paramsByKey.isEmpty());

		paramsByKey = asParams(StringLengthConstraint.MAX_LENGTH, 3);

		vc = NumericConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		assertNotNull(vc);
		assertEquals(0, paramsByKey.size());
	}

	@Test
	public void typedErrorFor() {

		StringLengthConstraint slc = new StringLengthConstraint("", true, 5, 10);

		Violation v = slc.errorFor(null);	// nulls ok
		assertNull(v);

		v = slc.errorFor("billy");
		assertNull(v);

		v = slc.errorFor("bill");
		assertNotNull(v);

		v = slc.errorFor("bill bill bill bill");
		assertNotNull(v);
	}
	
	@Test
	public void testEquals() {
		
		StringLengthConstraint c1 = new StringLengthConstraint("", true, 5, 10);
		StringLengthConstraint c2 = new StringLengthConstraint("", true, 5, 10);
		
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertEquals(c1.hashCode(), c2.hashCode());
		
		c2.description("asdf");
		assertFalse(c1.equals(c2));
		assertFalse(c1.equals(""));
		assertFalse(c1.equals(null));
		
		c1 = new StringLengthConstraint("asdf", true, 5, 10);
		assertFalse(c1.equals(c2));
		assertFalse(c1.hashCode() == c2.hashCode());
		c1 = new StringLengthConstraint("", false, 5, 10);
		assertFalse(c1.equals(c2));
		assertFalse(c1.hashCode() == c2.hashCode());
	}
	
	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return StringLengthConstraint.Builder; }

	@Override
	protected ValueConstraint<?> sampleConstraint() { return new StringLengthConstraint("", false, 3, 5); }
}
