package com.pipeclamp.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

public class ByteUtilTest {

  @Test
  public void matchesStart() {
   
	  byte[] prefix		= new byte[] { 1, 2, 3 };
	  byte[] candidate1	= new byte[] { 1, 2, 3, 4, 5};
	  byte[] candidate2	= new byte[] {    2, 3, 4, 5};
	  byte[] candidate3	= new byte[] { 1, 2};
	  byte[] candidate4	= new byte[] {  };
	  byte[] candidate5	= new byte[] { 1, 2, 3, 4, 5, 1, 2, 3};

	  assertTrue(ByteUtil.matchesStart(candidate1, prefix));
	  assertFalse(ByteUtil.matchesStart(candidate2, prefix));
	  assertFalse(ByteUtil.matchesStart(candidate3, prefix));
	  assertFalse(ByteUtil.matchesStart(candidate4, prefix));
	  assertTrue(ByteUtil.matchesStart(candidate5, prefix));
  
  }
}
