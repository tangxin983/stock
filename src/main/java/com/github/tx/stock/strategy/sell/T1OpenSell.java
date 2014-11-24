package com.github.tx.stock.strategy.sell;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.NumberUtils;

/** 
 * 
 * @author tangx
 * @since 2014年11月24日
 */

@Component
public class T1OpenSell extends Sell {
	
	private int sellDate;

	@Override
	public double sell(String symbol, int date, int units) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			logger.debug("{}无数据", symbol);
			return 0;
		}
		List<Integer> dates = indicators.getDates(symbol);
		int index = dates.indexOf(date);
		if (index == -1) {
			logger.debug("{}不是正常日期", date);
			return 0;
		}
		sellDate = dates.get(index+1);//t+1
		Stock stock = entitys.get(index+1);
		return NumberUtils.multiply(units, stock.getOpen());
	}

	@Override
	public int getSellDate() {
		return sellDate;
	}

}
