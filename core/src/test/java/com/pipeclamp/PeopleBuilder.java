package com.pipeclamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pipeclamp.test.Country;
import com.pipeclamp.test.Page;
import com.pipeclamp.test.Passport;
import com.pipeclamp.test.Person;

/**
 * 
 * @author Brian Remedios
 */
public class PeopleBuilder {

	public static final String[] BadWords = new String[] { "idiot", "jerk", "nitwit" };
	
	private PeopleBuilder() { }

	public static List<Person> createPeople(int count) {
		
		List<Person> people = new ArrayList<Person>(count);
		for (int i=0; i<count; i++) {
			people.add(
					Person.newBuilder()
					.setFirstname("asdf")
					.setLastname("qwer")
					.setWeight(i * 2f)
					.setEmail( i % 2 == 0 ? "email" + Integer.toString(i) + "@mail.com" : null)
					.setNicknames(Arrays.asList(new String[] {"bob", "sue"}))
					.setBellybuttons(1)
					.setPassports(createPassports(3))
					.build()
					);
		}
		return people;
	}

	public static Person newPerson(String firstName, String lastName, int weight, Date birthdate, String... nicknames) {
		 
		return Person.newBuilder()
				.setFirstname(firstName)
				.setLastname(lastName)
				.setBirthdate(birthdate.getTime())
				.setWeight(weight)
				.setNicknames(Arrays.asList(nicknames))
				.setPassports( createPassports(Country.values().length))
				.build();
	}

	private static String passportIdFor(int i) {
		return Integer.toHexString(i*23).toUpperCase() +
				Integer.toHexString(i*13).toUpperCase() +
				Integer.toHexString(i*99).toUpperCase();
	}
	
	private static String passportTextFor(int i) {
		
		if (i == 3) return BadWords[0];
		
		return Integer.toHexString(i*23).toUpperCase() +
				Integer.toHexString(i*13).toUpperCase() +
				Integer.toHexString(i*99).toUpperCase();
	}

	public static Map<String, Passport> createPassports(int count) {
		
		Map<String, Passport> passports = new HashMap<>(count);
		Country country = null;
		
		for (int i=0; i<count; i++) {
			country = i%2==0 ? Country.CAN : Country.USA;
			passports.put( 
					country.name(),
					Passport.newBuilder()
						.setCountry(country)
						.setExpiryDate(new Date().getTime())
						.setNumber(passportIdFor(i))
						.setPages( createPages(4) )
						.build()
						);
			}
		return passports;
	}
	
	public static List<Page> createPages( int count) {
		
		List<Page> pages = new ArrayList<Page>(count);
		for (int i=0; i<count; i++) {
			pages.add( 
				Page.newBuilder()
					.setIndex(i)
					.setContents(passportTextFor(i))
					.build()
					);
		}

		return pages;
	}

}
