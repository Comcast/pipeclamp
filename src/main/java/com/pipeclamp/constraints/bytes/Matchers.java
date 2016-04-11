package com.pipeclamp.constraints.bytes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.SignatureMatcher;

/**
 *
 * @author Brian Remedios
 */
public interface Matchers {

	Map<String, SignatureMatcher> ByteArrayMatchersById= ImmutableMap.<String, SignatureMatcher>builder().
		      put("GIF", SimplePrefixMatcher.GIF).
		      put("PDF", SimplePrefixMatcher.PDF).
		      put("PNG", SimplePrefixMatcher.PNG).
		      put("JPG", SimplePrefixMatcher.JPG).
		      put("JPG_EXIF", SimplePrefixMatcher.JPG_WITH_EXIF).
		      put("javaClass", SimplePrefixMatcher.JavaClass).
		      build();

}
