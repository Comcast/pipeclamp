package com.pipeclamp.params;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.avro.Schema.Type;
import org.testng.annotations.Test;

public class NumberParameterTest {

  @Test
  public void valueIn() {
    NumberParameter np = new NumberParameter("n", "en");
    
    Number result = np.valueIn("five", Type.INT);
    assertNull(result);
    
    result = np.valueIn("5", Type.INT);
    assertEquals(Integer.class, result.getClass());
    assertEquals(5, result.intValue());
    
    result = np.valueIn("5", Type.LONG);
    assertEquals(Long.class, result.getClass());
    assertEquals(5, result.longValue());
    
    result = np.valueIn("5", Type.FLOAT);
    assertEquals(Float.class, result.getClass());
    assertEquals("5.0", result.toString());
    
    result = np.valueIn("5", Type.DOUBLE);
    assertEquals(Double.class, result.getClass());
    assertEquals("5.0", result.toString());
  }
}
