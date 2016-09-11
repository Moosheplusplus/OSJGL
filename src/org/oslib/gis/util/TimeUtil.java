package org.oslib.gis.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

	/**
	 * Used for formatting GPX Timestamps
	 * (<i>YYYY-MM-DD</i><b>T</b><i>HH:mm:ss</i><b>Z</b>). All Timestamps are
	 * in Zulu Timezone (UTC/GMT).
	 *
	 *
	 * @param timestamp The timestamp to format
	 * @return The Date
	 */
	public static Date gpxTime(String timestamp) {
		String[] a = timestamp.split("T");
		String[] b = a[0].split("-");
		String[] c = a[1].split(":");

		int year = softInt(b[0]),
				month = softInt(b[1]),
				day = softInt(b[2]);

		int hour = softInt(c[0]),
				minute = softInt(c[1]),
				second = softInt(c[2]);

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hour, minute, second);
		cal.setTimeZone(TimeZone.getTimeZone(c[2].replaceAll("[\\d+]", "")));
		return cal.getTime();
	}

	public static String gpxTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("Z"));
		return format.format(date).replace(" ", "T")+"Z";
	}

	private static int softInt(String str) {
		try {
			return Integer.parseInt(str.replaceAll("[^\\d+]", ""));
		} catch(NumberFormatException e) {
			return 0;
		}
	}

}
