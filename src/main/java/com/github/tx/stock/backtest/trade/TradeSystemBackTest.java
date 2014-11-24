package com.github.tx.stock.backtest.trade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.github.tx.stock.backtest.BackTest;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.strategy.buy.Buy;
import com.github.tx.stock.strategy.position.Position;
import com.github.tx.stock.strategy.sell.Sell;
import com.github.tx.stock.util.NumberUtils;

@Component
public class TradeSystemBackTest extends BackTest {

	@Autowired
	@Qualifier("breakCloseBuy")
	Buy buy;

	@Autowired
	Sell sell;

	@Autowired
	Position p;

	public void trade(String symbol, int beginDate, int endDate, double capital) {
		List<Stock> entitys = indicators.getSymbolData(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = indicators.getDates(symbol);
		if (dates.get(dates.size() - 1) <= beginDate) {
			return;
		}
		int date = adjustBeginDate(symbol, beginDate);
		int index = dates.indexOf(date);
		double leftAmount = capital;// 剩余金额
		while (date <= endDate) {
			int sellDate = 0;
			if (buy.buy(symbol, date)) {// 入市
				Stock stock = entitys.get(index);
				int units = p.position(symbol, date, leftAmount, stock.getClose());// 头寸单位数
				double pAmount = NumberUtils.multiply(stock.getClose(), units);// 买入金额（close x units）
				leftAmount = NumberUtils.subtract(leftAmount, pAmount);
				logger.debug("symbol:{},入市日期:{},单位:{},买入:{},买入金额:{},余额:{}", symbol,
						date, units, stock.getClose(),pAmount, leftAmount);
				double sellAmount = sell.sell(symbol, date, units);// 卖出金额
				sellDate = sell.getSellDate();// 离市日期
				leftAmount = NumberUtils.add(leftAmount, sellAmount);
				logger.debug("symbol:{},离市日期:{},单位:{},卖出:{},卖出金额:{},余额:{}", symbol,
						sellDate, units, sell.getSellPrice(), sellAmount, leftAmount);
				logger.debug("本次差额:{}", sellAmount - pAmount);
			}
			if(sellDate != 0){
				index = dates.indexOf(sellDate);//卖完才能买
			}else{
				index++;
			}
			if (index >= dates.size()) {
				break;
			}
			date = dates.get(index);
		}

	}
}
