package com.pipeclamp.path;

import java.util.Collection;
import java.util.Date;

import org.apache.avro.generic.GenericRecord;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.avro.SimpleAvroPath;
import com.pipeclamp.path.Path;
import com.pipeclamp.test.Country;
import com.pipeclamp.test.Person;

public class AvroPathTest {

  @Test
  public void valueVia() {
	  
	  Person person = PeopleBuilder.newPerson("brian", "remedios", 123, new Date(), "a", "br");

	  Path<GenericRecord, Country> path = new SimpleAvroPath<Country>("passports[]", "country");
	  Collection<Country> results = path.valuesVia(person);
	  for (Object item : results) {
		  Assert.assertTrue(item instanceof Country);
	  }
	  
	  Path<GenericRecord, String> path2 = new SimpleAvroPath<String>("passports[]", "pages[]", "contents");
	  Collection<String> contents = path2.valuesVia(person);
	  for (String item : contents) {
		  Assert.assertTrue(item instanceof String);
	  }
//	  System.out.println(result);
  }
}
