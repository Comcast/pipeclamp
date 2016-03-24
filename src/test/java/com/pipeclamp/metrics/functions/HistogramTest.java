package com.pipeclamp.metrics.functions;

import java.util.Collection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.collectors.HistogramCollector;

public class HistogramTest extends AbstractFunctionTest {

	private static final String[] Sequence = new String[] {
		"1", 
		"2","2", 
		"3", "3", "3", 
		"4", "4", "4", "4"
		};

	@Test
	public void testCollectorß() {

		Collector<String> collector = new HistogramCollector<String>(null);
		
		for (String item : Sequence) {
			collector.add(item);
		}
		
		Collection<String> uniqueValues = collector.all();
		for (String item : uniqueValues) {
			int count = collector.instancesOf(item);
			Assert.assertEquals(count, Integer.valueOf(item).intValue());
		}
		
		Assert.assertEquals(Sequence.length, collector.collected());
	}

}
