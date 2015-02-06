package de.timweb.blueocean;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
	public static void main(String[] args) throws Exception {
		PropertyConfigurator.configure("log4j.properties");

		Logger logger = LoggerFactory.getLogger(WebServer.class);

		logger.info("Starting BlueOcean Webserver");

		SelectChannelConnector connector = new SelectChannelConnector();
		Server server = new Server();
		connector.setPort(7777);
		server.addConnector(connector);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[] { "main.html" });

		resourceHandler.setResourceBase("web");

		HandlerList handerList = new HandlerList();
		handerList.setHandlers(new Handler[] { new MainHandler(), resourceHandler });

		server.setHandler(handerList);

		server.start();
		server.join();
	}
}
