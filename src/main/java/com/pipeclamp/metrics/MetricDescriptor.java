package com.pipeclamp.metrics;

import com.pipeclamp.api.DescriptiveItem;
import com.pipeclamp.api.Function;

/**
 *
 * @author Brian Remedios
 */
public class MetricDescriptor implements DescriptiveItem {

	public final String id;
	public final String label;
	public final Function<? extends Object, ? extends Object> function;
	public final String unitLabel;
	public final String description;

	/**
	 *
	 * @param theId
	 * @param theLabel
	 * @param thePath
	 * @param theFunction
	 * @param aUnitLabel
	 */
	public MetricDescriptor(String theId, String theLabel, Function<? extends Object, ? extends Object> theFunction, String aUnitLabel, String theDoc) {
		id = theId;
		label = theLabel;
		function = theFunction;
		unitLabel = aUnitLabel;
		description = theDoc;
	}

	@Override
	public String description() { return description; }
}
