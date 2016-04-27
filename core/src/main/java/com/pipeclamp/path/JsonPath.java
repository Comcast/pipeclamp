package com.pipeclamp.path;

import java.util.Collection;

import com.jayway.jsonpath.ReadContext;

/**
 *
 * @author Brian Remedios
 *
 * Adapted from https://github.com/jayway/JsonPath
 *
 * @param <V>
 */
public class JsonPath<V extends Object> implements Path<ReadContext, V> {

	private final String path;
	private final boolean hasLoop;
	private final boolean denotesCollection;

	public JsonPath(String thePath, boolean hasLoopFlag, boolean denotesCollectionFlag) {
		path = thePath;
		hasLoop = hasLoopFlag;
		denotesCollection = denotesCollectionFlag;
	}

	@Override
	public V valueVia(ReadContext context) {
		return context.read(path);
	}

	@Override
	public Collection<V> valuesVia(ReadContext context) {
		return context.read(path);
	}

	@Override
	public boolean hasLoop() { return hasLoop; }

	@Override
	public boolean denotesCollection() { return denotesCollection; }

	@Override
	public Path<ReadContext, V> withIndex(int segment, String index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int depth() {
		// TODO Auto-generated method stub
		return -1;
	}
}
