package com.github.tx.stock.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlParser {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void test() throws IOException {
		Document doc = Jsoup.connect(
				"http://quote.eastmoney.com/stocklist.html").get();
		Elements links = doc.select("a[href]"); // a with href
		// 正则表达式
		String regex = "sh\\d{6}|sz\\d{6}";
		Pattern pattern = Pattern.compile(regex);
		List<String> stocklist = new ArrayList<String>();
		for (Element e : links) {
			Matcher matcher = pattern.matcher(e.attr("href"));
			if (matcher.find()) {
				stocklist.add(matcher.group());
			}
		}
		for(String stock : stocklist){
			logger.info(stock);
		}
	}

}
