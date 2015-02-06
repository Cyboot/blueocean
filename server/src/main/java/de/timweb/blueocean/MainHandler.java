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

import de.timweb.blueocean.sitecreator.CalendarSiteCreator;
import de.timweb.blueocean.sitecreator.HomeSiteCreator;
import de.timweb.blueocean.sitecreator.IssueSiteCreator;
import de.timweb.blueocean.sitecreator.StatsSiteCreator;
import de.timweb.blueocean.sitecreator.WikiSiteCreator;

public class MainHandler extends AbstractHandler {
	private final SiteCreator	calendar	= new CalendarSiteCreator();
	private final SiteCreator	home		= new HomeSiteCreator();
	private final SiteCreator	issue		= new IssueSiteCreator();
	private final SiteCreator	stats		= new StatsSiteCreator();
	private final SiteCreator	wiki		= new WikiSiteCreator();

	private static String[]		sites		= { "", "home", "issues", "calendar", "wiki", "stats" };

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// Logger logger = LoggerFactory.getLogger(MainHandler.class);
		// logger.info("Target: " + target);

		target = target.replace("/", "");
		if (!validReqest(target))
			return;

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
