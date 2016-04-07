package com.pipeclamp.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

public class StringsTest {
	
  @Test
  public void testAll() {
	  
	  assertTrue(Strings.IsEmpty.apply(null));
	  assertTrue(Strings.IsEmpty.apply(" "));
	  assertTrue(Strings.IsEmpty.apply("\t"));
	  assertTrue(Strings.IsEmpty.apply("\n\r"));
	  
	  assertFalse(Strings.IsEmpty.apply("123"));
	  assertFalse(Strings.IsEmpty.apply(" a   "));
	  
	  // -
	  
	  assertFalse(Strings.IsNotEmpty.apply(null));
	  assertFalse(Strings.IsNotEmpty.apply(" "));
	  assertFalse(Strings.IsNotEmpty.apply("\t"));
	  assertFalse(Strings.IsNotEmpty.apply("\n\r"));
	  
	  assertTrue(Strings.IsNotEmpty.apply("123"));
	  assertTrue(Strings.IsNotEmpty.apply(" a   "));
 
	  // -
	  
	  assertFalse(Strings.IsAlpha.apply(null));
	  assertFalse(Strings.IsAlpha.apply(" "));
	  assertFalse(Strings.IsAlpha.apply("\t"));
	  assertFalse(Strings.IsAlpha.apply("\n\r"));
	  assertFalse(Strings.IsAlpha.apply("abcd 5"));
	  
	  assertFalse(Strings.IsAlpha.apply("123"));
	  assertFalse(Strings.IsAlpha.apply("0"));
	  
	  assertTrue(Strings.IsAlpha.apply("brian"));
	  assertTrue(Strings.IsAlpha.apply("BRIAN"));
	  assertTrue(Strings.IsAlpha.apply("ab"));
	  
	  // -
	  
	  assertFalse(Strings.IsAlphanumeric.apply(null));
	  assertFalse(Strings.IsAlphanumeric.apply(" "));
	  assertFalse(Strings.IsAlphanumeric.apply("\t"));
	  assertFalse(Strings.IsAlphanumeric.apply("\n\r"));
	  assertFalse(Strings.IsAlphanumeric.apply("abcd 5"));
	  
	  assertTrue(Strings.IsAlphanumeric.apply("123"));
	  assertTrue(Strings.IsAlphanumeric.apply("0"));
	  
	  assertTrue(Strings.IsAlphanumeric.apply("brian"));
	  assertTrue(Strings.IsAlphanumeric.apply("BRIAN"));
	  assertTrue(Strings.IsAlphanumeric.apply("ab"));
	  
 // -
	  
	  assertFalse(Strings.IsNumeric.apply(null));
	  assertFalse(Strings.IsNumeric.apply(" "));
	  assertFalse(Strings.IsNumeric.apply("\t"));
	  assertFalse(Strings.IsNumeric.apply("\n\r"));
	  assertFalse(Strings.IsNumeric.apply("abcd 5"));
	  
	  assertTrue(Strings.IsNumeric.apply("123"));
	  assertTrue(Strings.IsNumeric.apply("0"));
	  
	  assertFalse(Strings.IsNumeric.apply("brian"));
	  assertFalse(Strings.IsNumeric.apply("BRIAN"));
	  assertFalse(Strings.IsNumeric.apply("ab"));
  }
}
