package com.pipeclamp.metrics.collectors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;

/**
 * Counts the number of items (strings) that match the registered regular expressions.
 *
 * @author Brian Remedios
 *
 */
public class RegexCollector extends AbstractCollector<String> {

	private Map<String, Pattern> qualifiersByName = new HashMap<>();
	private Map<String, Integer> countsPerItem = new HashMap<>();

	public RegexCollector(Predicate<String> aPredicate) {
		super(aPredicate);
	}

	private String keyFor(String item) {

		String itemStr = String.valueOf(item);

		for (Entry<String,Pattern> entry : qualifiersByName.entrySet()) {
			if (entry.getValue().matcher(itemStr).matches()) {
				return entry.getKey();
			}
		}
		return null;
	}

	public void register(String id, String regex) {
		qualifiersByName.put(id, Pattern.compile(regex));
	}

	@Override
	public boolean add(String item) {
		if (!super.add(item)) return false;

		String itemKey = keyFor(item);
		if (itemKey == null) return false;

		Integer count = countsPerItem.get(itemKey);
		if (count == null) count = Integer.valueOf(0);
		countsPerItem.put(itemKey, count+1);
		return true;
	}

	@Override
	public Collection<String> all() { return countsPerItem.keySet(); }

	@Override
	public int instancesOf(String item) {
		Integer count = countsPerItem.get(item);
		return count == null ? 0 : count;
	}

	@Override
	public void clear() {
		super.clear();

		countsPerItem.clear();
	}

}
