package com.pipeclamp.constraints.bytes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;

public class BytePrefixConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(BytePrefixConstraint.MATCHER, "GIF");

		Collection<ValueConstraint<?>> vcs = BytePrefixConstraint.Builder.constraintsFrom(Schema.Type.BYTES, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(1, vcs.size());

		ValueConstraint<?> vc = vcs.iterator().next();

		assertEquals(paramsByKey, vc.parameters());
	}

	@Test
	public void typedErrorFor() {

		Map<String,String> paramsByKey = asParams(BytePrefixConstraint.MATCHER, "GIF");

		Collection<ValueConstraint<?>> vcs = BytePrefixConstraint.Builder.constraintsFrom(Schema.Type.BYTES, false, paramsByKey);

		ValueConstraint<?> vc = vcs.iterator().next();

		byte[] data = readResource("throbber.gif");
		Violation v = vc.errorFor(data);
		assertNull(v);

		data = readResource("32px.png");
		v = vc.errorFor(data);
		assertNotNull(v);

		v = vc.errorFor(new byte[] { 0, 1, 2, 3, 4 } );
		assertNotNull(v);
	}
}
