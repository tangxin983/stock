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

import com.github.tx.stock.init.SysInit;
import com.github.tx.stock.util.SysUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExecuteInit extends AbstractJUnit4SpringContextTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SysInit init;

	// @Test
	public void createTable() {
		logger.info("begin init table");
		init.createTable();
		logger.info("end init table");
	}

	// @Test
	public void importAllData() {
		List<String> symbols = SysUtil.getSymbolList();
		int i = 1;
		for (String symbol : symbols) {
			init.importData(symbol);
			logger.info("imported data:{},index:{}", symbol, i);
			i++;
		}
	}

	@Test
	public void importData() {
		init.importData("sz000001");
	}

}
