package com.pipeclamp.metrics.aggregator;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pipeclamp.api.Aggregator;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Function;
import com.pipeclamp.path.Path;

/**
 *
 * @author Brian Remedios
 */
public class BasicAggregator<D extends Object> extends AbstractReceiver<D> implements Aggregator<D> {

	private Map<String, Collector<?>> collectorsById = new HashMap<>();
	private Map<String, PathFunction> pathsById = new HashMap<>();

	private long startTime = -1;

	private class PathFunction {

		final Path<D, ?> path;
		final Function<? extends Object, ? extends Object> function;
		final String unitLabel;

		PathFunction(Path<D, ?> thePath, Function<? extends Object, ? extends Object> theFunction, String theUnitLabel) {
			path = thePath; function = theFunction;  unitLabel = theUnitLabel;
		}

		String withLabel(Object value) {
			if (unitLabel == null) return String.valueOf(value);
			return value + unitLabel;
		}
	}

	public BasicAggregator() { }

	@Override
	public void unregisterAll() {

		pathsById.clear();
		collectorsById.clear();
	}

	@Override
	public int accept(D record) {

		for (Entry<String, PathFunction> entry  : pathsById.entrySet()) {
			Object value = entry.getValue().path.valueVia(record);
	//		System.out.println("**VALUE: " + value);
			Collector coll = collectorsById.get(entry.getKey());
			coll.add(value);
	//		System.out.println("**COLLECTED: " + coll.collected());
		}

		return super.accept(record);
	}

	@Override
	public void clear() {
		super.clear();

		for (Collector<?> coll : collectorsById.values()) coll.clear();
		startTime = System.currentTimeMillis();
	}

	@Override
	public void register(Path<D, ?> path, Function<? extends Object, ? extends Object> operation, String id, String unitLabel) {

		// TODO we might have multiple metrics linked to a single path so to avoid multiple retrievals of the same value
		// 		for these metrics, better to create mappings that relate unique paths to one or more metrics.

		//  for example we might want to obtain not just the min & max of a value but also its average.

		PathFunction pf = new PathFunction(path, operation, unitLabel);
		pathsById.put(id, pf);
		collectorsById.put(id, operation.createCollector());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> compute(PrintStream debugOut) {

		Map<String, Object> computedValuesById = new HashMap<String, Object>(collectorsById.size());
		Collector collector;

		for (Entry<String, PathFunction> entry : pathsById.entrySet()) {

			String id = entry.getKey();
			if (debugOut != null) debugOut.println("checking: " + id);

			collector = collectorsById.get(id);
			if (debugOut != null) debugOut.println("collected: " + collector.collected());
			if (collector.collected() == 0) continue;

			Object result = entry.getValue().function.compute(collector);
			if (debugOut != null) debugOut.println(id + "\t" + entry.getValue().withLabel(result));
			computedValuesById.put(id, result);
		}

		computedValuesById.put(TotalRecordsKey, count());
		computedValuesById.put(StartTimeKey, startTime);
		computedValuesById.put(EndTimeKey, System.currentTimeMillis());

		return computedValuesById;
	}

	@Override
	public String toString() {

		if (collectorsById.isEmpty()) return "";

		StringBuilder sb = new StringBuilder();
		for (String key : collectorsById.keySet()) {
			sb.append(key).append(": ").append(collectorsById.get(key)).append('\n');
		}
		return sb.toString();
	}
}
