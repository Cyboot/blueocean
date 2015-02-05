package de.timweb.blueocean;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MainHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// Logger logger = LoggerFactory.getLogger(MainHandler.class);
		// logger.info("Target: " + target);

		if (!checkReqest(target))
			return;

		PrintWriter out = response.getWriter();

		out.write("Hello you,");
		out.write("BlueOcean here!");


		baseRequest.setHandled(true);
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}


	private boolean checkReqest(String target) {
		return target.equals("/");
	}
}
