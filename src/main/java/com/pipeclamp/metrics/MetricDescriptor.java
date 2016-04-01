package com.pipeclamp.metrics;

import java.util.Objects;

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
	
	@Override
	public boolean equals(Object other) {
		
		if (this == other) return true;
		if (other == null) return false;
		
		if (!(other instanceof MetricDescriptor)) return false;
		
		MetricDescriptor md = MetricDescriptor.class.cast(other);
		
		return Objects.deepEquals(id, md.id) &&
				Objects.deepEquals(label, md.label) &&
				Objects.deepEquals(function, md.function) &&
				Objects.deepEquals(unitLabel, md.unitLabel) &&
				Objects.deepEquals(description, md.description);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, label, function, unitLabel, description);
	}

	@Override
	public String toString() {
		return id + "\t" + label + "\t" + unitLabel + "\t" + description;
	}
}
