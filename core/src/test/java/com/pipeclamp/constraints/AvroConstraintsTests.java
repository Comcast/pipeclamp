package com.pipeclamp.constraints;

import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.api.Violation;
import com.pipeclamp.avro.AvroConstraintUtil;
import com.pipeclamp.avro.AvroUtil;
import com.pipeclamp.avro.AvroValidator;
import com.pipeclamp.avro.QAUtil;
import com.pipeclamp.avro.SimpleAvroPath;
import com.pipeclamp.constraints.number.NumericConstraint;
import com.pipeclamp.constraints.string.RegexConstraint;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.path.Path;
import com.pipeclamp.test.Person;


/**
 *
 * @author Brian Remedios
 */
public class AvroConstraintsTests {

	private AvroValidator validator;

	public AvroConstraintsTests() { }

	private static Schema getPersonSchema() {
		 URL url = QAUtil.class.getClassLoader().getResource("person.avsc");
		 try {
			 return AvroUtil.schemaFrom(url);
		 	} catch (Exception ex) {
		 		ex.printStackTrace();
		 		return null;
		 	}
	}
	
	@BeforeSuite
	protected void setup() {

		validator = new AvroValidator(getPersonSchema(), false);
		System.out.println("Validator config:");
		System.out.println(validator);
	}

	@Test
	public void testStrings() {

		Person p = PeopleBuilder.newPerson("Bob", "Bitchin'", 250, new Date(), "frank", "franklin");
		p.setEmail("frank@mail.com");

		Map<Path<GenericRecord,?>, Collection<Violation>> issuesByPath = validator.validate(p);
		Assert.assertEquals(1, issuesByPath.size());
		Entry<Path<GenericRecord,?>, Collection<Violation>> entry = issuesByPath.entrySet().iterator().next();
		Assert.assertEquals(entry.getKey().toString(), "nicknames[]");

		p.setEmail(" not an email");	// set a bad one
		issuesByPath = validator.validate(p);
		Assert.assertEquals(2, issuesByPath.size());
		
		Collection<Violation> violations = issuesByPath.get(new SimpleAvroPath("email"));
		Assert.assertNotNull(violations);
		Assert.assertEquals(violations.size(), 1);
		Assert.assertEquals(RegexConstraint.class, violations.iterator().next().constraint.getClass());
		p.setEmail("frank@mail.com"); // set it right

		p.setFirstname("ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
		issuesByPath = validator.validate(p);
		Assert.assertEquals(issuesByPath.size(), 2);
		violations = issuesByPath.get(new SimpleAvroPath("firstname"));
		Assert.assertNotNull(violations);
		Assert.assertEquals(violations.size(), 1);
		Assert.assertEquals(StringLengthConstraint.class, violations.iterator().next().constraint.getClass());

		System.out.println("\nIssues:");
		AvroConstraintUtil.showIssues(issuesByPath, System.out);
	}

	private static void removeFrom(Map<Path<GenericRecord,?>, Collection<Violation>> fullSet, Map<Path<GenericRecord,?>, Collection<Violation>> subset) {

		for (Path<GenericRecord, ?> sub : subset.keySet()) {
			fullSet.remove(sub);
		}
	}

	@Test
	public void testNumbers() {

		Person person = PeopleBuilder.newPerson("Bob", "Bitchin'", 250, new Date(), "frank", "franklin");
		person.setEmail("frank@mail.com");

		Map<Path<GenericRecord,?>, Collection<Violation>> issuesByPath = validator.validate(person);

		AvroConstraintUtil.showIssues(issuesByPath, System.out);

		Assert.assertEquals(1, issuesByPath.size());

		person.setLatitude(200d);
		Map<Path<GenericRecord,?>, Collection<Violation>> newIssuesByPath = validator.validate(person);
		removeFrom(newIssuesByPath, issuesByPath);

		Assert.assertEquals(newIssuesByPath.size(), 1);
		Entry<Path<GenericRecord,?>, Collection<Violation>> entry = newIssuesByPath.entrySet().iterator().next();
		Assert.assertEquals("latitude", entry.getKey().toString());
		Assert.assertEquals(NumericConstraint.class, entry.getValue().iterator().next().constraint.getClass());
		person.setLatitude(null);

		person.setLongitude(-500d);

		newIssuesByPath = validator.validate(person);
		removeFrom(newIssuesByPath, issuesByPath);

		Assert.assertEquals(newIssuesByPath.size(), 1);
		entry = newIssuesByPath.entrySet().iterator().next();
		Assert.assertEquals("longitude", entry.getKey().toString());
		Assert.assertEquals(NumericConstraint.class, entry.getValue().iterator().next().constraint.getClass());

		System.out.println("\nIssues:");
		AvroConstraintUtil.showIssues(issuesByPath, System.out);
	}

	public static void main(String[] args) {
		
		Schema sch = Person.getClassSchema();
		
		StringLengthConstraint slc = new StringLengthConstraint("", true, 5, 10);
		
		AvroConstraintUtil.add(sch, slc);
		
		System.out.print(sch.toString(true));
	}
}
