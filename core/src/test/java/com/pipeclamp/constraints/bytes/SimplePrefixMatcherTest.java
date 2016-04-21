package com.pipeclamp.constraints.bytes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

import com.pipeclamp.constraints.AbstractConstraintTest;

public class SimplePrefixMatcherTest  {

	@Test
	public void matches() {

		SimplePrefixMatcher spm = new SimplePrefixMatcher("dummy", new byte[] { 0, 1, 2} );

		assertTrue(spm.matches(new byte[] { 0, 1, 2} ));
		assertTrue(spm.matches(new byte[] { 0, 1, 2, 3, 4} ));

		assertFalse(spm.matches(null));
		assertFalse(spm.matches(new byte[] { 0, 0, 1, 2} ));
	}

	@Test
	public void matchesPredefined() {

		byte[] data = AbstractConstraintTest.readResource("32px.png");

		assertTrue(SimplePrefixMatcher.PNG.matches(data));
		assertFalse(SimplePrefixMatcher.GIF.matches(data));
		assertFalse(SimplePrefixMatcher.PDF.matches(data));
		
		data = AbstractConstraintTest.readResource("throbber.gif");

		assertTrue(SimplePrefixMatcher.GIF.matches(data));
		assertFalse(SimplePrefixMatcher.PNG.matches(data));
		assertFalse(SimplePrefixMatcher.PDF.matches(data));
	}
}
