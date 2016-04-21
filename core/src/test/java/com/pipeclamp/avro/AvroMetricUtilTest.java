package com.pipeclamp.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.testng.annotations.Test;

import com.pipeclamp.metrics.MetricDescriptor;
import com.pipeclamp.path.Path;
import com.pipeclamp.test.Person;

public class AvroMetricUtilTest {

  @Test
  public void AvroMetricUtil() {
    
  }

  @Test
  public void allMetricsIn() {
	 
	  Map<Path<GenericRecord, ?>, Map<String, MetricDescriptor>> metrics = AvroMetricUtil.metricsIn(Person.SCHEMA$);
	  
	  assertEquals(metrics.size(), 1);
  }

//  @Test
//  public void childTextFrom() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void collectMetrics() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void hasLocalMetricsSchema() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void hasLocalMetricsField() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void localMetricsInSchema() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void localMetricsInField() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void metricsInSchema() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void metricsInJsonNodeTypeboolean() {
//    throw new RuntimeException("Test not implemented");
//  }
}
