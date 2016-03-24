package com.pipeclamp.constraints.string;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pipeclamp.api.Violation;
import com.pipeclamp.constraints.AbstractConstraintTest;
import com.pipeclamp.constraints.string.WordRestriction;
import com.pipeclamp.constraints.string.WordSetConstraint;

public class WordSetConstraintTest extends AbstractConstraintTest {

	private static final String[] BadWords = new String[] { "jerk", "idiot" };

	private static final String[] RequiredWords = new String[] { "red", "white", "blue" };

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
