package com.github.tx.stock.strategy.sell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.util.TechnicalIndicators;

/**
 * 
 * @author tangx
 * @since 2014年11月21日
 */

public abstract class Sell {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TechnicalIndicators indicators;

	/**
	 * 离市
	 * 
	 * @param symbol
	 *            代码
	 * @param date
	 *            入市日期
	 * @param units
	 *            可卖单位数
	 * @return 卖出金额
	 */
	public abstract double sell(String symbol, int date, int units);

	/**
	 * 获取离市日期
	 * 
	 * @return
	 */
	public abstract int getSellDate();
}
