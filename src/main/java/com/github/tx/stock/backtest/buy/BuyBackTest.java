package com.github.tx.stock.backtest.buy;

import java.util.ArrayList;
import java.util.List;

import com.github.tx.stock.backtest.BackTest;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.entity.Summary;
import com.github.tx.stock.util.NumberUtils;

public abstract class BuyBackTest extends BackTest{

	public abstract boolean hit(String symbol, int date);

	public void backTest(String symbol, int beginDate, int endDate) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = indicators.getDates(symbol);
		if (dates.get(dates.size()-1) <= beginDate) {
			return;
		}
		List<Integer> ztList = new ArrayList<Integer>();
		int i = 0;// 命中次数
		int day1 = 0, day2 = 0, day3 = 0, day4 = 0, day5 = 0;// Nday后的获利次数
		int date = adjustBeginDate(symbol, beginDate);
		int index = dates.indexOf(date);
		while (date <= endDate) {
			if (hit(symbol, date)) {
				if (entitys.size() - index > 5) {// 当前index后必须有够做测试的天数否则不计入
					// 计算1天，2天，3天，4天，5天后的ratio
					Double day1Ratio = indicators.changeRatioAfterNday(symbol,
							date, 1);
					Double day2Ratio = indicators.changeRatioAfterNday(symbol,
							date, 2);
					Double day3Ratio = indicators.changeRatioAfterNday(symbol,
							date, 3);
					Double day4Ratio = indicators.changeRatioAfterNday(symbol,
							date, 4);
					Double day5Ratio = indicators.changeRatioAfterNday(symbol,
							date, 5);
					logger.debug("1天:{},2天:{},3天:{},4天:{},5天:{}", day1Ratio,
							day2Ratio, day3Ratio, day4Ratio, day5Ratio);
					if (day1Ratio > 0) {
						day1++;
					}
					if (day2Ratio > 0) {
						day2++;
					}
					if (day3Ratio > 0) {
						day3++;
					}
					if (day4Ratio > 0) {
						day4++;
					}
					if (day5Ratio > 0) {
						day5++;
					}
					i++;
				}
			}
			index++;
			if (index >= dates.size()) {
				break;
			}
			date = dates.get(index);
		}
		Summary summary = new Summary();
		summary.setBegin(beginDate);
		summary.setEnd(endDate);
		summary.setSymbol(symbol);
		summary.setStrategy(this.getClass().getName());
		summary.setHit(i);
		summary.setLimit(ztList.size());
		summary.setSpec1(Double.valueOf(day1));
		summary.setSpec2(Double.valueOf(day2));
		summary.setSpec3(Double.valueOf(day3));
		summary.setSpec4(Double.valueOf(day4));
		summary.setSpec5(Double.valueOf(day5));
		service.insertSummary(summary);
		logger.debug("====={} {} summary=====", symbol, this.getClass()
				.getName());
		logger.debug("从{}到{}共命中{}次", beginDate, endDate, i);
		logger.debug("其中zt{}次，日期为{}", ztList.size(), ztList.toString());
		logger.debug("1天成功率{}%,2天成功率{}%,3天成功率{}%,4天成功率{}%,5天成功率{}%,",
				NumberUtils.percent(day1, i, 2),
				NumberUtils.percent(day2, i, 2),
				NumberUtils.percent(day3, i, 2),
				NumberUtils.percent(day4, i, 2),
				NumberUtils.percent(day5, i, 2));

	}

}
