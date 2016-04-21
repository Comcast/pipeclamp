package com.pipeclamp.metrics.functions;

import static com.pipeclamp.metrics.collectors.AbstractCollector.asFinalCollector;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.functions.Averager;

public class AveragerTest extends AbstractFunctionTest {

	private static final String[][] Tests = new String[][] {
		{ "0",   "0", "0", "0", "0" },
		{ "5",   "0", "10" },
		{ "0",   "-3", "3" },
		{ "2",   "1", "3" },
		};

	@Test
	public void computeInts() {

		for (String[] test : Tests) {
			Integer expectedResult = Integer.parseInt(test[0]);
			List<Integer> nums = intsOf(test);
			
			Collector<Integer> collector = asFinalCollector(nums);
			
			Integer result = Averager.IntAvg.compute(collector);
			Assert.assertEquals(result, expectedResult);
		}
	}

	@Test
	public void computeFloats() {

		for (String[] test : Tests) {
			Float expectedResult = Float.parseFloat(test[0]);
			List<Float> nums = floatsOf(test);

			Collector<Float> collector = asFinalCollector(nums);
			
			Float result = Averager.FloatAvg.compute(collector);
			Assert.assertEquals(result, expectedResult);
		}
	}
}
