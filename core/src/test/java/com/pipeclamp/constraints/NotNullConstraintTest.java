package com.pipeclamp.constraints;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Collections;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;

/**
 *
 * @author Brian Remedios
 */
public class NotNullConstraintTest {

	@Test
	public void typedErrorFor() {

		Collection<Constraint<?>> vcs = NotNullConstraint.Builder.constraintsFrom(Schema.Type.RECORD, false, Collections.<String,String>emptyMap());

		assertFalse(vcs.isEmpty());

		ValueConstraint<?> constraint = (ValueConstraint<?>)vcs.iterator().next();

		Violation v = constraint.errorFor("asdf");
		assertNull(v);
		
		v = constraint.errorFor(null);
		assertNotNull(v);
	}
}
