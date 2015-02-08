package de.timweb.blueocean.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filename: 001#title-with-some-words.json
 */
public class FileManager {
	private static final Logger	LOGGER		= LoggerFactory.getLogger(FileManager.class);
	private static FileManager	instance	= new FileManager();
	private static String		DIR			= "data/";
	private static File			DIR_FILE	= new File(DIR);

	private final JSONParser	parser		= new JSONParser();

	private FileManager() {
	}

	public void saveJSON(JSONObject object, String title) {
		try {
			FileUtils.write(getFile(title), object.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private File getFile(String title) {
		return new File(DIR + title + ".json");
	}

	public JSONObject getJSON(String title) {
		JSONObject jsonObj = null;
		try {
			String JSONstring = FileUtils.readFileToString(getFile(title));

			jsonObj = (JSONObject) parser.parse(JSONstring);

			LOGGER.info("JSON: " + jsonObj);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObj;
	}

	public List<JSONObject> getAllIssues() {
		List<JSONObject> issueList = new ArrayList<JSONObject>();

		for (File file : FileUtils.listFiles(DIR_FILE, null, false)) {
			try {
				issueList.add((JSONObject) parser.parse(FileUtils.readFileToString(file)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return issueList;
	}

	public String getIssueFileCount() {
		int size = FileUtils.listFiles(getDIR(), null, false).size();

		return StringUtils.leftPad(size + "", 3, "0");
	}

	public File getDIR() {
		return DIR_FILE;
	}

	public static FileManager getInstance() {
		return instance;
	}
}
