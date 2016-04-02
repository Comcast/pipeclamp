package com.pipeclamp.constraints.bytes;

import com.pipeclamp.api.ByteSignatureMatcher;
import com.pipeclamp.util.ByteUtil;
import com.pipeclamp.util.StringUtil;

/**
 * 
 * @author Brian Remedios
 */
public class SimplePrefixMatcher implements ByteSignatureMatcher {

	private final byte[][] prefixes;
	
	public static final SimplePrefixMatcher PDF			= new SimplePrefixMatcher( "%PDF".getBytes() );
	public static final SimplePrefixMatcher GIF			= new SimplePrefixMatcher( "GIF87a".getBytes(), "GIF89a".getBytes() );
	public static final SimplePrefixMatcher PNG			= new SimplePrefixMatcher( StringUtil.asByteArray("89 50 4E 47 0D 0A 1A 0A"));
	public static final SimplePrefixMatcher JavaClass	= new SimplePrefixMatcher( StringUtil.asByteArray("CA FE BA BE"));
	
	public SimplePrefixMatcher(byte[]... thePrefixes) {
		prefixes = thePrefixes;
	}

	@Override
	public boolean matches(byte[] data) {
		
		for (byte[] prefix : prefixes) {
			if (ByteUtil.matchesStart(data, prefix)) return true;
		}
		return false;
	}

}
