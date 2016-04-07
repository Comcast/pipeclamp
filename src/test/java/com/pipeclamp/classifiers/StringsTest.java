package com.pipeclamp.classifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.testng.annotations.Test;

public class StringsTest {
	
  @Test
  public void testAll() {
	  
	  assertEquals("206", Strings.AreaCode.classify("206 829-9992"));
	  assertEquals("604", Strings.AreaCode.classify("(604) 987-2520"));
	  assertEquals("773", Strings.AreaCode.classify("773.898..9087"));
	  
	  assertNull( Strings.AreaCode.classify("773.898..907"));
	  assertNull( Strings.AreaCode.classify("773.898..90907"));
  }
}
