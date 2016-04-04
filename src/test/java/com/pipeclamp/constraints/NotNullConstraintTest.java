package com.pipeclamp.constraints;



import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.NotNullConstraint;

/**
 * 
 * @author Brian Remedios
 */
public class NotNullConstraintTest {

	@Test
	public void typedErrorFor() {

		Collection<ValueConstraint<?>> vcs = NotNullConstraint.Builder.constraintsFrom(Schema.Type.RECORD, false, null);

		assertFalse(vcs.isEmpty());

		ValueConstraint<?> constraint = vcs.iterator().next();

		Violation v = constraint.errorFor("asdf");
		assertNull(v);
		
		v = constraint.errorFor(null);
		assertNotNull(v);
	}
}
