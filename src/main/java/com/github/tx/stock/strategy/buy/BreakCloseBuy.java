package com.github.tx.stock.strategy.buy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.entity.Summary;
import com.github.tx.stock.util.NumberUtils;
import com.github.tx.stock.util.TimeUtil;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class BreakCloseBuy extends Buy {

	private static final int BREAK_DAYS = 20;

	private static final int RATIO = 2;

	private static final int BEGIN_DATE = 20110101;// 起始回测日

	private static final int END_DATE = 20141111;// 结束回测日

	@Override
	public void buy(String symbol) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = indicators.getDates(symbol);
		List<Integer> ztList = new ArrayList<Integer>();
		int i = 0, index = 0;
		int day1 = 0, day2 = 0, day3 = 0, day4 = 0, day5 = 0;// Nday后的获利次数
		int date = BEGIN_DATE;
		while (date != END_DATE) {
			if (dates.contains(date)) {
				index = dates.indexOf(date);
				if (index < BREAK_DAYS) {
					logger.info("代码{}的回测日太早，需推后", symbol);
					return;
				}
				Stock stock = entitys.get(index);
				Double highestClose = indicators.highestClose(symbol, date,
						BREAK_DAYS);
				Double volumeRatio = indicators.volumeRatio(symbol, date);
				Double changeRatio = indicators.changeRatio(symbol, date);
				if (stock.getClose() > highestClose && volumeRatio >= RATIO) {
					if (changeRatio >= 9.95) {
						ztList.add(date);
					}
					logger.debug(
							"以{}为基准, change为{}, close为{}, vratio为{}, 前{}天的最高close为{}",
							stock.getDate(), changeRatio, stock.getClose(),
							volumeRatio, BREAK_DAYS, highestClose);
					if (entitys.size() - index > 5) {// 当前index后必须有够做测试的天数否则不计入
						// 计算1天，2天，3天，4天，5天后的ratio
						Double day1Ratio = indicators.changeRatioAfterNday(
								symbol, date, 1);
						Double day2Ratio = indicators.changeRatioAfterNday(
								symbol, date, 2);
						Double day3Ratio = indicators.changeRatioAfterNday(
								symbol, date, 3);
						Double day4Ratio = indicators.changeRatioAfterNday(
								symbol, date, 4);
						Double day5Ratio = indicators.changeRatioAfterNday(
								symbol, date, 5);
						logger.debug("1天:{},2天:{},3天:{},4天:{},5天:{}",
								day1Ratio, day2Ratio, day3Ratio, day4Ratio,
								day5Ratio);
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
			}
			date = Integer.valueOf(TimeUtil.getAfterDateStr(
					String.valueOf(date), 1));
		}
		Summary summary = new Summary();
		summary.setBegin(BEGIN_DATE);
		summary.setEnd(END_DATE);
		summary.setSymbol(symbol);
		summary.setStrategy("breakcloseBuy");
		summary.setHit(i);
		summary.setLimit(ztList.size());
		summary.setSpec1(Double.valueOf(day1));
		summary.setSpec2(Double.valueOf(day2));
		summary.setSpec3(Double.valueOf(day3));
		summary.setSpec4(Double.valueOf(day4));
		summary.setSpec5(Double.valueOf(day5));
		service.insertSummary(summary);
		logger.debug("====={} breakcloseBuy summary=====", symbol);
		logger.debug("从{}到{}共命中{}次", BEGIN_DATE, END_DATE, i);
		logger.debug("其中zt{}次，日期为{}", ztList.size(), ztList.toString());
		logger.debug("1天成功率{}%,2天成功率{}%,3天成功率{}%,4天成功率{}%,5天成功率{}%,",
				NumberUtils.percent(day1, i, 2),
				NumberUtils.percent(day2, i, 2),
				NumberUtils.percent(day3, i, 2),
				NumberUtils.percent(day4, i, 2),
				NumberUtils.percent(day5, i, 2));
	}

}
