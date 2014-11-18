package com.github.tx.stock.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tangx
 * @since 2014年11月18日
 */

public class SysUtil {

	private static final String URL_PATTERN = "http://table.finance.yahoo.com/table.csv?s=%s";

	private static Logger logger = LoggerFactory.getLogger(SysUtil.class);

	public static List<String> getSymbolList() {
		List<String> symbols = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(
					"http://quote.eastmoney.com/stocklist.html").get();
			Elements links = doc.select("a[href]"); // a with href
			// 正则表达式
			String regex = "sh\\d{6}|sz\\d{6}";
			Pattern pattern = Pattern.compile(regex);
			for (Element e : links) {
				Matcher matcher = pattern.matcher(e.attr("href"));
				if (matcher.find()) {
					symbols.add(matcher.group());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return symbols;
	}

	public static Iterable<CSVRecord> getCsvRecord(String symbol, int timeout) {
		Iterable<CSVRecord> records = null;
		String url = "";
		if (symbol.indexOf("sz") != -1) {
			url = String.format(URL_PATTERN, symbol.substring(2) + ".sz");
		} else if (symbol.indexOf("sh") != -1) {
			url = String.format(URL_PATTERN, symbol.substring(2) + ".ss");
		}
		logger.info(url);
		try {
			URL stockURL = new URL(url);
			URLConnection con = stockURL.openConnection();
			con.setConnectTimeout(timeout);
			con.setReadTimeout(timeout);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			records = CSVFormat.EXCEL.parse(reader);
		} catch (IOException e) {
			logger.error("error symbol:{},cause:{}", symbol, e);
		}
		return records;
	}
}
