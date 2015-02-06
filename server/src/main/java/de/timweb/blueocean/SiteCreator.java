package de.timweb.blueocean;

import java.io.IOException;

import org.eclipse.jetty.server.Request;

public interface SiteCreator {
	public String getHTML(Request request) throws IOException;
}
