package com.pipeclamp.metrics.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.pipeclamp.api.Classifier;
import com.pipeclamp.api.Collector;
import com.pipeclamp.api.Parameter;
import com.pipeclamp.metrics.collectors.ClassifyingHistogramCollector;
import com.pipeclamp.params.StringParameter;

/**
 * Counts the number of items that fall into distinct groups as determined by a provided classifier.
 *
 * @author Brian Remedios
 *
 * @param <I>
 */
public class GroupingHistogram<I extends Object> extends AbstractCollectionFunction<I, Map<String, Integer>> {

	private final Classifier<I> classifier;

	private static final StringParameter ClassificationParam = new StringParameter("classifier", "");

	public static final String Id = "groupingHistogram";

	public GroupingHistogram(Classifier<I> theClassifier) {
		super(null);

		classifier = theClassifier;
	}

	@Override
	public Collector<I> createCollector() {
		return new ClassifyingHistogramCollector<>(predicate(), classifier);
	}

	@Override
	public Map<String,Integer> compute(Collector<I> collector) {

		Set<String> classifications = collector.classifications();

		Map<String, Integer> values = new HashMap<>(classifications.size());

		for (String classification : classifications) {
			values.put(classification, collector.countsOf(classification));
		}
		return values;
	}

	@Override
	public Map<Parameter<?>, Object> parameters() {

		return ImmutableMap.<Parameter<?>, Object>builder().
			      put(ClassificationParam, classifier).
			      build();
	}
}
