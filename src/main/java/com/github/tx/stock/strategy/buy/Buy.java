package com.github.tx.stock.strategy.buy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.entity.Summary;
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
	
	public abstract void buy(String symbol);
	
}
