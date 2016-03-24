package com.br.jstree;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

/**
 * Generates formatted parent-child text for use with JsTree input data.
 * Concrete subclasses should be able to invoke all protected methods 
 * below to assemble the structure they need.
 * 
 * @see https://www.jstree.com/docs/json/
 * @author Brian Remedios
 */
public abstract class AbstractJsTreeComposer {

	private StringBuilder buffer;
	private int indentDepth = 0;
	private final String indentText;
	
	protected static final String DefaultIndentText = "  ";
	
	protected static String formatType(String typeStr) {
		return "<i><small>(" + typeStr.toLowerCase() + ")</small></i>";
	}

	protected AbstractJsTreeComposer(String theIndentText) {
		buffer = new StringBuilder(500);
		indentText = theIndentText;
	}

	protected void incrementDepth() { indentDepth++; }
	
	protected void decrementDepth() { indentDepth--; }
	
	protected String bufferAt(int position) { return buffer.substring(position); }

	protected void appendIds(String localId, String parentId) {
		append("id", localId, true);
		append("parent", parentId, true);
	}

	protected void append(String key, String value) {
		buffer.append('"').append(key).append("\": \"").append(value).append('"');
	}
	
	protected void append(String key, String value, boolean withComma) {
	
		append(key, value);
		buffer.append(withComma ? ", " : " ");
	}

	protected void appendDoc(String docText) {
	
		if (docText == null) return;
		
		appendAsData("doc", docText);
	}

	protected void startNewLine() {
		buffer.append(",\n");
		for (int i=0; i<indentDepth; i++) buffer.append(indentText);
		buffer.append('{');
	}
	
	protected void endLine() {
		buffer.append('}');
	}

	protected void appendAsData(String key, String value) {
		
		if (value == null) return;
		
		appendAsData(
				Arrays.<Entry<String, String>>asList(
						new AbstractMap.SimpleEntry<String, String>(key, value)
						)
				);
	}
	
	protected void appendAsData(List<Entry<String, String>> tuples) {
		
		if (tuples.isEmpty()) return;

		buffer.append(", \"data\": {");
		
		append(tuples.get(0).getKey(), tuples.get(0).getValue());
		for (int i=1; i<tuples.size(); i++) {
			buffer.append(", ");
			append(tuples.get(i).getKey(), tuples.get(i).getValue());
		}
		buffer.append('}');
	}

}