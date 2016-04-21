package com.pipeclamp.constraints.number;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.pipeclamp.AbstractRegisteredItem;

/**
 * 
 * @author Brian Remedios
 */
public class NamedRange extends AbstractRegisteredItem {

	public final Number min;
	public final boolean includesMin;
	public final Number max;
	public final boolean includesMax;

	public static final NamedRange Latitude		= new NamedRange("geoLatitude",  -90.0d, true, 90.0d, true);
	public static final NamedRange Longitude	= new NamedRange("geoLongitude", -180.0d, true, 180.0d, true);

	public static final Map<String, NamedRange> ALL = asMap(Latitude, Longitude);

	private static Map<String, NamedRange> asMap(NamedRange... theRanges) {
		Map<String, NamedRange> map = new HashMap<String, NamedRange>();
		for (NamedRange nr : theRanges) map.put(nr.id(), nr);
		return Collections.unmodifiableMap(map);
	}

	private NamedRange(String theName, Number theMin, boolean includesMinFlag, Number theMax, boolean includesMaxFlag) {
		super(theName, null);

		min = theMin;
		includesMin = includesMinFlag;
		max = theMax;
		includesMax = includesMaxFlag;
	}

	public boolean includes(Number value) {
		return false;
	}
}
