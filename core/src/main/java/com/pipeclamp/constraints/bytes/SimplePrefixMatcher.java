package com.pipeclamp.constraints.bytes;

import com.pipeclamp.AbstractRegisteredItem;
import com.pipeclamp.api.SignatureMatcher;
import com.pipeclamp.util.ByteUtil;
import com.pipeclamp.util.StringUtil;

/**
 * See http://www.filesignatures.net/
 *
 * @author Brian Remedios
 */
public class SimplePrefixMatcher extends AbstractRegisteredItem implements SignatureMatcher {

	private final int offset;
	private final byte[][] prefixes;

	public static final SimplePrefixMatcher PDF			= new SimplePrefixMatcher("pdf", "%PDF".getBytes() );
	public static final SimplePrefixMatcher GIF			= new SimplePrefixMatcher("gif", "GIF87a".getBytes(), "GIF89a".getBytes() );
	public static final SimplePrefixMatcher PNG			= new SimplePrefixMatcher("png", StringUtil.asByteArray("89 50 4E 47 0D 0A 1A 0A"));
	public static final SimplePrefixMatcher JavaClass	= new SimplePrefixMatcher("Java class", StringUtil.asByteArray("CA FE BA BE"));

	public static final SimplePrefixMatcher JPG				= new SimplePrefixMatcher("jpg", StringUtil.asByteArray("FF D8 FF E0"));
	public static final SignatureMatcher JPG_WITH_EXIF	= JPG.and("jpg w/Exif", new SimplePrefixMatcher(null, 4, StringUtil.asByteArray("FF D8 FF E1")));
	public static final SimplePrefixMatcher JPG2000			= new SimplePrefixMatcher("jpg2000", StringUtil.asByteArray("00 00 00 0C 6A 50 20 20 "));

	public SimplePrefixMatcher(String theId, byte[]... thePrefixes) {
		this(theId, 0, thePrefixes);
	}

	public SimplePrefixMatcher(String theId, int theOffset, byte[]... thePrefixes) {
		super(theId, null);
		
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

	public SignatureMatcher and(final String id, final SimplePrefixMatcher other) {

		return new SignatureMatcher() {
			public String id() { return id; }
			public String description() { return this.id() + " & " + other.id(); }
			public boolean matches(byte[] data) {
				return this.matches(data) && other.matches(data);
			}
		};
	}

	public SignatureMatcher or(final String id, final SimplePrefixMatcher other) {

		return new SignatureMatcher() {
			public String id() { return id; }
			public String description() { return this.id() + " | " + other.id(); }
			public boolean matches(byte[] data) {
				return this.matches(data) || other.matches(data);
			}
		};
	}
}
