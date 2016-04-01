package com.pipeclamp.params;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.testng.annotations.Test;

public class ByteArrayParameterTest {

  @Test
  public void asByteArray() {
    	ByteArrayParameter bap = new ByteArrayParameter("a", "aye", " ");
    	
    	byte[][] values = bap.valueIn("1", null);
    	assertNull(values);
    	
    	values = bap.valueIn("1 4", null);
    	assertNull(values);
    	
    	values = bap.valueIn("e  7", null);
    	assertNull(values);
    	
    	values = bap.valueIn("11 22 44", null);
    	assertEquals(3, values.length);
  }

}
