/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.br.jstree;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

/**
 * @author Brian Remedios
 */
public class JSONServer {

	private Undertow undertow;

	private static final String MainPage = "index.html";
	private static final String SchemaPath = "schemas/";
	private static final String[] JavascriptFiles = new String[] { "jstree.js", "jstree.min.js", "jquery.js" };
	
	public void setup() {

		undertow = Undertow.builder()
				.addHttpListener(8080, "localhost")
	//			.setHandler(path().addPrefixPath("/images", resource(new FileResourceManager(new File("/"), 0))))	
				.setHandler(path()
						.addPrefixPath("/myapp", websocket(new SchemaTreeProvider(SchemaPath)))
						.addPrefixPath("/", resource(new ClassPathResourceManager(JSONServer.class.getClassLoader(), JSONServer.class.getPackage()))
								.addWelcomeFiles(MainPage)
								.addWelcomeFiles(JavascriptFiles)
								)
						)
				.build();

		undertow.start();
	}

	public static void main(final String[] args) {

		JSONServer server = new JSONServer();
		server.setup();
	}
}
