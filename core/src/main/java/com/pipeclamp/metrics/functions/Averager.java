package com.pipeclamp.metrics.functions;

import java.math.BigDecimal;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.collectors.FullCollector;
import com.pipeclamp.metrics.operators.Multiply;
/**
 * Computes the running average of the numeric values received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class Averager<I extends Number> extends AbstractCollectionFunction<I,I> {

	private final Summer<I> summer;
	private final Multiply<I> multiplier;

	public static final String Id = "average";

	public Averager(Summer<I> theSummer, Multiply<I> theMult) {
		super(null);

		summer = theSummer;
		multiplier = theMult;
	}

	@Override
	public I compute(Collector<I> collector) {

		I sum = summer.compute(collector);

		return multiplier.divide(sum, collector.collected());
	}

	public static final Averager<Integer>	IntAvg = new Averager<Integer>(Summer.IntegerSum, Multiply.IntMult);
	public static final Averager<Long>		LongAvg = new Averager<Long>(Summer.LongSum, Multiply.LongMult);
	public static final Averager<Float>		FloatAvg = new Averager<Float>(Summer.FloatSum, Multiply.FloatMult);
	public static final Averager<Double>	DoubleAvg = new Averager<Double>(Summer.DoubleSum, Multiply.DoubleMult);
	public static final Averager<BigDecimal> BigDecimalAvg = new Averager<BigDecimal>(Summer.BigDecimalSum, Multiply.BigDecimalMult);

	public static final Map<Class<?>, Function<?,?>> AveragersByType = ImmutableMap.<Class<?>, Function<?,?>>builder().
		      put(Integer.class, IntAvg).
		      put(Long.class, LongAvg).
		      put(Float.class, FloatAvg).
		      put(Double.class, DoubleAvg).
		      put(BigDecimal.class, BigDecimalAvg).
		      build();

	@Override
	public Collector<I> createCollector() { return new FullCollector<I>(predicate()); }

//	public static void main(String[] args) {
//
//		Averager<Integer> avg = IntAvg;
//
//		Predicate<Integer> pred = new Predicate<Integer>() {
//			public boolean apply(Integer num) {
//				return num > 1;
//			}
//		};
//
//		Averager<Integer> newOne = (Averager<Integer>)avg.withPredicate(pred);
//
//		System.out.println();
//	}
}
