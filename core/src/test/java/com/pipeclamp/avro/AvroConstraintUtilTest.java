package com.pipeclamp.avro;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.testng.annotations.Test;

import com.pipeclamp.api.Constraint;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.path.Path;

public class AvroConstraintUtilTest {

  @Test
  public void constraintsIn() {
    
	  byte[] schemaBytes = AbstractConstraintTest.readResource("person.avsc");
	  Schema sc = AvroUtil.parseSchema(new String(schemaBytes));
	  
	  Map<Path<GenericRecord, ?>, Collection<Constraint<?>>> constraints = AvroConstraintUtil.constraintsIn(sc, true, AvroConfiguration.ConstraintFactory);
	  assertEquals(10, constraints.size());
  }
  
  @Test
  public void constraintsIn2() {
    
	  byte[] schemaBytes = AbstractConstraintTest.readResource("GoodBadMix.avsc");
	  Schema sc = AvroUtil.parseSchema(new String(schemaBytes));
	  
	  Map<Path<GenericRecord, ?>, Collection<Constraint<?>>> constraints = AvroConstraintUtil.constraintsIn(sc, true, AvroConfiguration.ConstraintFactory);
	  assertEquals(3, constraints.size());
  }
}
