package com.github.tx.stock.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.service.DbService;

/**
 * 技术指标
 * 
 * @author tangx
 * @since 2014年11月21日
 */

@Component
public class TechnicalIndicators {

	@Autowired
	private DbService service;

	// symbol -> dates
	private Map<String, List<Integer>> datesMap = new ConcurrentHashMap<String, List<Integer>>();

	public List<Stock> getSymbolData(String symbol) {
		return service.getSymbolData(symbol);
	}
	
	public Stock getSymbolData(String symbol, int date) {
		List<Stock> entitys = service.getSymbolData(symbol);
		if(entitys.size() == 0){
			return null;
		}
		List<Integer> dates = getDates(symbol);
		if(!dates.contains(date)){
			return null;
		}
		return entitys.get(dates.indexOf(date));
	}

	/**
	 * 获取所有的日期列表
	 * 
	 * @param symbol
	 *            代码
	 * @return
	 */
	public List<Integer> getDates(String symbol) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = CollectionUtils.extractToList(entitys, "date",
				true);
		if (!datesMap.containsKey(symbol)) {
			datesMap.put(symbol, dates);
		}
		return datesMap.get(symbol);
	}

	/**
	 * 最近N天最高close
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @param n
	 * @return
	 */
	public Double highestClose(String symbol, int date, int n) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < n) {
			throw new IllegalArgumentException("highestClose:index必须大等于n");
		}
		List<Stock> sub = entitys.subList(index - n, index);
		List<Double> closes = CollectionUtils.extractToList(sub, "close", true);
		return Collections.max(closes);
	}

	/**
	 * 最近N天最高价
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @param n
	 * @return
	 */
	public Double highestHigh(String symbol, int date, int n) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < n) {
			throw new IllegalArgumentException("highestHigh:index必须大等于n");
		}
		List<Stock> sub = entitys.subList(index - n, index);
		List<Double> highs = CollectionUtils.extractToList(sub, "high", true);
		return Collections.max(highs);
	}

	/**
	 * 量比(前5天)
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @return
	 */
	public Double volumeRatio(String symbol, int date) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < 5) {
			throw new IllegalArgumentException("volumeRatio:index必须大等于5");
		}
		Stock today = entitys.get(index);
		Long sumVolume = 0L;
		for (int i = 1; i <= 5; i++) {
			Stock stock = entitys.get(index - i);
			sumVolume += stock.getVolume();
		}
		Double averageVolume = NumberUtils.divide(sumVolume, 5, 2);
		return NumberUtils.divide(today.getVolume(), averageVolume, 2);
	}

	/**
	 * 涨幅
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @return
	 */
	public Double changeRatio(String symbol, int date) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < 1) {
			throw new IllegalArgumentException("changeRatio:index必须大等于1");
		}
		Stock today = entitys.get(index);
		Stock yesterday = entitys.get(index - 1);
		Double minus = NumberUtils.subtract(today.getClose(), yesterday.getClose());
		return NumberUtils.percent(minus, yesterday.getClose(), 2);
	}

	/**
	 * 基准日N天后的涨幅
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            基准日
	 * @param n
	 * @return
	 */
	public Double changeRatioAfterNday(String symbol, int date, int n) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if ((index + n) >= entitys.size()) {
			throw new IllegalArgumentException(
					"changeRatioAfterNday:index+n超出数组界限");
		}
		Stock today = entitys.get(index);
		Stock afterNday = entitys.get(index + n);
		Double minus = NumberUtils.subtract(afterNday.getClose(), today.getClose());
		return NumberUtils.percent(minus, today.getClose(), 2);
	}

	/**
	 * 简单移动平均值
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @param n
	 * @return
	 */
	public Double sma(String symbol, int date, int n) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < (n - 1)) {
			throw new IllegalArgumentException("sma:index必须大等于n-1");
		}
		Double sumClose = 0d;
		for (int i = 0; i < n; i++) {
			Stock stock = entitys.get(index - i);
			sumClose += stock.getClose();
		}
		return NumberUtils.divide(sumClose, n, 2);
	}

	/**
	 * 波动幅度均值
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @param n
	 * @return
	 */
	public Double atr(String symbol, int date, int n) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < (n - 1)) {
			throw new IllegalArgumentException("atr:index必须大等于n-1");
		}
		Double sum = 0d;
		for (int i = 0; i < n; i++) {
			Stock stock = entitys.get(index - i);
			sum += mtr(symbol, stock.getDate());
		}
		return NumberUtils.divide(sum, n, 2);
	}

	/**
	 * 波动幅度
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            日期
	 * @return
	 */
	public Double mtr(String symbol, int date) {
		List<Stock> entitys = service.getSymbolData(symbol);
		List<Integer> dates = getDates(symbol);
		if (!dates.contains(date)) {
			throw new IllegalArgumentException("日期" + date + "不存在");
		}
		int index = dates.indexOf(date);
		if (index < 1) {
			throw new IllegalArgumentException("mtr:index必须大等于1");
		}
		Stock today = entitys.get(index);
		Stock yesterday = entitys.get(index - 1);
		Double minus1 = NumberUtils.subtract(today.getHigh(), today.getLow());
		Double minus2 = Math.abs(NumberUtils.subtract(yesterday.getClose(),
				today.getHigh()));
		Double minus3 = Math.abs(NumberUtils.subtract(yesterday.getClose(),
				today.getLow()));
		return Math.max(minus1, Math.max(minus2, minus3));
	}

}
