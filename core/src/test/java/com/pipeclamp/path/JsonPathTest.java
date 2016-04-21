package com.pipeclamp.path;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.ReadContext;
import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.path.JsonPath;
import com.pipeclamp.path.Path;
import com.pipeclamp.test.Country;
import com.pipeclamp.test.Person;

public class JsonPathTest {

  @Test
  public void valueVia() {
	  
	  Person person = PeopleBuilder.newPerson("brian", "remedios", 123, new Date(), "a", "br");

	  String json = person.toString();
	  ReadContext context = com.jayway.jsonpath.JsonPath.parse(json);
	  
	  Path<ReadContext, Country> path = new JsonPath<Country>("$.passports[*].country", true, false);
	  Collection<Country> results = path.valuesVia(context);
	  for (Object item : results) {
		  Assert.assertTrue(item instanceof String);
	  }
	  
	  Path<ReadContext, String> path2 = new JsonPath<String>("$.passports[*].pages[*].contents", true, false);
	  Collection<String> contents = path2.valuesVia(context);
	  for (String item : contents) {
		  Assert.assertTrue(item instanceof String);
	  }
//	  System.out.println(result);
  }
}
