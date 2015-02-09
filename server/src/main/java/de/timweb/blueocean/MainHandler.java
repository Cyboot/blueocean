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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.timweb.blueocean.sitecreator.CalendarSite;
import de.timweb.blueocean.sitecreator.HomeSite;
import de.timweb.blueocean.sitecreator.IssueSite;
import de.timweb.blueocean.sitecreator.StatsSite;
import de.timweb.blueocean.sitecreator.WikiSite;

public class MainHandler extends AbstractHandler {
	private static final Logger	LOGGER		= LoggerFactory.getLogger(MainHandler.class);

	private final Site			calendar	= new CalendarSite();
	private final Site			home		= new HomeSite();
	private final Site			issue		= new IssueSite();
	private final Site			stats		= new StatsSite();
	private final Site			wiki		= new WikiSite();

	private static String[]		sites		= { "", "home", "issues", "calendar", "wiki", "stats" };

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		target = target.replace("/", "");
		if (!validReqest(target))
			return;

		response.setContentType("text/html;charset=utf-8");
		LOGGER.info("Request: GET /" + target + " => " + baseRequest.getParameterMap());

		if (target.equals(""))
			target = "home";

		String templateHTML = FileUtils.readFileToString(new File("web/main.html"));

		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("tab.home", "");
		replaceMap.put("tab.issues", "");
		replaceMap.put("tab.calendar", "");
		replaceMap.put("tab.wiki", "");
		replaceMap.put("tab.stats", "");
		replaceMap.put("tab." + target, "active");

		String siteHTML = null;
		switch (target) {
		case "home":
			siteHTML = home.getHTML(baseRequest);
			break;
		case "issues":
			siteHTML = issue.getHTML(baseRequest);
			break;
		case "calendar":
			siteHTML = calendar.getHTML(baseRequest);
			break;
		case "wiki":
			siteHTML = wiki.getHTML(baseRequest);
			break;
		case "stats":
			siteHTML = stats.getHTML(baseRequest);
			break;
		default:
			throw new IllegalStateException("Site " + target + " is not handled.");
		}
		replaceMap.put("main", siteHTML);


		StrSubstitutor sub = new StrSubstitutor(replaceMap);
		PrintWriter out = response.getWriter();
		out.write(sub.replace(templateHTML));

		baseRequest.setHandled(true);
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
