package com.github.tx.stock.strategy.position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.util.TechnicalIndicators;

/**
 * 
 * @author tangx
 * @since 2014年11月21日
 */

public abstract class Position {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	TechnicalIndicators indicators;

	/**
	 * 头寸单位数
	 * @param symbol 代码
	 * @param date 入市日期
	 * @param capital 资金
	 * @param price 价格
	 * @return
	 */
	public abstract int position(String symbol, int date, double capital, double price);
}
