package com.github.tx.stock.strategy.buy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class BreakHighBuy extends Buy {

	private static final int BREAK_DAYS = 20;

	private static final int RATIO = 2;

	public boolean buy(String symbol, int date) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			logger.debug("{}无数据", symbol);
			return false;
		}
		List<Integer> dates = indicators.getDates(symbol);
		int index = dates.indexOf(date);
		if (index == -1) {
			logger.debug("{}不是正常日期", date);
			return false;
		}
		if (index < BREAK_DAYS) {
			logger.debug("{}的回测日太早，需推后", symbol);
			return false;
		}
		Stock stock = entitys.get(index);
		Double highestHigh = indicators.highestHigh(symbol, date, BREAK_DAYS);
		Double volumeRatio = indicators.volumeRatio(symbol, date);
		Double changeRatio = indicators.changeRatio(symbol, date);
		if (stock.getHigh() > highestHigh && volumeRatio >= RATIO) {
			logger.debug(
					"以{}为基准, change为{}, high为{}, vratio为{}, 前{}天的最高high为{}",
					stock.getDate(), changeRatio, stock.getHigh(), volumeRatio,
					BREAK_DAYS, highestHigh);
			return true;
		}
		return false;
	}

}
