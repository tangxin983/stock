package com.github.tx.stock.backtest.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.strategy.buy.BreakHighBuy;

@Component
public class BreakHighBuyBackTest extends BuyBackTest {
	
	@Autowired
	BreakHighBuy breakHighBuy;

	@Override
	public boolean hit(String symbol, int date) {
		return breakHighBuy.buy(symbol, date);
	}

}
