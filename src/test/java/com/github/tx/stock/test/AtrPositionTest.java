package com.github.tx.stock.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.tx.stock.strategy.position.AtrPosition;

/** 
 * 
 * @author tangx
 * @since 2014年11月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AtrPositionTest {
	
	@Autowired
	AtrPosition position;

	@Test
	public void test() {
		position.position("sz000598", 20141124, 26000, 6.10);
	}

}
