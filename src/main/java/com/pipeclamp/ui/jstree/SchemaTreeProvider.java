package com.pipeclamp.ui.jstree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;

/**
 * 
 * @author Brian Remedios
 */
public class SchemaTreeProvider implements WebSocketConnectionCallback {

	private final String schemaPath;
	
	public SchemaTreeProvider(String theSchemaPath) { 
		schemaPath = theSchemaPath;
	}

	@Override
	public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
		
		channel.getReceiveSetter().set(new AbstractReceiveListener() {

			@Override
			protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
				
				String jsonContent = getTreeContent(message.getData());
				if (jsonContent == null) return;
				
				for (WebSocketChannel session : channel.getPeerConnections()) {
					WebSockets.sendText(jsonContent, session, null);
				}
			}
		});
		channel.resumeReceives();
	}

	private String getTreeContent(String messageData) {

//		System.out.println("Received: " + messageData);
		
		if (!messageData.endsWith(".avsc")) return null;
		
		String filePath = schemaPath + messageData;
		String json = null;
		
		try {
			json = new String(Files.readAllBytes(Paths.get(filePath)));
			} catch (IOException ioe) {
				return null;
			}
		
		AvroSchemaTreeComposer comp = new AvroSchemaTreeComposer();
		String jsonResult = comp.render(json);

		System.out.println(jsonResult);

		return jsonResult;
	}
}
