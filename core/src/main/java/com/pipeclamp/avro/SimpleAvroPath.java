package com.pipeclamp.avro;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.avro.generic.GenericRecord;

import com.pipeclamp.api.PathBuilder;
import com.pipeclamp.path.AbstractPath;
import com.pipeclamp.path.Path;

/**
 *
 * @author Brian Remedios
 */
public class SimpleAvroPath<O extends Object> extends AbstractPath<GenericRecord, O> {

	public static final PathBuilder Builder = new PathBuilder() {

		public Path<GenericRecord, ?> pathFor(String[] parts) {
			return new SimpleAvroPath(Arrays.asList(parts));
		}
	};
	
	public SimpleAvroPath(List<String> thePath) {
		super(thePath.toArray(new String[thePath.size()]));
	}

	public SimpleAvroPath(String... thePath) {
		super(thePath);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected O valueFor(GenericRecord record, int pathIndex) {

		String key = partAt(pathIndex);

		if (isLast(pathIndex)) {
			return (O)record.get(asKey(key));
		}

		Object item = record.get(key);

		if (item instanceof GenericRecord) {
			return valueFor((GenericRecord)item, pathIndex+1);
		}

		// shouldn't reach here
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void collectItemsFor(GenericRecord record, Collection<O> collector, int pathIndex) {

		String key = partAt(pathIndex);

		if (isLast(pathIndex)) {
			if (denotesCollection(pathIndex)) {
				Object result = record.get(asKey(key));
				if (result == null) return;
				if (result instanceof Collection) {
					collector.addAll((Collection<O>) result);
					} else {
						throw new IllegalArgumentException("Value at " + key + " is not an array");
					}
				return;
				}
			collector.add( (O) record.get(key) );
		}

		Object item = record.get(asKey(key));

		if (item instanceof Collection) {
			for (GenericRecord child : (Collection<GenericRecord>)item) {
				collectItemsFor(child, collector, pathIndex+1);
			}
			return;
		}

		if (item instanceof GenericRecord) {
			collectItemsFor((GenericRecord)item, collector, pathIndex+1);
		}
		// shouldn't reach here
	}

	@Override
	public Path<GenericRecord, O> withIndex(int segmentIdx, String index) {

		String segment = asKey(partAt(segmentIdx));
		String[] newPath = partsReplacing(segmentIdx, segment + "[" + index + "]");
		return new SimpleAvroPath<O>(newPath);
	}

}
