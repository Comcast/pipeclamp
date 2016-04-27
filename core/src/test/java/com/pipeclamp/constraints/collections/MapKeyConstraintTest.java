package com.pipeclamp.constraints.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema.Type;
import org.testng.annotations.Test;

import com.pipeclamp.PeopleBuilder;
import com.pipeclamp.api.Constraint;
import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.avro.AvroConfiguration;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.string.StringLengthConstraint;
import com.pipeclamp.test.Person;

public class MapKeyConstraintTest extends AbstractConstraintTest {

	@Test
	public void testMapKeyConstraintBuilder() {

		Map<String,String> paramsByKey = asParams(
				MapKeyConstraint.KEY_TYPE, "string",
				AbstractMapConstraint.CONSTRAINT_ID, StringLengthConstraint.TypeTag, 
				StringLengthConstraint.MIN_LENGTH, 3);

		Collection<Constraint<?>> vcs = MapKeyConstraint.builderWith(AvroConfiguration.ConstraintFactory).constraintsFrom(Type.MAP, false, paramsByKey);

		assertNotNull(vcs);
		assertEquals(vcs.size(), 1);
	
		paramsByKey = asParams(
				MapKeyConstraint.KEY_TYPE, "string",
	//			AbstractMapConstraint.CONSTRAINT_ID, StringLengthConstraint.TypeTag, 		missing
				StringLengthConstraint.MIN_LENGTH, 3);

		vcs = MapKeyConstraint.builderWith(AvroConfiguration.ConstraintFactory).constraintsFrom(Type.MAP, false, paramsByKey);

		assertNull(vcs);
	}

	@Test
	public void typedErrorFor() {
		
		// Constraint: country names, used as keys in the passport map, cannot be longer than three characters

		Map<String,String> paramsByKey = asParams(
				MapKeyConstraint.KEY_TYPE, "string",
				AbstractMapConstraint.CONSTRAINT_ID, StringLengthConstraint.TypeTag, 
				StringLengthConstraint.MIN_LENGTH, 3);

		Collection<Constraint<?>> vcs = MapKeyConstraint.builderWith(AvroConfiguration.ConstraintFactory).constraintsFrom(Type.MAP, false, paramsByKey);
		assertNotNull(vcs);
		assertEquals(vcs.size(), 1);
		
		ValueConstraint<?> vc = (ValueConstraint<?>)vcs.iterator().next();
		
		Person person = PeopleBuilder.createPeople(1).iterator().next();
		
		Violation violation = vc.errorFor(person.getPassports());
		assertNull(violation);
	}

	@Override
	protected ConstraintBuilder<?> sampleBuilder() { return MapKeyConstraint.builderWith(AvroConfiguration.ConstraintFactory); }

	@Override
	protected ValueConstraint<?> sampleConstraint() {
		
		Map<String,String> paramsByKey = asParams(
				MapKeyConstraint.KEY_TYPE, "string",
				AbstractMapConstraint.CONSTRAINT_ID, StringLengthConstraint.TypeTag, 
				StringLengthConstraint.MIN_LENGTH, 3);

		Collection<Constraint<?>> vcs = MapKeyConstraint.builderWith(AvroConfiguration.ConstraintFactory).constraintsFrom(Type.MAP, false, paramsByKey);
		return (ValueConstraint<?>)vcs.iterator().next();
	}
}
