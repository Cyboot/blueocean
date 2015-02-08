package de.timweb.blueocean.data;

import org.json.simple.JSONObject;

import de.timweb.blueocean.util.DateHelperUtil;

public class IssueData {
	private String	nr			= "000";
	private String	title		= "";
	private String	text		= "";
	private long	timestamp	= 0;

	public IssueData() {
	}

	public IssueData(JSONObject json) {
		nr = (String) json.get("nr");
		title = (String) json.get("title");
		text = (String) json.get("text");
		timestamp = (long) json.get("date");
	}

	public IssueData(String title, String text, long timestamp) {
		this.title = title;
		this.text = text;
		this.timestamp = timestamp;
	}

	public IssueData(String title, String text, String date) {
		this.title = title;
		this.text = text;
		this.timestamp = DateHelperUtil.getTimestamp(date);
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("nr", nr);
		json.put("title", title);
		json.put("text", text);
		json.put("date", timestamp);

		return json;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getNr() {
		return nr;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDate() {
		return DateHelperUtil.getDate(timestamp);
	}
}
