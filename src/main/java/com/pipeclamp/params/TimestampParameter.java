package com.pipeclamp.params;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.avro.Schema.Type;

/**
 *
 * @author Brian Remedios
 *
 */
public class TimestampParameter extends AbstractParameter<Long> {

	private final DateFormat format;

	public TimestampParameter(String theId, String theDescription, String theFormat) {
		super(theId, theDescription);

		format = new SimpleDateFormat(theFormat);
	}

	@Override
	public Long valueIn(String text, Type type) {

		if (text == null || text.length() == 0) return null;

		try {
			Date ts = format.parse(text);
			return ts.getTime();
			} catch (ParseException pe) {
				return null;
			}
	}

	public String asString(long timestamp) {
		return format.format(new Date(timestamp));
	}
}
