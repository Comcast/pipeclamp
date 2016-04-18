package com.pipeclamp.constraints;

import static org.junit.Assert.fail;

import org.testng.annotations.Test;

import com.pipeclamp.avro.AvroConfiguration;

public class BasicConstraintFactoryTest {

  @Test
  public void showOn() {
   
	  try {
		  AvroConfiguration.ConstraintFactory.showOn(System.out);
		  	} catch (Exception ex) {
		  		fail(ex.getMessage());
		  	}
  }
}
