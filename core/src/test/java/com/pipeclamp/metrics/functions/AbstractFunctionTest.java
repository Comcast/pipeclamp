package com.pipeclamp.metrics.functions;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFunctionTest {

	protected static List<Integer> intsOf(String[] nums) {

		List<Integer> ints = new ArrayList<>(nums.length - 1);

		for (int i = 1; i < nums.length; i++) {
			ints.add(Integer.parseInt(nums[i]));
			}
		return ints;
	}

	protected static List<Long> longsOf(String[] nums) {

		List<Long> longs = new ArrayList<>(nums.length - 1);

		for (int i = 1; i < nums.length; i++) {
			longs.add(Long.parseLong(nums[i]));
			}
		return longs;
	}

	protected static List<Float> floatsOf(String[] nums) {

		List<Float> floats = new ArrayList<>(nums.length - 1);

		for (int i = 1; i < nums.length; i++) {
			floats.add(Float.parseFloat(nums[i]));
			}
		return floats;
	}

	protected static List<Double> doublesOf(String[] nums) {

		List<Double> doubles = new ArrayList<>(nums.length - 1);

		for (int i = 1; i < nums.length; i++) {
			doubles.add(Double.parseDouble(nums[i]));
			}
		return doubles;
	}

	protected AbstractFunctionTest() {
		super();
	}

}