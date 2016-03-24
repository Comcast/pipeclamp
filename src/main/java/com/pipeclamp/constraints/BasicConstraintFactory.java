package com.pipeclamp.constraints;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pipeclamp.api.ConstraintBuilder;
import com.pipeclamp.api.ConstraintFactory;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.util.StringUtil;

/**
 * Maintains a listing of all the constraints that can be created. They can be retrieved
 * by referencing their construction parameters or their intrinsic value types.
 *
 * @author Brian Remedios
 */
public class BasicConstraintFactory<T extends Object> implements ConstraintFactory<T> {

	private final Map<T, Map<String, ConstraintBuilder<?>>> buildersByType = new HashMap<>();

	public BasicConstraintFactory() { }

	@Override
	public void register(T type, ConstraintBuilder<?> ...builders) {

		Map<String, ConstraintBuilder<?>> buildersById = buildersByType.get(type);
		if (buildersById == null) {
			buildersById = new HashMap<>(builders.length);
			buildersByType.put(type, buildersById);
			}

		for (ConstraintBuilder<?> cb : builders) {
			buildersById.put(cb.id(), cb);
		}
	}

	public Set<T> registeredTypes() { return buildersByType.keySet(); }

	@Override
	public ConstraintBuilder<?> builderFor(T type, String id) {
		Map<String, ConstraintBuilder<?>> builders =  buildersByType.get(type);
		if (builders == null) return null;
		return builders.get(id);
	}

	@Override
	public void showOn(PrintStream out) {

		int paramIdWidth = 12;

		for (Entry<T, Map<String, ConstraintBuilder<?>>> entry : buildersByType.entrySet()) {
			out.println(entry.getKey());
			for (ConstraintBuilder<?> builder : entry.getValue().values()) {
				out.println("\t" + builder.toString());
				for (Parameter<?> p : builder.parameters()) {
					out.print("\t\t");
					StringUtil.printLeftJustified('\'' + p.id() + '\'', out, paramIdWidth);;
					out.println("\t" + p.description());
				}
			}
		}
	}
}
