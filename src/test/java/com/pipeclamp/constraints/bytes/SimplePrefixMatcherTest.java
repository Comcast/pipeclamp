package com.pipeclamp.constraints.bytes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.Test;

import com.pipeclamp.constraints.AbstractConstraintTest;

public class SimplePrefixMatcherTest extends AbstractConstraintTest {

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

		byte[] data = readResource("32px.png");

		assertTrue(SimplePrefixMatcher.PNG.matches(data));
		assertFalse(SimplePrefixMatcher.GIF.matches(data));
		assertFalse(SimplePrefixMatcher.PDF.matches(data));
		
		data = readResource("throbber.gif");

		assertTrue(SimplePrefixMatcher.GIF.matches(data));
		assertFalse(SimplePrefixMatcher.PNG.matches(data));
		assertFalse(SimplePrefixMatcher.PDF.matches(data));
	}

}
