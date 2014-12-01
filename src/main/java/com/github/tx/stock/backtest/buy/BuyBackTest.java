package com.github.tx.stock.backtest.buy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.tx.stock.backtest.BackTest;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.entity.Summary;
import com.github.tx.stock.strategy.buy.Buy;

@Component
public class BuyBackTest extends BackTest {
	
	@Autowired
	@Qualifier("afterLimitUpBuy")
	Buy buy;

	public void backTest(String symbol, int beginDate, int endDate) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = indicators.getDates(symbol);
		if (dates.get(dates.size() - 1) <= beginDate) {
			return;
		}
		int date = adjustBeginDate(symbol, beginDate);
		int index = dates.indexOf(date);
		// date -> price
		Map<Integer, Double> dateAndPrice = new HashMap<Integer, Double>();
		while (date <= endDate) {
			if (buy.makeDecision(symbol, date)) {
				if (entitys.size() - index > 5) {// 当前index后必须有够做测试的天数否则不计入
					dateAndPrice.put(date, buy.getPrice(symbol, date));
				}
			}
			index++;
			if (index >= dates.size()) {
				break;
			}
			date = dates.get(index);
		}
		if(!dateAndPrice.isEmpty()){
			calculateEratio(symbol, beginDate, endDate, dateAndPrice);
		}
	}

	private void calculateEratio(String symbol, int beginDate, int endDate,
			Map<Integer, Double> dateAndPrice) {
		Summary summary = new Summary();
		summary.setBegin(beginDate);
		summary.setEnd(endDate);
		summary.setSymbol(symbol);
		summary.setStrategy(this.getClass().getName());
		summary.setHit(dateAndPrice.keySet().size());
		summary.setSpec1(indicators.e_ratio(symbol, dateAndPrice, 1));
		summary.setSpec2(indicators.e_ratio(symbol, dateAndPrice, 2));
		summary.setSpec3(indicators.e_ratio(symbol, dateAndPrice, 3));
		summary.setSpec4(indicators.e_ratio(symbol, dateAndPrice, 4));
		summary.setSpec5(indicators.e_ratio(symbol, dateAndPrice, 5));
		service.insertSummary(summary);
	}
}
