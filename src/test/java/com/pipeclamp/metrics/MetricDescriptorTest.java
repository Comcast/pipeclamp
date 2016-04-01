package com.pipeclamp.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.testng.annotations.Test;

public class MetricDescriptorTest {

  @Test
  public void MetricDescriptor() {
    
	  MetricDescriptor md = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
	  
	  assertEquals(md.id, "beatFrequency");
	  assertEquals(md.label, "Beat frequency");
	  assertEquals(md.function, null);
	  assertEquals(md.unitLabel, "Hz");
	  assertEquals(md.description, "The interference resulting from the union of two signals");
  }

  @Test
  public void description() {
	  MetricDescriptor md = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
	  assertEquals(md.description(), "The interference resulting from the union of two signals");
  }

  @Test
  public void equals() {
	  MetricDescriptor md1 = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
	  MetricDescriptor md2 = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
		
	  assertEquals(md1, md2);
	
	  md2 = new MetricDescriptor("beatFrequency", "Beat Frequency", null, "Hz", "The interference resulting from the union of two signals");
		
	  assertNotSame(md1, md2);
  }

  @Test
  public void hashCodeTest() {
	  
	  MetricDescriptor md1 = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
	  MetricDescriptor md2 = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
		
	  assertEquals(md1.hashCode(), md2.hashCode());
	
	  md2 = new MetricDescriptor("beatFrequency", "Beat Frequency", null, "Hz", "The interference resulting from the union of two signals");
		
	  assertNotSame(md1.hashCode(), md2.hashCode());
  }
  
  @Test
  public void toStringTest() {
	  MetricDescriptor md = new MetricDescriptor("beatFrequency", "Beat frequency", null, "Hz", "The interference resulting from the union of two signals");
	  String textForm = md.toString();
	  
	  assertEquals("beatFrequency	Beat frequency	Hz	The interference resulting from the union of two signals", textForm);
  }
}
