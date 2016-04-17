package com.pipeclamp.constraints.string;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.number.NumericConstraint;
import com.pipeclamp.constraints.string.StringLengthConstraint;

public class StringLengthConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(StringLengthConstraint.MIN_LENGTH, 3);

		Collection<ValueConstraint<?>> vc = StringLengthConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		Assert.assertNotNull(vc);
		Assert.assertTrue(paramsByKey.isEmpty());

		paramsByKey = asParams(StringLengthConstraint.MAX_LENGTH, 3);

		vc = NumericConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		Assert.assertNotNull(vc);
		Assert.assertEquals(0, paramsByKey.size());
	}

	@Test
	public void typedErrorFor() {

		StringLengthConstraint slc = new StringLengthConstraint("", true, 5, 10);

		Violation v = slc.errorFor(null);	// nulls ok
		Assert.assertNull(v);

		v = slc.errorFor("billy");
		Assert.assertNull(v);

		v = slc.errorFor("bill");
		Assert.assertNotNull(v);

		v = slc.errorFor("bill bill bill bill");
		Assert.assertNotNull(v);
	}
}
