package com.pipeclamp.path;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.test.Country;
import com.pipeclamp.test.Person;

public class ReflectivePathTest {

  @Test
  public void valueVia() {
	  
	  Person person = PeopleBuilder.newPerson("brian", "remedios", 123, new Date(), "a", "br");

	  Path<Person, Country> path = new ReflectivePath<Person, Country>("getPassports[]", "getCountry");
	  Collection<Country> results = path.valuesVia(person);
	  for (Object item : results) {
		  assertTrue(item instanceof Country);
	  }
	  
	  Path<Person, String> path2 = new ReflectivePath<Person, String>("getPassports[]", "getPages[]", "getContents");
	  Collection<String> contents = path2.valuesVia(person);
	  for (String item : contents) {
		  assertTrue(item instanceof String);
	  }
//	  System.out.println(result);
  }
}
