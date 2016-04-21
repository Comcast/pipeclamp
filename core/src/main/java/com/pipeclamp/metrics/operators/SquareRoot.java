package com.pipeclamp.metrics.operators;

import java.math.BigDecimal;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public interface SquareRoot<I extends Number> {

    I squareRoot(I value);

    SquareRoot<Integer> IntegerSqrt = new SquareRoot<Integer>() {
		public Integer squareRoot(Integer value) { return (int)Math.sqrt(value); }
    };

    SquareRoot<Long> LongSqrt = new SquareRoot<Long>() {
    	public Long squareRoot(Long value) { return (long)Math.sqrt(value); }
    };

    SquareRoot<Float> FloatSqrt = new SquareRoot<Float>() {
    	public Float squareRoot(Float value) { return (float)Math.sqrt(value); }
    };

    SquareRoot<Double> DoubleSqrt = new SquareRoot<Double>() {
    	public Double squareRoot(Double value) { return (double)Math.sqrt(value); }
    };

    SquareRoot<BigDecimal> BigDecimalSqrt = new SquareRoot<BigDecimal>() {
    	public BigDecimal squareRoot(BigDecimal value) { return BigDecimal.valueOf(Math.sqrt(value.doubleValue())); }	// TODO - conversion to double...urgh
    };
}
