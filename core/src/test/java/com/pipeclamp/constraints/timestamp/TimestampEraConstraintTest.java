package com.pipeclamp.constraints.timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.annotations.Test;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.string.WordRestriction;
import com.pipeclamp.constraints.string.WordSetConstraint;

public class TimestampEraConstraintTest extends AbstractConstraintTest {

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(TimestampEraConstraint.Era, TimestampRestriction.Future);

		Collection<ValueConstraint<?>> vcs = TimestampEraConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(1, vcs.size());
		
		ValueConstraint<?> vc = vcs.iterator().next();
		assertTrue(vc.getClass() == TimestampEraConstraint.class);
		
	//	assertEquals(paramsByKey, vc.parameters());		 TODO
	}
	
  @Test
  public void typedErrorFor() {
    
	  Map<String,String> paramsByKey = asParams(TimestampEraConstraint.Era, TimestampRestriction.Historical);

	  Collection<ValueConstraint<?>> vcs = TimestampEraConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

	  ValueConstraint<?> vc = vcs.iterator().next();
	  
	  Violation violation = vc.errorFor(System.currentTimeMillis() - 1000);
	  assertNull(violation);
	  
	  violation = vc.errorFor(System.currentTimeMillis() + 1000);
	  assertNotNull(violation);
	  
	  

	  paramsByKey = asParams(TimestampEraConstraint.Era, TimestampRestriction.Future);

	 vcs = TimestampEraConstraint.Builder.constraintsFrom(Schema.Type.LONG, false, paramsByKey);

	 vc = vcs.iterator().next();
	  
	  violation = vc.errorFor(System.currentTimeMillis() - 1000);
	  assertNotNull(violation);
	  
	  violation = vc.errorFor(System.currentTimeMillis() + 1000);
	  assertNull(violation);
  }

	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return TimestampEraConstraint.Builder; }

	@Override
	protected ValueConstraint<?> sampleConstraint() { return new TimestampEraConstraint("", false, TimestampRestriction.Future); }
}
