package com.pipeclamp.constraints.multivalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.MultiValueConstraint;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.avro.AvroValidator;
import com.pipeclamp.avro.SimpleAvroPath;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.path.Path;
import com.pipeclamp.test.Person;

public class ComparativeConstraintTest extends AbstractConstraintTest {

	@Test
	public void builderFor() {

		ConstraintBuilder<?> builder = ComparativeConstraint.builderFor(" ", SimpleAvroPath.Builder);
		assertNotNull(builder);
	
		Map<String,String> paramsByKey = asParams(
				ComparativeConstraint.Path1, "birthdate", 
				ComparativeConstraint.Path2, "deathdate",
				ComparativeConstraint.CompParam, ">"
				);
	
		Collection<Constraint<?>> constraints = builder.constraintsFrom(null, false, paramsByKey);
		assertNotNull(constraints);	
		
		Constraint<?> cst = constraints.iterator().next();
		assertNotNull(cst);
	}

	@Test
	public void testEvaluate() {
		
		ConstraintBuilder<?> builder = ComparativeConstraint.builderFor(" ", SimpleAvroPath.Builder);
	
		Map<String,String> paramsByKey = asParams(
				ComparativeConstraint.Path1, "birthdate", 
				ComparativeConstraint.Path2, "deathdate",
				ComparativeConstraint.CompParam, "<"
				);
	
		Collection<Constraint<?>> constraints = builder.constraintsFrom(null, false, paramsByKey);
		MultiValueConstraint<GenericRecord> mvc = (MultiValueConstraint<GenericRecord>)constraints.iterator().next();
		assertNotNull(mvc);
	
		AvroValidator validator = new AvroValidator();
		validator.register(mvc, "");
		
		long now = System.currentTimeMillis();
		
		Person person = PeopleBuilder.createPeople(1).iterator().next();
		person.setBirthdate( now);
		person.setDeathdate( now + 1 );
		
		Map<Path<GenericRecord, ?>, Collection<Violation>> issues = validator.validate(person);
		assertEquals(0, issues.size());
		
		person.setBirthdate( now);
		person.setDeathdate( now - 1 );
		
		issues = validator.validate(person);
		assertEquals(1, issues.size());
	}
	
	
	@Override
	protected ConstraintBuilder<?> sampleBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ValueConstraint<?> sampleConstraint() {
		// TODO Auto-generated method stub
		return null;
	}
}
