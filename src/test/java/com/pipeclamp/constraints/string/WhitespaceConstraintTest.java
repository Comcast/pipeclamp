package com.pipeclamp.constraints.string;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;

public class WhitespaceConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(WhitespaceConstraint.NO_LEADING, true);

		Collection<ValueConstraint<?>> vcs = WhitespaceConstraint.Builder.constraintsFrom(Schema.Type.STRING, false, paramsByKey);

		assertNotNull(vcs);
		assertTrue(paramsByKey.isEmpty());

		paramsByKey = asParams(WhitespaceConstraint.NO_TRAILING, true);

		vcs = WhitespaceConstraint.Builder.constraintsFrom(Schema.Type.STRING, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(0, paramsByKey.size());
		
		
		paramsByKey = asParams(WhitespaceConstraint.NO_TRAILING, false);

		vcs = WhitespaceConstraint.Builder.constraintsFrom(Schema.Type.STRING, false, paramsByKey);

		Assert.assertNull(vcs);
	}
	
	@Test
	public void testTypedErrorFor() {
		
		Map<String,String> paramsByKey = asParams(WhitespaceConstraint.NO_LEADING, true);

		Collection<ValueConstraint<?>> vcs = WhitespaceConstraint.Builder.constraintsFrom(Schema.Type.STRING, false, paramsByKey);
		assertNotNull(vcs);
		ValueConstraint<?> vc = vcs.iterator().next();
		
		Violation violation = vc.errorFor("hello");
		assertNull(violation);
		
		violation = vc.errorFor("hello\n");
		assertNull(violation);
		
		violation = vc.errorFor("\thello");
		assertNotNull(violation);
		
		// ========
		
		paramsByKey = asParams(WhitespaceConstraint.NO_TRAILING, true);

		vcs = WhitespaceConstraint.Builder.constraintsFrom(Schema.Type.STRING, false, paramsByKey);
		assertNotNull(vcs);
		vc = vcs.iterator().next();
		
		violation = vc.errorFor("hello");
		assertNull(violation);
		
		violation = vc.errorFor("\thello");
		assertNull(violation);
		
		violation = vc.errorFor("hello\n");
		assertNotNull(violation);
	}
}
