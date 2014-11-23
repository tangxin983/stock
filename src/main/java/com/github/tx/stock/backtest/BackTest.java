package com.github.tx.stock.backtest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.service.DbService;
import com.github.tx.stock.util.TechnicalIndicators;
import com.github.tx.stock.util.TimeUtil;

public abstract class BackTest {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected TechnicalIndicators indicators;
	
	@Autowired
	protected DbService service;
	
	/**
	 * 校正开始日期
	 * 
	 * @param symbol
	 * @param beginDate
	 * @return
	 */
	protected int adjustBeginDate(String symbol, int beginDate) {
		List<Integer> dates = indicators.getDates(symbol);
		while (dates.indexOf(beginDate) == -1) {
			beginDate = Integer.valueOf(TimeUtil.getAfterDateStr(
					String.valueOf(beginDate), 1));
		}
		return beginDate;
	}

}
