package com.pipeclamp.constraints.string;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.apache.avro.Schema;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.ValueConstraint;
import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.collections.CollectionContentConstraint;

public class WordSetConstraintTest extends AbstractConstraintTest {

	private static final String[] BadWords = new String[] { "jerk", "idiot" };

	private static final String[] RequiredWords = new String[] { "red", "white", "blue" };

	@Test
	public void testBuilder() {

		Map<String,String> paramsByKey = asParams(
				  CollectionContentConstraint.Function, WordRestriction.CannotHave, 
				  CollectionContentConstraint.Options, "jerk idiot");

		Collection<ValueConstraint<?>> vcs = WordSetConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		assertNotNull(vcs);
		assertTrue(paramsByKey.isEmpty());
		
		paramsByKey = asParams(
				  CollectionContentConstraint.Options, "jerk idiot");

		vcs = WordSetConstraint.Builder.constraintsFrom(Schema.Type.ARRAY, false, paramsByKey);

		assertNull(vcs);
		assertTrue(paramsByKey.isEmpty());
	}

	@Test
	public void typedErrorForCannotHave() {

		WordSetConstraint wsc = new WordSetConstraint("", true, BadWords, WordRestriction.CannotHave);

		Violation v = wsc.errorFor(null);
		Assert.assertNull(v);

		v = wsc.errorFor("Bob is an idiot");
		Assert.assertNotNull(v);

		v = wsc.errorFor("Bob is an nice guy");
		Assert.assertNull(v);
	}

	@Test
	public void typedErrorForMustHaveAll() {

		WordSetConstraint wsc = new WordSetConstraint("", false, RequiredWords, WordRestriction.MustHaveAll);

		Violation v = wsc.errorFor(null);		// nulls not allowed
		Assert.assertNotNull(v);

		v = wsc.errorFor("The American flag has the colours: red, white, and blue");
		Assert.assertNull(v);

		v = wsc.errorFor("The Canadian flag has red and white");
		Assert.assertNotNull(v);

		v = wsc.errorFor("The Canadian flag is nice");
		Assert.assertNotNull(v);
	}
}
