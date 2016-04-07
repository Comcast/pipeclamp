package com.pipeclamp.constraints.bytes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.testng.annotations.Test;

public class SimplePrefixMatcherTest {

	@Test
	public void matches() {

		SimplePrefixMatcher spm = new SimplePrefixMatcher(new byte[] { 0, 1, 2} );

		assertTrue(spm.matches(new byte[] { 0, 1, 2} ));
		assertTrue(spm.matches(new byte[] { 0, 1, 2, 3, 4} ));

		assertFalse(spm.matches(null));
		assertFalse(spm.matches(new byte[] { 0, 0, 1, 2} ));
	}

	private static void closeQuietly(Closeable c) {

		if (c == null) return;
		try {
			c.close();
		} catch (IOException ioe) {
			// ignore
		}
	}

	private byte[] readResource(String filename) {
		
		URL url = getClass().getClassLoader().getResource(filename);
		File file;
		try {
			file = new File(url.toURI());
		} catch(URISyntaxException e) {
			file = new File(url.getPath());
		}

		byte[] bFile = new byte[(int) file.length()];
		InputStream input = null;
		try {
			input = new FileInputStream(file);
			input.read(bFile);
			return bFile;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			closeQuietly(input);
		}
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
