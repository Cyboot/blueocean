package de.timweb.blueocean.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filename: 001#title-with-some-words.json
 */
public class FileManager {
	private static final Logger	LOGGER			= LoggerFactory.getLogger(FileManager.class);
	private static FileManager	instance		= new FileManager();
	private static String		DIR_ISSUE		= "data/issue/";
	private static String		DIR_COMMENT		= "data/comment/";
	private static File			DIR_FILE_ISSUE	= new File(DIR_ISSUE);

	private final JSONParser	parser			= new JSONParser();

	private FileManager() {
	}

	public void saveComment(JSONArray jsonArray, String issueNr) {
		try {
			FileUtils.write(new File(DIR_COMMENT + "#" + issueNr + ".json"),
					jsonArray.toJSONString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void saveIssue(JSONObject object, String title) {
		try {
			FileUtils.write(new File(DIR_ISSUE + title + ".json"), object.toJSONString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<JSONObject> getAllIssues() {
		List<JSONObject> issueList = new ArrayList<JSONObject>();

		for (File file : FileUtils.listFiles(DIR_FILE_ISSUE, null, false)) {
			try {
				issueList.add((JSONObject) parser.parse(FileUtils.readFileToString(file)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return issueList;
	}

	public JSONArray getCommentJSON(String issueNr) {
		try {
			return (JSONArray) parser.parse(FileUtils.readFileToString(new File(DIR_COMMENT + "#"
					+ issueNr + ".json")));
		} catch (ParseException | IOException e) {
			return new JSONArray();
		}
	}

	public int getIssueFileCount() {
		return FileUtils.listFiles(DIR_FILE_ISSUE, null, false).size();
	}

	public static FileManager getInstance() {
		return instance;
	}
}
