package de.timweb.blueocean;

import java.io.IOException;

import org.eclipse.jetty.server.Request;

public interface Site {
	public String getHTML(Request request) throws IOException;
}
