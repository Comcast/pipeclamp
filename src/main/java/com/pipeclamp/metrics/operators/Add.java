package com.pipeclamp.metrics.operators;

import java.math.BigDecimal;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public interface Add<I extends Number> {

	I zero();

    I add(I lhs, I rhs);

//    T add(T lhs, int rhs);

    I subtract(I lhs, I rhs);

    Add<Integer> IntegerAdd = new Add<Integer>() {
		public Integer zero() { return Integer.valueOf(0); }
		public Integer add(Integer lhs, Integer rhs) { return lhs + rhs; }
		public Integer subtract(Integer lhs, Integer rhs) { return lhs - rhs; }

//		public Integer add(Integer lhs, int rhs) { return lhs + rhs; }
    };

    Add<Long> LongAdd = new Add<Long>() {
		public Long zero() { return Long.valueOf(0); }
		public Long add(Long lhs, Long rhs) { return lhs + rhs; }
		public Long subtract(Long lhs, Long rhs) { return lhs - rhs; }

//		public Long add(Long lhs, int rhs) { return lhs + rhs; }
    };

    Add<Float> FloatAdd = new Add<Float>() {
		public Float zero() { return Float.valueOf(0); }
		public Float add(Float lhs, Float rhs) { return lhs + rhs; }
		public Float subtract(Float lhs, Float rhs) { return lhs - rhs; }

//		public Float add(Float lhs, int rhs) { return lhs + rhs; }
    };

    Add<Double> DoubleAdd = new Add<Double>() {
		public Double zero() { return Double.valueOf(0); }
		public Double add(Double lhs, Double rhs) { return lhs + rhs; }
		public Double subtract(Double lhs, Double rhs) { return lhs - rhs; }

//		public Double add(Double lhs, int rhs) { return lhs + rhs; }
    };

    Add<BigDecimal> BigDecimalAdd = new Add<BigDecimal>() {
		public BigDecimal zero() { return BigDecimal.ZERO; }
		public BigDecimal add(BigDecimal lhs, BigDecimal rhs) { return lhs.add(rhs); }
		public BigDecimal subtract(BigDecimal lhs, BigDecimal rhs) { return lhs.subtract(rhs); }

//		public BigDecimal add(BigDecimal lhs, int rhs) { return lhs.add(BigDecimal.valueOf(rhs)); }
    };
}
