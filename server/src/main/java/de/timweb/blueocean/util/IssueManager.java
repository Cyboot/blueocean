package de.timweb.blueocean.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.timweb.blueocean.data.CommentData;
import de.timweb.blueocean.data.IssueData;
import de.timweb.blueocean.io.FileManager;

public class IssueManager {
	private static FileManager	fileManager	= FileManager.getInstance();

	public void saveIssue(IssueData issue) {
		String fileName = "";
		String issueNR = getNextIssueNr();
		issue.setNr(issueNR);

		fileName += issueNR;
		fileName += "#" + StringUtils.abbreviate(issue.getTitle(), 50);

		fileManager.saveIssue(issue.toJSON(), fileName);


		// Collection files = FileUtils.listFiles(fileManager.getDIR(), new
		// RegexFileFilter("^(.*?)"),
		// DirectoryFileFilter.DIRECTORY);
	}

	public String getNextIssueNr() {
		return StringUtils.leftPad(fileManager.getIssueFileCount() + 1 + "", 3, "0");

	}

	/**
	 * return the issue by NR or null if it does not exist
	 * 
	 * @param nr
	 * @return
	 */
	public IssueData getIssueByNr(String nr) {
		for (IssueData issue : getIssueList()) {
			if (issue.getNr().equals(nr))
				return issue;
		}
		return null;
	}

	public List<IssueData> getIssueList() {
		List<IssueData> issueList = new ArrayList<IssueData>();
		for (JSONObject json : fileManager.getAllIssues()) {
			issueList.add(new IssueData(json));
		}

		return issueList;
	}

	public List<CommentData> getComments(String issueNr) {
		List<CommentData> commentList = new ArrayList<CommentData>();

		JSONArray commentJSON = fileManager.getCommentJSON(issueNr);

		for (Object json : commentJSON) {
			commentList.add(new CommentData((JSONObject) json));
		}

		return commentList;
	}

	@SuppressWarnings("unchecked")
	public void saveComment(String issueNr, CommentData newComment) {
		JSONArray json = new JSONArray();

		for (CommentData comment : getComments(issueNr)) {
			json.add(comment);
		}
		json.add(newComment);

		fileManager.saveComment(json, issueNr);
	}
}
