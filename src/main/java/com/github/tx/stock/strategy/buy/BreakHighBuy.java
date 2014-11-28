package com.github.tx.stock.strategy.buy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.filter.BreakoutHighFilter;
import com.github.tx.stock.filter.MaLongFilter;
import com.github.tx.stock.filter.VolumeFilter;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class BreakHighBuy extends Buy {

	private double price;

	@Autowired
	private MaLongFilter maLongFilter;
	
	@Autowired
	private BreakoutHighFilter breakoutHighFilter;
	
	@Autowired
	private VolumeFilter volumeFilter;
	
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
		if(!maLongFilter.doFilter(symbol, date)){
			return false;
		}
//		if(!volumeFilter.doFilter(symbol, date)){
//			return false;
//		}
		if(!breakoutHighFilter.doFilter(symbol, date)){
			return false;
		}
		price = indicators.highestHigh(symbol, date, BreakoutHighFilter.BREAK_DAYS);
		logger.debug("{}入市, 价格{}", date, price);
		return true;
	}

	@Override
	public double getPrice(String symbol, int date) {
		return price;
	}

}
