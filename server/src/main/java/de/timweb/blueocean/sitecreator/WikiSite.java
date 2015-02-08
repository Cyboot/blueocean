package de.timweb.blueocean.sitecreator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Request;

import de.timweb.blueocean.Site;

public class WikiSite implements Site {

	@Override
	public String getHTML(Request request) throws IOException {
		return FileUtils.readFileToString(new File("web/wiki.html"));
	}

}
