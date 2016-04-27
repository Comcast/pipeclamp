package com.pipeclamp.constraints.timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.number.NumericConstraint;

public class TimestampRangeConstraintTest extends AbstractConstraintTest {


	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(
				TimestampRangeConstraint.MIN_TIMESTAMP, "2016-02-01 00:00:00",
				TimestampRangeConstraint.MAX_TIMESTAMP, "2016-03-01 00:00:00");

		Collection<Constraint<?>> vcs = TimestampRangeConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

		assertFalse(vcs.isEmpty());
		assertTrue(paramsByKey.isEmpty());	// they were consumed
		assertTrue(vcs.iterator().next() instanceof TimestampRangeConstraint);
		paramsByKey = asParams(NumericConstraint.MIN_VALUE, 3);

		vcs = TimestampRangeConstraint.Builder.constraintsFrom(Schema.Type.INT, false, paramsByKey);

		Assert.assertNull(vcs);
		Assert.assertEquals(1, paramsByKey.size());
	}

	@Test
	public void parameters() {

		Map<String,String> paramsByKey = asParams(
				TimestampRangeConstraint.MIN_TIMESTAMP, "2016-02-01 00:00:00",
				TimestampRangeConstraint.MAX_TIMESTAMP, "2016-03-01 00:00:00");

		Collection<Constraint<?>> vcs = TimestampRangeConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

		ValueConstraint<?> vc = (ValueConstraint<?>)vcs.iterator().next();
		Map<Parameter<?>, Object> params = vc.parameters();
		assertEquals(2, params.size());
	}

	@Test
	public void typedErrorFor() {

		Map<String,String> paramsByKey = asParams(
				TimestampRangeConstraint.MIN_TIMESTAMP, "2016-02-01 00:00:00",
				TimestampRangeConstraint.MAX_TIMESTAMP, "2016-03-01 00:00:00");

		Collection<Constraint<?>> vcs = TimestampRangeConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

		TimestampRangeConstraint trc = (TimestampRangeConstraint)vcs.iterator().next();
		
		long midMonth = TimestampRangeConstraint.MIN_TIMESTAMP.valueIn("2016-02-15 00:00:00", null);
		Violation violation = trc.typedErrorFor(midMonth);
		assertNull(violation);
		
		long nextMonth = TimestampRangeConstraint.MIN_TIMESTAMP.valueIn("2016-04-01 00:00:00", null);
		violation = trc.typedErrorFor(nextMonth);
		assertNotNull(violation);
	}

	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return TimestampRangeConstraint.Builder; }

	@Override
	protected ValueConstraint<?> sampleConstraint() { return new TimestampRangeConstraint("", false, 1000L, 2000L); }
}
