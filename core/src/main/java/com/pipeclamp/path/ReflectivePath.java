package com.pipeclamp.path;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Path parts denote method names to call to retrieve the child elements.
 *
 * @author Brian Remedios
 */
public class ReflectivePath<I,O extends Object> extends AbstractPath<I, O> {

	private static Object getFrom(Object source, String accessor) {

		if (source == null) return null;

		try {
			Method method = source.getClass().getMethod(accessor);
			return method.invoke(source);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
	}

	public ReflectivePath(String... thePath) {
		super(thePath);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected O valueFor(Object record, int pathIndex) {

		String key = partAt(pathIndex);

		if (isLast(pathIndex)) {
			return (O)getFrom(record, asKey(key));
		}

		Object item = getFrom(record, key);

		return valueFor(item, pathIndex+1);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void collectItemsFor(I source, Collection<O> collector, int pathIndex) {

		String key = partAt(pathIndex);

		if (isLast(pathIndex)) {
			if (denotesCollection(pathIndex)) {
				Object result = getFrom(source, asKey(key));
				if (result == null) return;
				if (result instanceof Collection) {
					collector.addAll((Collection<O>) result);
					} else {
						throw new IllegalArgumentException("Value at " + key + " is not an array");
					}
				return;
				}
			collector.add( (O) getFrom(source, key) );
			return;
		}

		Object item = getFrom(source, asKey(key));

		if (item instanceof Collection) {
			for (I child : (Collection<I>)item) {
				collectItemsFor(child, collector, pathIndex+1);
			}
			return;
		}

		if (item instanceof Map) {
			//for (I child : (Map)item) {	 TODO
			//	collectItemsFor(child, collector, pathIndex+1);
			//}
			return;
		}
		
		collectItemsFor((I)item, collector, pathIndex+1);
		// shouldn't reach here
	}

	@Override
	public Path<I, O> withIndex(int segment, String index) {
		// TODO Auto-generated method stub
		return null;
	}

}
