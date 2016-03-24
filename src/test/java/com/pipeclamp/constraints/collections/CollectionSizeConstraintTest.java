package com.pipeclamp.constraints.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.collections.CollectionSizeConstraint;

public class CollectionSizeConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams("minItems=3", "maxItems=66", "MAX_ITEMS=99");

		Collection<ValueConstraint<?>> vcs = CollectionSizeConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		ValueConstraint vc = vcs.iterator().next();

		Assert.assertNotNull(vc);
		Assert.assertEquals(1, paramsByKey.size());
	}

  @Test
  public void typedErrorFor() {

	  Map<String,String> paramsByKey = asParams("minItems=3", "maxItems=5", "MAX_ITEMS=99");

		Collection<ValueConstraint<?>> vcs = CollectionSizeConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		ValueConstraint<?> vc = vcs.iterator().next();

		Violation v = vc.errorFor(Arrays.asList("1", "2", "3", "4"));
		Assert.assertNull(v);

		v = vc.errorFor(Arrays.asList("1", "2"));
		Assert.assertNotNull(v);

		v = vc.errorFor(Arrays.asList("1", "2", "3", "4", "5", "6"));
		Assert.assertNotNull(v);
  }
}
