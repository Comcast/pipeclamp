package com.pipeclamp.metrics.functions;

import static com.pipeclamp.metrics.collectors.AbstractCollector.asFinalCollector;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.functions.StdDeviation;

public class StdDeviationTest extends AbstractFunctionTest {

	private static final String[][] Tests = new String[][] {
		{ "0",    "0", "0", "0", "0" },
		{ "2",    "2", "4", "4", "4", "5", "5", "7", "9" },
		};

	@Test
	public void computeInts() {

		for (String[] test : Tests) {
			Integer expectedResult = Integer.parseInt(test[0]);
			List<Integer> nums = intsOf(test);

			Collector<Integer> collector = asFinalCollector(nums);

			Integer result = StdDeviation.IntDev.compute(collector);
			Assert.assertEquals(result, expectedResult);
		}
	}

	@Test
	public void computeLongs() {

		for (String[] test : Tests) {
			Long expectedResult = Long.parseLong(test[0]);
			List<Long> nums = longsOf(test);

			Collector<Long> collector = asFinalCollector(nums);

			Long result = StdDeviation.LongDev.compute(collector);
			Assert.assertEquals(result, expectedResult);
		}
	}
}
