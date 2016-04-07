package com.pipeclamp.predicates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.common.base.Predicate;

public class NumbersTest {

	@Test
	public void testOthers() {
		
		assertTrue(Numbers.IsZero.apply(0));
		assertFalse(Numbers.IsZero.apply(null));
		assertFalse(Numbers.IsZero.apply(1));
		assertFalse(Numbers.IsZero.apply(-1));
		
		assertTrue(Numbers.IsEven.apply(0));
		assertFalse(Numbers.IsEven.apply(null));
		assertTrue(Numbers.IsEven.apply(96));
		assertFalse(Numbers.IsEven.apply(7));
		
		assertTrue(Numbers.IsOdd.apply(5));
		assertFalse(Numbers.IsOdd.apply(null));
		assertTrue(Numbers.IsOdd.apply(-19));
		assertFalse(Numbers.IsOdd.apply(8));
		
		assertTrue(Numbers.IsPositive.apply(5));
		assertFalse(Numbers.IsPositive.apply(null));
		assertTrue(Numbers.IsPositive.apply(9999));
		assertFalse(Numbers.IsPositive.apply(-8));
		
		assertTrue(Numbers.IsNegative.apply(-1));
		assertFalse(Numbers.IsNegative.apply(null));
		assertTrue(Numbers.IsNegative.apply(-19));
		assertFalse(Numbers.IsNegative.apply(23));
	}
  @Test
  public void inRange() {
    
	  Predicate<Number> range = Numbers.inRange(1, 5);
	  
	  assertTrue(range.apply(1));
	  assertTrue(range.apply(3));
	  assertTrue(range.apply(5));
	  
	  assertFalse(range.apply(null));
	  assertFalse(range.apply(0));
	  assertFalse(range.apply(15));
  }
}
