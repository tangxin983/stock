package com.github.tx.stock.backtest.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.strategy.buy.BreakCloseBuy;

@Component
public class BreakCloseBuyBackTest extends BuyBackTest {
	
	@Autowired
	BreakCloseBuy breakCloseBuy;

	@Override
	public boolean hit(String symbol, int date) {
		return breakCloseBuy.buy(symbol, date);
	}

}
