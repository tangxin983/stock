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
	 * 判断是否可入市
	 * @param symbol 代码
	 * @param date 日期
	 * @return
	 */
	public abstract boolean buy(String symbol, int date);
	
}
