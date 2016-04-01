package com.pipeclamp.params;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.testng.annotations.Test;

public class IntegerRangeParameterTest {

  @Test
  public void IntegerRangeParameter() {
	  
	  IntegerRangeParameter ipr = new IntegerRangeParameter("a", "Aye", " ");
	  assertEquals("a", ipr.id());
	  assertEquals("Aye", ipr.description());
  }

  @Test
  public void valueInTest() {
    
	  IntegerRangeParameter ipr = new IntegerRangeParameter("a", "Aye", " ");
	  Integer[] range = ipr.valueIn("5 19", null);
	  
	  assertNotNull(range);
	  assertEquals(2, range.length);
	  assertEquals(5, range[0].intValue());
	  assertEquals(19, range[1].intValue());
	  
	  range = ipr.valueIn("", null);
	  assertNull(range);
	  
	  range = ipr.valueIn("  19", null);
	  assertNull(range);
	  
	  
	  range = ipr.valueIn("4 6 9", null);
	  assertNull(range);
  }
}
