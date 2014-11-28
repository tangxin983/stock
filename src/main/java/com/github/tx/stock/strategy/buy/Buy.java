package com.github.tx.stock.strategy.buy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.util.TechnicalIndicators;

/** 
 * 
 * @author tangx
 * @since 2014年11月21日
 */

public abstract class Buy {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TechnicalIndicators indicators;
	
	/**
	 * 获取入市价格
	 * @param symbol 代码
	 * @param date 日期
	 * @return
	 */
	public abstract double getPrice(String symbol, int date);
	
	/**
	 * 入市决策
	 * @param symbol 代码
	 * @param date 日期
	 * @return
	 */
	public abstract boolean makeDecision(String symbol, int date);
	
}
