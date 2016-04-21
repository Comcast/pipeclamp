package com.pipeclamp.metrics.functions;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Collector;
import com.pipeclamp.metrics.functions.MinMax;

public class MinMaxTest extends AbstractFunctionTest {

	private static final String[][] Mins = new String[][] {
		{ "0",   "0", "0", "0", "0" },
		{ "0",   "0", "10" },
		{ "-3",   "-3", "3" },
		{ "1",   "1", "3" },
		{ "-5000",   "-999", "8", "9", "0", "-5000" },
		{ "1",   "1", "3", "44", "33" },
		};

	private static final String[][] Maxes = new String[][] {
		{ "0",   "0", "0", "0", "0" },
		{ "10",   "0", "10" },
		{ "3",   "-3", "3" },
		{ "3",   "1", "3" },
		{ "9",   "-999", "8", "9", "0", "-5000" },
		{ "44",   "1", "3", "44", "33" },
		};

	@SuppressWarnings("rawtypes")
	@Test
	public void computeInts() {

		for (String[] test : Maxes) {
			Integer expectedResult = Integer.parseInt(test[0]);
			List<Integer> nums = intsOf(test);

			Collector<Integer> collector = MinMax.Integer.createCollector();
			for (Integer i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Integer.compute(collector);
			Assert.assertEquals(result[1], expectedResult);
		}

		for (String[] test : Mins) {
			Integer expectedResult = Integer.parseInt(test[0]);
			List<Integer> nums = intsOf(test);
			
			Collector<Integer> collector = MinMax.Integer.createCollector();
			for (Integer i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Integer.compute(collector);
			Assert.assertEquals(result[0], expectedResult);
		}
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void computeFloats() {

		for (String[] test : Maxes) {
			Float expectedResult = Float.parseFloat(test[0]);
			List<Float> nums = floatsOf(test);

			Collector<Float> collector = MinMax.Float.createCollector();
			for (Float i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Float.compute(collector);
			Assert.assertEquals(result[1], expectedResult);
		}

		for (String[] test : Mins) {
			Float expectedResult = Float.parseFloat(test[0]);
			List<Float> nums = floatsOf(test);
			
			Collector<Float> collector = MinMax.Float.createCollector();
			for (Float i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Float.compute(collector);
			Assert.assertEquals(result[0], expectedResult);
		}
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void computeDoubles() {

		for (String[] test : Maxes) {
			Double expectedResult = Double.parseDouble(test[0]);
			List<Double> nums = doublesOf(test);
			
			Collector<Double> collector = MinMax.Double.createCollector();
			for (Double i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Double.compute(collector);
			Assert.assertEquals(result[1], expectedResult);
		}

		for (String[] test : Mins) {
			Double expectedResult = Double.parseDouble(test[0]);
			List<Double> nums = doublesOf(test);
			
			Collector<Double> collector = MinMax.Double.createCollector();
			for (Double i : nums) collector.add(i);
			
			Comparable[] result = MinMax.Double.compute(collector);
			Assert.assertEquals(result[0], expectedResult);
		}
	}
}
