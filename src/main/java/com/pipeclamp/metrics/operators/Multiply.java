package com.pipeclamp.metrics.operators;

import java.math.BigDecimal;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public interface Multiply<I extends Number> {

    I multiply(I lhs, I rhs);

    I divide(I lhs, I rhs);

    I divide(I lhs, int rhs);

    Multiply<Integer> IntMult = new Multiply<Integer>() {
		public Integer multiply(Integer lhs, Integer rhs) { return lhs * rhs; }
		public Integer divide(Integer lhs, Integer rhs) { return lhs / rhs; }
		public Integer divide(Integer lhs, int rhs) { return lhs / rhs; }
    };

    Multiply<Long> LongMult  = new Multiply<Long>() {
		public Long multiply(Long lhs, Long rhs) { return lhs * rhs; }
		public Long divide(Long lhs, Long rhs) { return lhs / rhs; }
		public Long divide(Long lhs, int rhs) { return lhs / rhs; }
    };

    Multiply<Float> FloatMult = new Multiply<Float>() {
		public Float multiply(Float lhs, Float rhs) { return lhs * rhs; }
		public Float divide(Float lhs, Float rhs) { return lhs / rhs; }
		public Float divide(Float lhs, int rhs) { return lhs / rhs; }
    };

    Multiply<Double> DoubleMult = new Multiply<Double>() {
		public Double multiply(Double lhs, Double rhs) { return lhs * rhs; }
		public Double divide(Double lhs, Double rhs) { return lhs / rhs; }
		public Double divide(Double lhs, int rhs) { return lhs / rhs; }
    };

    Multiply<BigDecimal> BigDecimalMult = new Multiply<BigDecimal>() {
		public BigDecimal multiply(BigDecimal lhs, BigDecimal rhs) { return lhs.multiply(rhs); }
		public BigDecimal divide(BigDecimal lhs, BigDecimal rhs) { return lhs.divide(rhs); }
		public BigDecimal divide(BigDecimal lhs, int rhs) { return lhs.divide(BigDecimal.valueOf(rhs)); }
    };
}
