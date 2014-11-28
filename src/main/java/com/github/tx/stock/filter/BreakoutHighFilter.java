package com.github.tx.stock.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.TechnicalIndicators;

/**
 * 突破过滤器
 * 
 * @author tangx
 * @since 2014年11月28日
 */

@Component
public class BreakoutHighFilter implements Filter {

	public static final int BREAK_DAYS = 20;

	@Autowired
	TechnicalIndicators indicators;

	@Override
	public boolean doFilter(String symbol, int date) {
		Double highestHigh = indicators.highestHigh(symbol, date, BREAK_DAYS);
		Stock stock = indicators.getSymbolData(symbol, date);
		return stock.getHigh() > highestHigh;
	}

}
