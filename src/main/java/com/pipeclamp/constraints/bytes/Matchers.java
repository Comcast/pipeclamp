package com.pipeclamp.constraints.bytes;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.ByteSignatureMatcher;

/**
 * 
 * @author Brian Remedios
 */
public interface Matchers {

	Map<String, ByteSignatureMatcher> ByteArrayMatchersById= ImmutableMap.<String, ByteSignatureMatcher>builder().
		      put("GIF", SimplePrefixMatcher.GIF).
		      put("PDF", SimplePrefixMatcher.PDF).
		      put("javaClass", SimplePrefixMatcher.JavaClass).
		      put("PNG", SimplePrefixMatcher.PNG).
		      build();
	
}
