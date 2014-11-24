package com.github.tx.stock.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tx.stock.backtest.trade.TradeSystemBackTest;
import com.github.tx.stock.util.SysUtil;

/**
 * 执行回测
 * 
 * @author tangx
 * @since 2014年11月19日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExecuteTradeSystemTest extends AbstractJUnit4SpringContextTests {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TradeSystemBackTest backTest;

	private static final int BEGIN_DATE = 20140610;// 起始回测日

	private static final int END_DATE = 20141111;// 结束回测日

	// @Test
	public void test() {
		List<String> stockList = SysUtil.getSymbolList();
		if (stockList != null && stockList.size() > 0) {
			for (String stock : stockList) {
			}
		}
	}

	@Test
	public void testSingle() {
		String symbol = "sz000001";
		backTest.trade(symbol, BEGIN_DATE, END_DATE, 100000);
	}

}
