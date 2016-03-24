package com.pipeclamp.util;

import java.util.Date;

/**
 *
 * @author Brian Remedios
 */
public class TimeUtil {

	private TimeUtil() {}

	public static boolean isFuture(long timestamp) {
		return new Date().getTime() < timestamp;
	}

	public static boolean isPast(long timestamp) {
		return new Date().getTime() > timestamp;
	}
}
