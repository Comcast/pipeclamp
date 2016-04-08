package com.pipeclamp.metrics.operators;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.testng.annotations.Test;

public class MultiplyTest {

  @Test
  public void divideII() {
   // TODO
  }

  @Test
  public void divideIint() {
	// TODO
  }

  @Test
  public void multiply() {
    
	  assertEquals(Integer.valueOf(45), Multiply.IntMult.multiply(5, 9));
	  assertEquals(Long.valueOf(45), Multiply.LongMult.multiply(5L, 9L));
	  assertEquals(Float.valueOf(49.5f), Multiply.FloatMult.multiply(5.0f, 9.9f));
	  assertEquals("45.0", Multiply.DoubleMult.multiply(5.0, 9.0).toString());
	  assertEquals(BigDecimal.valueOf(-4), Multiply.BigDecimalMult.multiply(BigDecimal.valueOf(-1), BigDecimal.valueOf(4)));
  }
}
