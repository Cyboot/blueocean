package de.timweb.blueocean.data;

import org.json.simple.JSONObject;

public class CommentData {
	private String	text;
	private long	timestamp;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public CommentData(String text, long timestamp) {
		this.text = text;
		this.timestamp = timestamp;
	}

	public CommentData(JSONObject json) {
		text = (String) json.get("text");
		timestamp = (long) json.get("date");
	}


	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();

		json.put("text", text);
		json.put("date", timestamp);

		return json;
	}

	@Override
	public String toString() {
		return toJSON().toJSONString();
	}
}
