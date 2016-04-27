package com.pipeclamp.constraints.bytes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;

public class BytePrefixConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(BytePrefixConstraint.MATCHERS, "GIF");

		Collection<Constraint<?>> vcs = BytePrefixConstraint.Builder.constraintsFrom(Schema.Type.BYTES, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(1, vcs.size());

		ValueConstraint<?> vc = (ValueConstraint<?>)vcs.iterator().next();

	//	assertEquals(paramsByKey, vc.parameters());	TODO
	}

	@Test
	public void typedErrorFor() {

		Map<String,String> paramsByKey = asParams(BytePrefixConstraint.MATCHERS, "GIF");

		Collection<Constraint<?>> vcs = BytePrefixConstraint.Builder.constraintsFrom(Schema.Type.BYTES, false, paramsByKey);

		ValueConstraint<?> vc = (ValueConstraint<?>)vcs.iterator().next();

		byte[] data = readResource("throbber.gif");
		Violation v = vc.errorFor(data);
		assertNull(v);

		data = readResource("32px.png");
		v = vc.errorFor(data);
		assertNotNull(v);

		v = vc.errorFor(new byte[] { 0, 1, 2, 3, 4 } );
		assertNotNull(v);
	}
	
	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return BytePrefixConstraint.Builder; }

	@Override
	protected ValueConstraint<?> sampleConstraint() {
		
		Map<String,String> paramsByKey = asParams(BytePrefixConstraint.MATCHERS, "GIF");
		Collection<Constraint<?>> vcs = BytePrefixConstraint.Builder.constraintsFrom(Schema.Type.BYTES, false, paramsByKey);
		return (ValueConstraint<?>)vcs.iterator().next();
	}
}
