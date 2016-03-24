package com.pipeclamp.constraints.number;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.number.NumericConstraint;

public class NumericConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams("min=3");

		Collection<ValueConstraint<?>> vc = NumericConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		Assert.assertNotNull(vc);
		Assert.assertTrue(paramsByKey.isEmpty());

		paramsByKey = asParams("minValue=3");

		vc = NumericConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		Assert.assertNull(vc);
		Assert.assertEquals(1, paramsByKey.size());
	}

	@Test
	public void errorFor() {

		NumericConstraint nc = new NumericConstraint("", false, 1, true, 5, true);

		Violation v = nc.errorFor(null);
		Assert.assertNotNull(v);

		v = nc.errorFor(3);
		Assert.assertNull(v);

		v = nc.errorFor(31);
		Assert.assertNotNull(v);

		v = nc.errorFor(-1);
		Assert.assertNotNull(v);
	}

	@Test
	public void errorFor2() {

		NumericConstraint nc = new NumericConstraint("", true, -11, true, 55, true);

		Violation v = nc.errorFor(null);
		Assert.assertNull(v);

		v = nc.errorFor(3);
		Assert.assertNull(v);

		v = nc.errorFor(131);
		Assert.assertNotNull(v);

		v = nc.errorFor(-1);
		Assert.assertNull(v);

		v = nc.errorFor(-91);
		Assert.assertNotNull(v);
	}
}
