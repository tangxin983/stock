package com.github.tx.stock.backtest.trade;

import java.util.List;

import com.github.tx.stock.backtest.BackTest;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.strategy.buy.Buy;
import com.github.tx.stock.strategy.sell.Sell;

public class TradeSystem extends BackTest{

	Buy buy;

	Sell sell;

	public void trade(String symbol, int capital, int beginDate, int endDate) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = indicators.getDates(symbol);
		if (dates.get(dates.size()-1) <= beginDate) {
			return;
		}
		int date = adjustBeginDate(symbol, beginDate);
		int index = dates.indexOf(date);
		while (date <= endDate) {
			if(buy.buy(symbol, date)){//入市
				//头寸
			}
			
			
			index++;
			if (index >= dates.size()) {
				break;
			}
			date = dates.get(index);
		}
		
	}
}
