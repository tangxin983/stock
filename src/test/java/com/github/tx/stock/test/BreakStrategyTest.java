package com.github.tx.stock.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tx.stock.BreakStrategy;
import com.github.tx.stock.util.SysUtil;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BreakStrategyTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	BreakStrategy strategy;
	
//	@Test
	public void test() {
		List<String> stockList = SysUtil.getSymbolList();
		if (stockList != null && stockList.size() > 0) {
			for (String stock : stockList) {
				strategy.buy(stock);
			}
		}
	}
	
	@Test
	public void testSingle() {
		strategy.buy("sh600052");
	}


}
