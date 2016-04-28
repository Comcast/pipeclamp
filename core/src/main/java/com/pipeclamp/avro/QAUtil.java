package com.pipeclamp.avro;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.Schema;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.pipeclamp.api.Parameter;

/**
 *
 * @author Brian Remedios
 */
public class QAUtil {

	private static final String ArgsKey = "args";
	private static final String ArgNameKey = "name";
	private static final String ArgValueKey = "value";

	public static final String IdKey = "id";
	public static final String DocKey = "doc";

	protected QAUtil() {
		super();
	}

	protected static Map<String, String> argumentsOn(JsonNode node) {

		Map<String, String> argValues = new HashMap<>();
		
		JsonNode argsNode = node.get(ArgsKey);
		if (argsNode == null) return argValues;

		if (!argsNode.isArray()) {
			System.err.println("invalid argument node, must be an array");
			return argValues;
		}

		ArrayNode args = (ArrayNode)argsNode;

		final int argCount = args.size();

		for (int i=0; i<argCount; i++) {
			String[] tuple = getArgs(args.get(i));
			argValues.put(tuple[0], tuple[1]);
		}

		return argValues;
	}

	private static String[] getArgs(JsonNode node) {

		if (!node.isObject()) {
			System.err.println("invalid argument node, must be an object");
			return null;
		}

		if (!ensurePresenceOf(node, ArgNameKey, ArgValueKey)) {
			return null;
		}

		return new String[] {
			node.get(ArgNameKey).asText(),
			node.get(ArgValueKey).asText()
			};
	}

	static boolean ensurePresenceOf(JsonNode node, String... childNodes) {

		for (String childNodeId : childNodes) {
			if (node.has(childNodeId)) continue;
			System.err.println("Missing node: " + childNodeId);
			return false;
		}
		return true;
	}

	// ---------------------- editing ----------------------

	protected static void addParameters(ObjectNode cstNode, Map<Parameter<?>, Object> params) {
		ArrayNode argsNode = JsonNodeFactory.instance.arrayNode();
		cstNode.put(ArgsKey, argsNode);

		for (Entry<Parameter<?>, Object> entry : params.entrySet()) {
			ObjectNode argNode = JsonNodeFactory.instance.objectNode();
			argNode.put(ArgNameKey, entry.getKey().id());
			argNode.put(ArgValueKey, String.valueOf(entry.getValue()));
			argsNode.add(argNode);
		}
	}

	protected static ArrayNode getOrCreateArrayNode(Schema schema, String childId) {

		ArrayNode node = (ArrayNode)schema.getJsonProp(childId);
		if (node != null) return node;

		node = JsonNodeFactory.instance.arrayNode();
		schema.addProp(childId, node);

		return node;
	}

}