package com.pipeclamp.metrics;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pipeclamp.api.Function;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.metrics.functions.Averager;
import com.pipeclamp.metrics.functions.Histogram;
import com.pipeclamp.metrics.functions.MinMax;
import com.pipeclamp.metrics.functions.StdDeviation;
import com.pipeclamp.metrics.functions.Summer;
import com.pipeclamp.util.StringUtil;

/**
 * Maintains a listing of all the metrics that can be created. They can be retrieved
 * by referencing their construction parameters or their intrinsic value types.
 *
 * @author Brian Remedios
 */
public class MetricFactory {

	private final Map<String, Map<Class<?>, Function<?,?>>> buildersByFunction = new HashMap<>();

	public static final MetricFactory Instance = new MetricFactory();

	private MetricFactory() {
		setup();
	}

	private void setup() {
		register(Averager.Id,	Averager.AveragersByType );
		register(Summer.Id,		Summer.SummersByType);
		register(Histogram.Id,	Histogram.HistogramsByType);
		register(MinMax.Id,		MinMax.MinMaxByType);
		register(StdDeviation.Id, StdDeviation.StdDeviationByType);
	}

	public void register(String functionId, Map<Class<?>, Function<?,?>> builders) {

		buildersByFunction.put(functionId, builders);
	}

	public Set<String> registeredFunctions() { return buildersByFunction.keySet(); }

	public Function<?,?> functionFor(String functionId, Class<?> javaType) {

		Map<Class<?>, Function<?,?>> builders =  buildersByFunction.get(functionId);
		if (builders == null) return null;

		return builders.get(javaType);
	}

	public Set<String> availableFunctionsFor(Class<?> javaType) {

		Set<String> functions = new HashSet<>(buildersByFunction.size());
		for (Entry<String, Map<Class<?>, Function<?,?>>> entry : buildersByFunction.entrySet()) {
			if (entry.getValue().containsKey(javaType)) functions.add(entry.getKey());
		}
		return functions;
	}

	public void showOn(PrintStream out) {

		int paramIdWidth = 12;

		for (Entry<String, Map<Class<?>, Function<?,?>>> entry : buildersByFunction.entrySet()) {
			out.println(entry.getKey());
			for (Function<?,?> function : entry.getValue().values()) {
				out.println("\t" + function.toString());
				for (Parameter<?> p : function.parameters().keySet()) {
					out.print("\t\t");
					StringUtil.printLeftJustified('\'' + p.id() + '\'', out, paramIdWidth);;
					out.println("\t" + p.description());
				}
			}
		}
	}
}
