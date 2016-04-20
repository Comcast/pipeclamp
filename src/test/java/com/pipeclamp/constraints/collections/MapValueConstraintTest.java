package com.pipeclamp.constraints.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.avro.AvroConfiguration;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.string.StringLengthConstraint;

public class MapValueConstraintTest extends AbstractConstraintTest {

	@Test
	public void testMapValueConstraintBuilder() {

		Map<String,String> paramsByKey = asParams(
				MapValueConstraint.VALUE_TYPE, "string",
				AbstractMapConstraint.CONSTRAINT_ID, StringLengthConstraint.TypeTag, 
				StringLengthConstraint.MIN_LENGTH, 3);

		Collection<ValueConstraint<?>> vcs = MapValueConstraint.builderWith(AvroConfiguration.ConstraintFactory).constraintsFrom(Type.MAP, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(vcs.size(), 1);
	}

	@Test
	public void typedErrorFor() {
		//    TODO
	}
}
