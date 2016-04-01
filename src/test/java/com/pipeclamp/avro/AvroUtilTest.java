package com.pipeclamp.avro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.testng.annotations.Test;

import com.pipeclamp.test.Person;

public class AvroUtilTest {

  @Test
  public void allowsNull() {
	  
	assertFalse(  AvroUtil.allowsNull(Person.SCHEMA$) );
	
	Field field = Person.SCHEMA$.getField("birthdate");
	assertTrue( AvroUtil.allowsNull( field.schema() ));
	
	field = Person.SCHEMA$.getField("lastname");
	assertFalse( AvroUtil.allowsNull( field.schema() ));
	
	field = Person.SCHEMA$.getField("latitude");
	assertTrue( AvroUtil.allowsNull( field.schema() ));
  }

//  @Test
//  public void childIntNodeAsString() {
//    throw new RuntimeException("Test not implemented");
//  }

  @Test
  public void defaultFor() {
	  
	  Field field = Person.SCHEMA$.getField("bellybuttons");
	  assertEquals("1", AvroUtil.defaultFor( field ));
	  
	  field = Person.SCHEMA$.getField("latitude");
	  assertEquals(null, AvroUtil.defaultFor( field ));
  }

  @Test
  public void denotesPrimitiveValue() {
    
	  Field field = Person.SCHEMA$.getField("bellybuttons");
	  assertTrue( AvroUtil.denotesPrimitiveValue( field.schema() ));
	  
	  assertFalse( AvroUtil.denotesPrimitiveValue(Person.SCHEMA$));
  }

  @Test
  public void isNullableSingleTypeField() {
	  
	  Field field = Person.SCHEMA$.getField("bellybuttons");
	  assertFalse( AvroUtil.isNullableSingleType( field ));
	  
	  field = Person.SCHEMA$.getField("latitude");
	  assertTrue( AvroUtil.isNullableSingleType( field ));
  }

//  @Test
//  public void isNullableSingleTypeListSchema() {
//    throw new RuntimeException("Test not implemented");
//  }

  @Test
  public void isRecordType() {
	  
	  assertTrue( AvroUtil.isRecordType( Person.SCHEMA$ ));
	  assertFalse( AvroUtil.isRecordType( Person.SCHEMA$.getField("latitude").schema() ));
  }

//  @Test
//  public void nonNullSchemaIn() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void nullableSingleTypeIn() {
//    throw new RuntimeException("Test not implemented");
//  }

  @Test
  public void primaryTypeIn() {
   
	  assertEquals(Type.RECORD, AvroUtil.primaryTypeIn(Person.SCHEMA$));
	  assertEquals(Type.INT, AvroUtil.primaryTypeIn(Person.SCHEMA$.getField("bellybuttons").schema()));
  }

}
