package com.pipeclamp.metrics.functions;

import java.math.BigDecimal;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.metrics.collectors.FullCollector;
import com.pipeclamp.metrics.operators.Add;

/**
 * Tracks the sum of all numeric values received.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class Summer<I extends Number> extends AbstractCollectionFunction<I,I> {

	private final Add<I> adder;

	public static final String Id = "sum";

	private Summer(Add<I> theAdder) {
		super(null);

		adder = theAdder;
	}

	@Override
	public Collector<I> createCollector() {	return new FullCollector<I>(predicate()); }

	@Override
	public I compute(Collector<I> collector) {

		I total = adder.zero();

		for (I n : collector.all()) {
			total = adder.add(total, n);
		}
		return total;
	}

	public I subtract(I a, I b) {
		return adder.subtract(a, b);
	}

	public static final Summer<Integer>		IntegerSum = new Summer<Integer>(Add.IntegerAdd);
	public static final Summer<Long>		LongSum = new Summer<Long>(Add.LongAdd);
	public static final Summer<Float>		FloatSum = new Summer<Float>(Add.FloatAdd);
	public static final Summer<Double>		DoubleSum = new Summer<Double>(Add.DoubleAdd);
	public static final Summer<BigDecimal>	BigDecimalSum = new Summer<BigDecimal>(Add.BigDecimalAdd);

	public static final Map<Class<?>, Function<?,?>> SummersByType = ImmutableMap.<Class<?>, Function<?,?>>builder().
		      put(Long.class, LongSum).
		      put(Float.class, FloatSum).
		      put(Double.class, DoubleSum).
		      put(Integer.class, IntegerSum).
		      put(BigDecimal.class, BigDecimalSum).
		      build();
}
