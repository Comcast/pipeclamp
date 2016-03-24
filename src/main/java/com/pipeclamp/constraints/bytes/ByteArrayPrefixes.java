package com.pipeclamp.constraints.bytes;

public class ByteArrayPrefixes {

	private String label;
	private byte[][] prefixes;

	private static byte[][] asByteArrays(String[] arrays) {
		return null;	// TODO
	}

	private ByteArrayPrefixes(String theLabel, String... thePrefixes) {
		label = theLabel;
		prefixes = asByteArrays(thePrefixes);
	}

	public static final ByteArrayPrefixes JPG = new ByteArrayPrefixes("JPG",
			"FF D8 FF DB",
			"FF D8 FF E0 nn nn 4A 46 49 46 00 01",
			"FF D8 FF E1 nn nn 45 78 69 66 00 00"
			);
	public static final ByteArrayPrefixes PNG = new ByteArrayPrefixes("PNG", "89 50 4E 47 0D 0A 1A 0A");

	public static final ByteArrayPrefixes PDF = new ByteArrayPrefixes("PDF", "25 50 44 46");
}
