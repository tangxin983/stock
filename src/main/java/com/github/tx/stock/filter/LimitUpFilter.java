package com.github.tx.stock.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.NumberUtils;
import com.github.tx.stock.util.TechnicalIndicators;

/**
 * limitup过滤器
 * 
 * @author tangx
 * @since 2014年11月27日
 */

@Component
public class LimitUpFilter implements Filter {

	public static final int n = 4;

	private static final double limit = 9.5;

	@Autowired
	TechnicalIndicators indicators;

	@Override
	public boolean doFilter(String symbol, int date) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		List<Integer> dates = indicators.getDates(symbol);
		int index = dates.indexOf(date);
		int date1 = dates.get(index - 1 - n);// 昨天的N天前
		int date2 = dates.get(index - 1);// 昨天
		double ratio = indicators.changeRatioBetweenTwoday(symbol, date1, date2);
		double limitRatio = NumberUtils.multiply(n, limit);
		Stock stock = entitys.get(index);
		return stock.getHigh() != stock.getLow() && ratio >= limitRatio;
	}

}
