package com.pipeclamp.metrics.operators;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

public class CompareTest {

  @Test
  public void max() {
    
	  assertEquals(Integer.valueOf(9), Compare.IntegerCompare.max(5, 9));
	  assertEquals(Long.valueOf(9), Compare.LongCompare.max(5L, 9L));
	  assertEquals(Float.valueOf(9.9f), Compare.FloatCompare.max(5.0f, 9.9f));
	  assertEquals(Double.valueOf(9.2), Compare.DoubleCompare.max(5.1, 9.2));
	  assertEquals(BigDecimal.valueOf(4), Compare.BigDecimalCompare.max(BigDecimal.valueOf(-1), BigDecimal.valueOf(4)));
	  assertEquals("base", Compare.StringCompare.max("ace", "base"));
	  
	  Date now = new Date();
	  Date yesterday = new Date(now.getTime() - TimeUnit.DAYS.toMillis(1));
	  assertEquals(now, Compare.DateCompare.max(now, yesterday));
	  
	  Calendar calNow = GregorianCalendar.getInstance();
	  Calendar calYesterday = GregorianCalendar.getInstance();
	  calYesterday.roll(Calendar.DATE, false);
	  assertEquals(calNow, Compare.CalendarCompare.max(calNow, calYesterday));
  }

  @Test
  public void min() {
	  
	  assertEquals(Integer.valueOf(-1), Compare.IntegerCompare.min(-1, 9));
	  assertEquals(Long.valueOf(5), Compare.LongCompare.min(5L, 9L));
	  assertEquals(Float.valueOf(5f), Compare.FloatCompare.min(5.0f, 9.9f));
	  assertEquals(Double.valueOf(5.1), Compare.DoubleCompare.min(5.1, 9.2));
	  assertEquals(BigDecimal.valueOf(-1), Compare.BigDecimalCompare.min(BigDecimal.valueOf(-1), BigDecimal.valueOf(4)));
	  assertEquals("ace", Compare.StringCompare.min("ace", "base"));
	  
	  Date now = new Date();
	  Date yesterday = new Date(now.getTime() - TimeUnit.DAYS.toMillis(1));
	  assertEquals(yesterday, Compare.DateCompare.min(now, yesterday));
	  
	  Calendar calNow = GregorianCalendar.getInstance();
	  Calendar calYesterday = GregorianCalendar.getInstance();
	  calYesterday.roll(Calendar.DATE, false);
	  assertEquals(calYesterday, Compare.CalendarCompare.min(calNow, calYesterday));
  }
}
