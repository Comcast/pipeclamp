package com.pipeclamp.constraints.bytes;

import com.pipeclamp.api.SignatureMatcher;
import com.pipeclamp.util.ByteUtil;
import com.pipeclamp.util.StringUtil;

/**
 * See http://www.filesignatures.net/
 *
 * @author Brian Remedios
 */
public class SimplePrefixMatcher implements SignatureMatcher {

	private final int offset;
	private final byte[][] prefixes;

	public static final SimplePrefixMatcher PDF			= new SimplePrefixMatcher( "%PDF".getBytes() );
	public static final SimplePrefixMatcher GIF			= new SimplePrefixMatcher( "GIF87a".getBytes(), "GIF89a".getBytes() );
	public static final SimplePrefixMatcher PNG			= new SimplePrefixMatcher( StringUtil.asByteArray("89 50 4E 47 0D 0A 1A 0A"));
	public static final SimplePrefixMatcher JavaClass	= new SimplePrefixMatcher( StringUtil.asByteArray("CA FE BA BE"));

	public static final SimplePrefixMatcher JPG				= new SimplePrefixMatcher(StringUtil.asByteArray("FF D8 FF E0"));
	public static final SignatureMatcher JPG_WITH_EXIF	= JPG.and(new SimplePrefixMatcher(4, StringUtil.asByteArray("FF D8 FF E1")));
	public static final SimplePrefixMatcher JPG2000			= new SimplePrefixMatcher(StringUtil.asByteArray("00 00 00 0C 6A 50 20 20 "));

	public SimplePrefixMatcher(byte[]... thePrefixes) {
		this(0, thePrefixes);
	}

	public SimplePrefixMatcher(int theOffset, byte[]... thePrefixes) {
		offset = theOffset;
		prefixes = thePrefixes;
	}

	@Override
	public boolean matches(byte[] data) {

		for (byte[] prefix : prefixes) {
			if (ByteUtil.matches(data, prefix, offset)) return true;
		}
		return false;
	}

	public SignatureMatcher and(final SimplePrefixMatcher other) {

		return new SignatureMatcher() {
			public boolean matches(byte[] data) {
				return this.matches(data) && other.matches(data);
			}
		};
	}

	public SignatureMatcher or(final SimplePrefixMatcher other) {

		return new SignatureMatcher() {
			public boolean matches(byte[] data) {
				return this.matches(data) || other.matches(data);
			}
		};
	}
}
