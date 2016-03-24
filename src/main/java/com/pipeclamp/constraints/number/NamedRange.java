package com.pipeclamp.constraints.number;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Brian Remedios
 */
public class NamedRange {

	public final String name;
	public final Number min;
	public final Number max;

	public static final NamedRange Latitude		= new NamedRange("geoLatitude",  -90.0d,  90.0d);
	public static final NamedRange Longitude	= new NamedRange("geoLongitude", -180.0d, 180.0d);

	public static final Map<String, NamedRange> ALL = asMap(Latitude, Longitude);

	private static Map<String, NamedRange> asMap(NamedRange... theRanges) {
		Map<String, NamedRange> map = new HashMap<String, NamedRange>();
		for (NamedRange nr : theRanges) map.put(nr.name, nr);
		return Collections.unmodifiableMap(map);
	}

	private NamedRange(String theName, Number theMin, Number theMax) {
		name = theName;
		min = theMin;
		max = theMax;
	}

}
