package com.pipeclamp.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Brian Remedios
 */
public abstract class AbstractPath<T extends Object, V extends Object> implements Path<T, V> {

	private final boolean hasLoop;
	private final boolean denotesCollection;
	private final String[] parts;

	private static final char DefaultSeparator = '/';

	private static boolean denotesLoop(String[] parts) {
		for (String part : parts) {
			if (denotesLoop(part)) return true;
		}
		return false;
	}

	private static boolean denotesCollection(String[] parts) {

		String lastSegment = parts[parts.length-1];
		return denotesLoop(lastSegment);
	}

	private static boolean denotesLoop(String part) {
		return loopBounds(part) != null;
	}

	private static int[] loopBounds(String part) {

		int start = part.indexOf('[');
		if (start < 0) return null;

		int end = part.indexOf(']', start);
		if (end < 0) return null;

		return new int[] { start, end };
	}

	protected static String asKey(String maybeKey) {

		int[] bounds = loopBounds(maybeKey);
		if (bounds == null) return maybeKey;

		return maybeKey.substring(0, bounds[0]);
	}

	protected AbstractPath(String... theParts) {
		parts = theParts;
		hasLoop = denotesLoop(parts);
		denotesCollection = denotesCollection(parts);
	}

	@Override
	public int depth() { return parts.length; }

	@Override
	public V valueVia(T source) {

		if (hasLoop()) {
			// throw error
			return null;
		} else {
			return valueFor(source, 0);
		}
	}

	protected String[] partsReplacing(int index, String segment) {

		String[] copy = new String[parts.length];
		System.arraycopy(parts, 0, copy, 0, parts.length);
		copy[index] = segment;
		return copy;
	}

	protected abstract V valueFor(T source, int i) ;

	@Override
	public Collection<V> valuesVia(T source) {

		if (hasLoop()) {
			Collection<V> items = new ArrayList<V>();
			collectItemsFor(source, items, 0);
			return items;
		} else {
			return null;
		}
	}

	protected abstract void collectItemsFor(T source, Collection<V> items, int i);

	protected boolean isLast(int depth) {
		return parts.length == depth+1;
	}

	protected boolean denotesCollection(int index) {
		return loopBounds(parts[index]) != null;
	}

	protected String partAt(int index) { return parts[index]; }

	@Override
	public boolean hasLoop() { return hasLoop; }

	@Override
	public boolean denotesCollection() { return denotesCollection; }

	@Override
	public String toString() {

		if (parts.length == 0) return "(root)";
		if (parts.length == 1) return parts[0];

		StringBuilder sb = new StringBuilder(parts[0]);
		for (int i=1; i<parts.length; i++) {
			sb.append(DefaultSeparator).append(parts[i]);
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {

		int code = 17;
		for (String part : parts) {
			code += part.hashCode();
		}
		return code;
	}

	@Override
	public boolean equals(Object item) {

		if (item == null) return false;
		if (item == this) return true;

		if (item instanceof AbstractPath) {
			AbstractPath<?, ?> other = (AbstractPath<?,?>)item;
			if (other.parts.length != parts.length) return false;
			return Arrays.equals(other.parts, parts);
		}

		return false;
	}
}
