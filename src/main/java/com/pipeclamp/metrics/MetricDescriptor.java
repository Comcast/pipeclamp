package com.pipeclamp.metrics;

import java.util.Objects;

import com.pipeclamp.AbstractRegisteredItem;
import com.pipeclamp.api.Function;

/**
 *
 * @author Brian Remedios
 */
public class MetricDescriptor extends AbstractRegisteredItem {

	public final String label;
	public final Function<? extends Object, ? extends Object> function;
	public final String unitLabel;

	/**
	 *
	 * @param theId
	 * @param theLabel
	 * @param thePath
	 * @param theFunction
	 * @param aUnitLabel
	 */
	public MetricDescriptor(String theId, String theLabel, Function<? extends Object, ? extends Object> theFunction, String aUnitLabel, String theDescription) {
		super(theId, theDescription);
		label = theLabel;
		function = theFunction;
		unitLabel = aUnitLabel;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if (!super.equals(other)) return false;
		
		if (!(other instanceof MetricDescriptor)) return false;
		
		MetricDescriptor md = MetricDescriptor.class.cast(other);
		
		return 
				Objects.deepEquals(label, md.label) &&
				Objects.deepEquals(function, md.function) &&
				Objects.deepEquals(unitLabel, md.unitLabel);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hash(label, function, unitLabel);
	}

	@Override
	public String toString() {
		return id() + "\t" + label + "\t" + unitLabel + "\t" + description();
	}
}
