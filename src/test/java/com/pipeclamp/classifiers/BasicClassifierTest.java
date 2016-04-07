package com.pipeclamp.classifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

public class BasicClassifierTest {


  @Test
  public void classify() {
   
	  BasicClassifier<String> bc = new BasicClassifier<String>("a", "b");
	  
	  try {
		  String clasz = bc.classify("hello world");
	  	} catch (RuntimeException re) {
	  		assertTrue(re.getMessage().contains("not implemented"));
	  		}
  }

  @Test
  public void description() {
    
	  BasicClassifier<String> bc = new BasicClassifier<String>("a", "b");
	  assertEquals("b", bc.description());
	  assertFalse("a".equals( bc.description() ));
  }

  @Test
  public void equals() {
    
	  BasicClassifier<String> bc1 = new BasicClassifier<String>("a", "b");
	  BasicClassifier<String> bc2 = new BasicClassifier<String>("a", "b");
	  
	  assertTrue(bc1.equals(bc2));
	  assertTrue(bc2.equals(bc1));
  }

  @Test
  public void hashCodeTest() {
	  
	  BasicClassifier<String> bc1 = new BasicClassifier<String>("a", "b");
	  BasicClassifier<String> bc2 = new BasicClassifier<String>("a", "b");
	  
	  assertEquals(bc1.hashCode(), bc2.hashCode());
  }

  @Test
  public void id() {
	  
	  BasicClassifier<String> bc = new BasicClassifier<String>("a", "b");
	  assertEquals("a", bc.id());
	  assertFalse("b".equals( bc.id() ));
  }
  
}
