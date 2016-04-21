package com.pipeclamp.metrics.collectors;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.Test;

import com.pipeclamp.constraints.string.RegexDescriptor;

public class RegexCollectorTest {

  @Test
  public void add() {
    
	  RegexCollector collector = new RegexCollector(null);
	  collector.register("emails", RegexDescriptor.EMailAddress.regex);
	  
	  collector.add("brian@test.com");
	  collector.add("not an email.com");
	  collector.add("brian @test.com");
	  
	  assertEquals(1, collector.countsOf("emails"));
	  
	  collector.clear();
	  assertEquals(0, collector.countsOf("emails"));
  }

}
