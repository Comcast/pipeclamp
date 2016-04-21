package com.pipeclamp.metrics.functions;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.collectors.MinMaxCollector;
import com.pipeclamp.metrics.operators.Compare;

/**
 * Tracks the minimum and maximum values received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class MinMax<I extends Comparable<?>> extends AbstractCollectionFunction<I, Comparable<?>[]> {

	private final Compare<I> comp;

	private static final String MinKey = "min";
	private static final String MaxKey = "max";

	public static final String Id = "min-max";

	public MinMax(Compare<I> theComp) {
		super(null);

		comp = theComp;
	}

	public Collector<I> createCollector() { return new MinMaxCollector<I>(predicate()); }

//	@Override
//	public Collector<I> createCollector() {
//
//		return new Collector<I>() {
//
//			private I min;
//			private I max;
//			private int count = 0;
//
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			@Override
//			public boolean add(I item) {
//
//				if (!qualifies(item)) return false;
//
//				count++;
//
//				if (min == null) {
//					min = item;
//					max = item;
//					return true;
//				}
//
//				if (((Comparable)min).compareTo(item) > 0) min = item;
//				if (((Comparable)max).compareTo(item) < 0) max = item;
//
//				return true;
//			}
//
//
//			@Override
//			public I get(String identifier) {
//				if (MinKey.equals(identifier)) return min;
//				if (MaxKey.equals(identifier)) return max;
//				return null;
//			}
//
//			@Override
//			public int collected() { return count; }
//
//
//			@Override
//			public void clear() {
//				count = 0;
//				min = null;
//				max = null;
//			}
//
//			public int instancesOf(I item) { return -1; }
//			public Collection<I> all() { return null; }
//			public Set<String> classifications() { return Collections.emptySet(); }
//			public int countsOf(String classification) { return 0; }
//			};
//	}

	public Comparable<?>[] compute(Collector<I> collector) {

		return new Comparable[] {
			(Comparable)collector.get(MinKey),
			(Comparable)collector.get(MaxKey)
			};
	}

	public static final MinMax<Date> Date = new MinMax<Date>(Compare.DateCompare);
	public static final MinMax<Long> Long = new MinMax<Long>(Compare.LongCompare);
	public static final MinMax<Float> Float = new MinMax<Float>(Compare.FloatCompare);
	public static final MinMax<Double> Double = new MinMax<Double>(Compare.DoubleCompare);
	public static final MinMax<Integer> Integer = new MinMax<Integer>(Compare.IntegerCompare);
	public static final MinMax<BigDecimal> BigDecimal = new MinMax<BigDecimal>(Compare.BigDecimalCompare);

	public static final Map<Class<?>, Function<?,?>> MinMaxByType = ImmutableMap.<Class<?>, Function<?,?>>builder().
		      put(Date.class, Date).
		      put(Long.class, Long).
		      put(Float.class, Float).
		      put(Double.class, Double).
		      put(Integer.class, Integer).
		      put(BigDecimal.class, BigDecimal).
		      build();
}
