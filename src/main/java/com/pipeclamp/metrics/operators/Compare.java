package com.pipeclamp.metrics.operators;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public interface Compare<I extends Comparable<?>> {

    I max(I lhs, I rhs);

    I min(I lhs, I rhs);

    Compare<Integer> IntegerCompare = new Compare<Integer>() {
		public Integer min(Integer lhs, Integer rhs) { return lhs > rhs ? rhs : lhs; }
		public Integer max(Integer lhs, Integer rhs) { return lhs < rhs ? rhs : lhs; }
    };

    Compare<Long> LongCompare = new Compare<Long>() {
    	public Long min(Long lhs, Long rhs) { return lhs > rhs ? rhs : lhs; }
		public Long max(Long lhs, Long rhs) { return lhs < rhs ? rhs : lhs; }
    };

    Compare<Float> FloatCompare = new Compare<Float>() {
    	public Float min(Float lhs, Float rhs) { return lhs > rhs ? rhs : lhs; }
		public Float max(Float lhs, Float rhs) { return lhs < rhs ? rhs : lhs; }
    };

    Compare<Double> DoubleCompare = new Compare<Double>() {
    	public Double min(Double lhs, Double rhs) { return lhs > rhs ? rhs : lhs; }
		public Double max(Double lhs, Double rhs) { return lhs < rhs ? rhs : lhs; }
    };

    Compare<BigDecimal> BigDecimalCompare = new Compare<BigDecimal>() {
    	public BigDecimal min(BigDecimal lhs, BigDecimal rhs) { return lhs.compareTo(rhs) < 0 ? rhs : lhs; }
		public BigDecimal max(BigDecimal lhs, BigDecimal rhs) { return lhs.compareTo(rhs) > 0 ? rhs : lhs; }
    };

    Compare<Date> DateCompare = new Compare<Date>() {
		public Date min(Date lhs, Date rhs) { return lhs.before(rhs) ? lhs : rhs; }
		public Date max(Date lhs, Date rhs) { return lhs.after(rhs) ? lhs : rhs; }
    };

    Compare<Calendar> CalendarCompare = new Compare<Calendar>() {
		public Calendar min(Calendar lhs, Calendar rhs) { return lhs.before(rhs) ? lhs : rhs; }
		public Calendar max(Calendar lhs, Calendar rhs) { return lhs.after(rhs) ? lhs : rhs; }
    };

    Compare<String> StringCompare = new Compare<String>() {
		public String min(String lhs, String rhs) { return lhs.compareTo(rhs) < 0 ? lhs : rhs; }
		public String max(String lhs, String rhs) { return lhs.compareTo(rhs) > 0 ? lhs : rhs; }
    };
}
