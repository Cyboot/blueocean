package de.timweb.blueocean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MainHandler extends AbstractHandler {
	private static String[]	sites	= { "", "issues", "keep", "calendar", "wiki", "stats" };

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// Logger logger = LoggerFactory.getLogger(MainHandler.class);
		// logger.info("Target: " + target);

		target = target.replace("/", "");
		if (!validReqest(target))
			return;

		if (target.equals(""))
			target = "issues";

		String templateHTML = FileUtils.readFileToString(new File("../web/main.html"));
		String siteHTML = FileUtils.readFileToString(new File("../web/" + target + ".html"));

		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("main", siteHTML);
		replaceMap.put("tab.issues", "");
		replaceMap.put("tab.calendar", "");
		replaceMap.put("tab.wiki", "");
		replaceMap.put("tab.stats", "");

		replaceMap.put("tab." + target, "active");

		StrSubstitutor sub = new StrSubstitutor(replaceMap);


		PrintWriter out = response.getWriter();
		out.write(sub.replace(templateHTML));


		baseRequest.setHandled(true);
		response.setCharacterEncoding("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}


	private boolean validReqest(String target) {
		for (String str : sites) {
			if (str.equals(target))
				return true;
		}
		return false;
	}
}
