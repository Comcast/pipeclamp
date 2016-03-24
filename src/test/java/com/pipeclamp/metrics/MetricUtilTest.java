package com.pipeclamp.metrics;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.junit.Test;
import org.testng.Assert;

import com.pipeclamp.avro.AvroMetricUtil;
import com.pipeclamp.avro.AvroUtil;
import com.pipeclamp.avro.QAUtil;
import com.pipeclamp.path.Path;

public class MetricUtilTest {

	public static Schema schema1;
	public static Schema schema2;
	
	static {
		try {
		    URL url = QAUtil.class.getClassLoader().getResource("PlaybackSessionEventSchema.avsc");
		    schema1 = AvroUtil.schemaFrom(url);
			
		    url = QAUtil.class.getClassLoader().getResource("person.avsc");
		    schema2 = AvroUtil.schemaFrom(url);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testMetricExtraction1() {

		Map<Path<GenericRecord, ?>, Map<String, MetricDescriptor>> metrics = AvroMetricUtil.metricsIn(schema1);
		
		Assert.assertFalse(metrics.isEmpty());
		
		for (Entry<Path<GenericRecord, ?>, Map<String, MetricDescriptor>> entry : metrics.entrySet()) {
			System.out.println(entry.getKey());
//			for (MetricDescriptor md : entry.getValue()) {
//				System.out.println("\t" + md);
//			}
		}

	}
	
	
	@Test
	public void testMetricExtraction2() {

		Map<Path<GenericRecord, ?>, Map<String, MetricDescriptor>> metrics = AvroMetricUtil.metricsIn(schema2);
		
		Assert.assertFalse(metrics.isEmpty());
		
		for (Entry<Path<GenericRecord, ?>, Map<String, MetricDescriptor>> entry : metrics.entrySet()) {
			System.out.println(entry.getKey());
//			for (MetricDescriptor md : entry.getValue()) {
//				System.out.println("\t" + md);
//			}
		}

	}

}
