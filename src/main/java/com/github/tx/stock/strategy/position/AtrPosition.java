package com.github.tx.stock.strategy.position;

import org.springframework.stereotype.Component;

import com.github.tx.stock.util.NumberUtils;

/**
 * 
 * @author tangx
 * @since 2014年11月24日
 */

@Component
public class AtrPosition extends Position {

	private static final int N = 14;// atr周期

	private static final int PERCENT = 2;// 亏损百分比

	@Override
	public int position(String symbol, int date, double capital, double price) {
		double r = indicators.atr(symbol, date, N);// 每股风险
		double c = NumberUtils.multiply(capital,
				NumberUtils.divide(PERCENT, 100, 2));// 总风险
		double p = NumberUtils.divide(NumberUtils.divide(c, r, 2), 100, 0);
		double pAmount = NumberUtils.multiply(price, (int) p * 100);// 买入金额（price x 总数目）
		if(capital < pAmount){//防止四舍五入再减掉1
			p = NumberUtils.subtract(NumberUtils.divide(NumberUtils.divide(capital, price, 2), 100, 0), 1);
		}
		logger.debug("总风险:{},每单位风险:{},头寸:{}手", c, r, p);
		return (int) p * 100;
	}

}
