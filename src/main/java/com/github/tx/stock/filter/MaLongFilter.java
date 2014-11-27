package com.github.tx.stock.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.util.TechnicalIndicators;

/** 
 * ma多头排列
 * @author tangx
 * @since 2014年11月27日
 */

@Component
public class MaLongFilter implements Filter {
	
	private static final int SHORT_PERIOD = 25;
	
	private static final int LONG_PERIOD = 350;
	
	@Autowired
	TechnicalIndicators indicators;

	@Override
	public boolean doFilter(String symbol, int date) {
		double short_ma = indicators.sma(symbol, date, SHORT_PERIOD);
		double long_ma = indicators.sma(symbol, date, LONG_PERIOD);
		return short_ma>long_ma;
	}

}
