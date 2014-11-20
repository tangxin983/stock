package com.github.tx.stock.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tx.stock.BreakStrategy;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.service.DbService;
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
	
	@Autowired
	protected DbService service;
	
//	@Test
	public void test() {
		List<String> stockList = SysUtil.getSymbolList();
		if (stockList != null && stockList.size() > 0) {
			for (String stock : stockList) {
				strategy.buy(stock);
			}
		}
	}
	
//	@Test
	public void testSingle() {
		strategy.buy("sh600052");
	}

//	@Test
	public void testSma() {
		List<Stock> entitys = service.select("sh600052");
		if(entitys.size() >0){
			System.out.println(strategy.smaByDate(entitys, 20141117, 60));
		}
	}
	
//	@Test
	public void testMtr() {
		List<Stock> entitys = service.select("sh600052");
		if(entitys.size() >0){
			System.out.println(strategy.mtrByDate(entitys, 20141028));
		}
	}
	
	@Test
	public void testAtr() {
		List<Stock> entitys = service.select("sh600052");
		if(entitys.size() >0){
			System.out.println(strategy.atrByDate(entitys, 20140918, 14));
		}
	}
}
