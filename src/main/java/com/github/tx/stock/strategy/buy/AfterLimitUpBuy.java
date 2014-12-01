package com.github.tx.stock.strategy.buy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.filter.LimitUpFilter;
import com.github.tx.stock.util.NumberUtils;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class AfterLimitUpBuy extends Buy {

	private double price;

	@Autowired
	private LimitUpFilter filter;
	
	public boolean makeDecision(String symbol, int date) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			logger.debug("{}无数据", symbol);
			return false;
		}
		List<Integer> dates = indicators.getDates(symbol);
		int index = dates.indexOf(date);
		if (index == -1) {
			logger.debug("日期{}不存在", date);
			return false;
		}
		if (index - 1 - LimitUpFilter.n < 0) {
			return false;
		}
		if(!filter.doFilter(symbol, date)){
			return false;
		}
		Stock stock = entitys.get(index);
		price = NumberUtils.divide(NumberUtils.add(stock.getHigh(), stock.getLow()), 2, 2);//当天平均价
		logger.info("{}, {}入市, 价格{}", symbol, date, price);
		return true;
	}

	@Override
	public double getPrice(String symbol, int date) {
		return price;
	}

}
