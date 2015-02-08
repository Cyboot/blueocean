package de.timweb.blueocean.sitecreator;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.eclipse.jetty.server.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.timweb.blueocean.Site;
import de.timweb.blueocean.data.CommentData;
import de.timweb.blueocean.data.IssueData;
import de.timweb.blueocean.util.DateHelperUtil;
import de.timweb.blueocean.util.IssueManager;

public class IssueSite implements Site {
	private static final Logger	LOGGER			= LoggerFactory.getLogger(IssueSite.class);
	private final IssueManager	issueManager	= new IssueManager();

	@Override
	public String getHTML(Request request) throws IOException {

		if ("create-issue".equals(request.getParameter("action"))) {
			saveNewIssue(request.getParameter("issue.title"), request.getParameter("issue.text"),
					request.getParameter("issue.date"));
		}
		if ("reply".equals(request.getParameter("action"))) {
			replyComment(request.getParameter("issue-nr"), request.getParameter("comment"));
		}


		return generateHTML(request);
	}

	private void replyComment(String issueNr, String reply) {
		issueNr = issueNr.substring(1);
		LOGGER.info(issueNr + ": " + reply);

		CommentData comment = new CommentData(reply, new Date().getTime());

		issueManager.saveComment(issueNr, comment);
	}

	private void saveNewIssue(String title, String text, String date) {
		issueManager.saveIssue(new IssueData(title, text, date));
	}

	private String generateHTML(Request request) throws IOException {
		String templateHTML = FileUtils.readFileToString(new File("web/issues.html"));

		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("issue.list", generateIssueList());

		replaceMap.putAll(generateMainIssue(request.getParameter("issue-nr")));
		replaceMap.putAll(generateComments(request.getParameter("issue-nr")));

		StrSubstitutor sub = new StrSubstitutor(replaceMap);


		return sub.replace(templateHTML);
	}

	private Map<String, String> generateComments(String issueNr) {
		if (issueNr != null && issueNr.startsWith("#"))
			issueNr = issueNr.substring(1);

		Map<String, String> replaceMap = new HashMap<String, String>();

		StringBuffer buffer = new StringBuffer();
		for (CommentData comment : issueManager.getComments(issueNr)) {
			buffer.append("<tr valign=\"top\"><td valign=\"top\"><div id='comment-text'>")
					.append(comment.getText()).append("</div>");

			buffer.append("<div id='comment-date'>")
					.append(DateHelperUtil.geFullDate(comment.getTimestamp()))
					.append("</div></td></tr>");
		}
		replaceMap.put("issue.comments", buffer.toString());

		return replaceMap;
	}

	private Map<String, String> generateMainIssue(String nr) {
		Map<String, String> replaceMap = new HashMap<String, String>();

		IssueData issue = null;
		if (nr != null) {
			if (nr != null && nr.startsWith("#"))
				nr = nr.substring(1);

			issue = issueManager.getIssueByNr(nr);
		}
		if (issue == null) {
			issue = new IssueData();
			issue.setTimestamp(new Date().getTime());

			issue.setNr(issueManager.getNextIssueNr());
		}

		replaceMap.put("issue.nr", "#" + issue.getNr());
		replaceMap.put("issue.title", issue.getTitle());
		replaceMap.put("issue.text", issue.getText());
		replaceMap.put("issue.date", DateHelperUtil.getDate(issue.getTimestamp()));
		replaceMap.put("issue.category", "");

		return replaceMap;
	}

	private String generateIssueList() {
		StringBuilder builder = new StringBuilder();

		for (IssueData issue : issueManager.getIssueList()) {
			builder.append("<tr onclick=\"document.location = '/issues?issue-nr=")
					.append(issue.getNr()).append("';\"><td><b>#").append(issue.getNr())
					.append("</b> ");

			builder.append(StringUtils.abbreviate(issue.getTitle(), 50)).append("</td></tr>");
		}


		return builder.toString();
	}
}
