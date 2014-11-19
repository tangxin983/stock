package com.github.tx.stock.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tx.stock.BreakStrategy;

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
	
	@Test
	public void test() {
		strategy.buy();
	}

}
