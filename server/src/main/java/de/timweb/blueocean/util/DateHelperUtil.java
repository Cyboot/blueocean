package de.timweb.blueocean.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelperUtil {
	private static final DateFormat	df		= new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat	df_full	= new SimpleDateFormat("HH:mm dd.MM.yyyy");

	/**
	 * 
	 * @param date
	 *            - Format: dd.MM.YYYY
	 * @return
	 */
	public static long getTimestamp(String date) {
		try {
			return df.parse(date).getTime();
		} catch (ParseException e) {
			throw new IllegalArgumentException("Date is not valid: " + date);
		}
	}

	public static String getDate(long timestamp) {
		return df.format(new Date(timestamp));
	}

	public static String geFullDate(long timestamp) {
		return df_full.format(new Date(timestamp));
	}

}
