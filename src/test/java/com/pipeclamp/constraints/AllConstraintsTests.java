package com.pipeclamp.constraints;

import com.pipeclamp.validation.Validator;


/**
 *
 * @author Brian Remedios
 */
public class AllConstraintsTests {

	private Validator validator;

	public AllConstraintsTests() { }

//	@BeforeSuite
//	protected void setup() {
//		validator = new Validator(Person.getClassSchema(), false);
//		System.out.println("Validator config:");
//		System.out.println(validator);
//	}

//	@Test
//	public void testStrings() {
//
//		Person p = PeopleBuilder.newPerson("Bob", "Bitchin'", 250, new Date(), "frank", "franklin");
//		p.setEmail("frank@mail.com");
//
//		Map<String[], Collection<Violation>> issuesByPath = validator.validate(p);
//		Assert.assertTrue(issuesByPath.isEmpty());
//
//		p.setEmail(" not an email");	// set a bad one
//		issuesByPath = validator.validate(p);
//		Assert.assertTrue(issuesByPath.size() == 1);
//		Entry<String[], Collection<Violation>> entry = issuesByPath.entrySet().iterator().next();
//		Assert.assertEquals("email", entry.getKey()[0]);
//		Assert.assertEquals(RegexConstraint.class, entry.getValue().iterator().next().constraint.getClass());
//		p.setEmail("frank@mail.com"); // set it right
//
//		p.setFirstname("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
//		issuesByPath = validator.validate(p);
//		Assert.assertTrue(issuesByPath.size() == 1);
//		entry = issuesByPath.entrySet().iterator().next();
//		Assert.assertEquals("firstname", entry.getKey()[0]);
//		Assert.assertEquals(StringLengthConstraint.class, entry.getValue().iterator().next().constraint.getClass());
//
//		System.out.println("\nIssues:");
//		ConstraintUtil.showIssues(issuesByPath, System.out);
//	}
//
//	@Test
//	public void testNumbers() {
//
//		Person person = PeopleBuilder.newPerson("Bob", "Bitchin'", 250, new Date(), "frank", "franklin");
//		person.setEmail("frank@mail.com");
//
//		Map<String[], Collection<Violation>> issuesByPath = validator.validate(person);
//
//		ConstraintUtil.showIssues(issuesByPath, System.out);
//
//		Assert.assertTrue(issuesByPath.isEmpty());
//
//		person.setLatitude(200d);
//		issuesByPath = validator.validate(person);
//		Assert.assertTrue(issuesByPath.size() == 1);
//		Entry<String[], Collection<Violation>> entry = issuesByPath.entrySet().iterator().next();
//		Assert.assertEquals("latitude", entry.getKey()[0]);
//		Assert.assertEquals(NumericConstraint.class, entry.getValue().iterator().next().constraint.getClass());
//		person.setLatitude(null);
//
//		person.setLongitude(-500d);
//		issuesByPath = validator.validate(person);
//		Assert.assertTrue(issuesByPath.size() == 1);
//		entry = issuesByPath.entrySet().iterator().next();
//		Assert.assertEquals("longitude", entry.getKey()[0]);
//		Assert.assertEquals(NumericConstraint.class, entry.getValue().iterator().next().constraint.getClass());
//
//		System.out.println("\nIssues:");
//		ConstraintUtil.showIssues(issuesByPath, System.out);
//	}
}
