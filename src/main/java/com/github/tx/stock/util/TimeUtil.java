package com.github.tx.stock.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * 
 * @author tangx
 * @since 2014年8月5日
 */
public class TimeUtil {

	private static final String DEFAULT_PATTERN = "yyyyMMdd";

	/**
	 * 获取某个日期days天后的日期字符串表示（yyyyMMdd）
	 * @param date 某个日期(格式为yyyyMMdd)
	 * @param days
	 * @return
	 */
	public static String getAfterDateStr(String date, int days) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DEFAULT_PATTERN);
		DateTime dt = DateTime.parse(date, fmt);
		return dt.plusDays(days).toString(DEFAULT_PATTERN);
	}
	
	/**
	 * 获取某个日期days天后的日期字符串表示（yyyyMMdd）
	 * @param date 某个日期(格式为yyyyMMdd)
	 * @param days
	 * @return
	 */
	public static String getBeforeDateStr(String date, int days) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(DEFAULT_PATTERN);
		DateTime dt = DateTime.parse(date, fmt);
		return dt.minusDays(days).toString(DEFAULT_PATTERN);
	}

}
