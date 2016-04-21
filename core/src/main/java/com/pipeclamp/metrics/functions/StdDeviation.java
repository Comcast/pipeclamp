package com.pipeclamp.metrics.functions;

import static com.pipeclamp.metrics.collectors.AbstractCollector.asFinalCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.collectors.FullCollector;
import com.pipeclamp.metrics.operators.Multiply;
import com.pipeclamp.metrics.operators.SquareRoot;
/**
 *  Computes the standard deviation for the numeric values received.
 *
 *  Taken from https://en.wikipedia.org/wiki/Standard_deviation
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class StdDeviation<I extends Number> extends AbstractCollectionFunction<I,I> {

	private final Summer<I> summer;
	private final Multiply<I> multiplier;
	private final Averager<I> averager;
	private final SquareRoot<I> rooter;

	public static final String Id = "std-dev";

	private StdDeviation(Summer<I> theSummer, Averager<I> theAverager, Multiply<I> theMult, SquareRoot<I> theRoot) {
		super(null);

		summer = theSummer;
		averager = theAverager;
		multiplier = theMult;
		rooter = theRoot;
	}

	@Override
	public Collector<I> createCollector() {	return new FullCollector<I>(predicate());	}

	@Override
	public I compute(Collector<I> collector) {

		I avg = averager.compute(collector);

		List<I> deviations = new ArrayList<I>(collector.collected());
		for (I value : collector.all()) {
			deviations.add( summer.subtract(avg, value));
		}
		List<I> squares = new ArrayList<I>(collector.collected());
		for (I dev : deviations) {
			squares.add( multiplier.multiply(dev, dev));
		}

		I variance = averager.compute( asFinalCollector(squares));
		return rooter.squareRoot(variance);
	}

	public static final StdDeviation<Integer> IntDev = new StdDeviation<Integer>(Summer.IntegerSum, Averager.IntAvg, Multiply.IntMult, SquareRoot.IntegerSqrt);

	public static final StdDeviation<Long> LongDev = new StdDeviation<Long>(Summer.LongSum, Averager.LongAvg, Multiply.LongMult, SquareRoot.LongSqrt);

	public static final StdDeviation<Float> FloatDev = new StdDeviation<Float>(Summer.FloatSum, Averager.FloatAvg, Multiply.FloatMult, SquareRoot.FloatSqrt);

	public static final StdDeviation<Double> DoubleDev = new StdDeviation<Double>(Summer.DoubleSum, Averager.DoubleAvg, Multiply.DoubleMult, SquareRoot.DoubleSqrt);

	public static final Map<Class<?>, Function<?,?>> StdDeviationByType = ImmutableMap.<Class<?>, Function<?,?>>builder().
		      put(Long.class, LongDev).
		      put(Float.class, FloatDev).
		      put(Double.class, DoubleDev).
		      put(Integer.class, IntDev).
		      build();
}
