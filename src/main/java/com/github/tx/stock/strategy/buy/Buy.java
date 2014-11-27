package com.github.tx.stock.strategy.buy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.service.DbService;
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
	
	@Autowired
	DbService service;
	
	/**
	 * 判断此日期是否可入市
	 * @param symbol 代码
	 * @param date 测试日期
	 * @param isFilterEnable 是否开启过滤器
	 * @return
	 */
	public abstract boolean buy(String symbol, int date, boolean isFilterEnable);
	
	public abstract double getBuyPrice();
	
}
