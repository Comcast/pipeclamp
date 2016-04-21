package com.pipeclamp.constraints.string;

import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

public class RegexDescriptorTest {

  @Test
  public void matches() {
   
	  for (RegexDescriptor rd : RegexDescriptor.ALL.values()) {
		  assertTrue(rd.matches(rd.example));
	  }
  }
}
