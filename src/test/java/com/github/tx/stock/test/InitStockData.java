package com.github.tx.stock.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;

public class InitStockData {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws Exception {
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		// SqlSession session = null;
		// try {
		// session = sqlSessionFactory.openSession();
		// Connection conn = session.getConnection();
		// reader = Resources.getResourceAsReader("mysql.sql");
		// ScriptRunner runner = new ScriptRunner(conn);
		// runner.setLogWriter(null);
		// runner.runScript(reader);
		// reader.close();
		// } finally {
		// if (session != null) {
		// session.close();
		// }
		// }
	}

	@Test
	public void test() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StockMapper mapper = session.getMapper(StockMapper.class);
//			URL stockURL = new URL(
//					"http://table.finance.yahoo.com/table.csv?s=000001.sz");
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					stockURL.openStream()));
			 Reader in = new FileReader("g:/table.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			int i = 0;
			for (CSVRecord record : records) {
				if (i != 0) {
					Stock stock = new Stock();
					stock.setDate(Integer.valueOf(record.get(0)
							.replace("-", "")));
					stock.setOpen(Double.valueOf(record.get(1)));
					stock.setHigh(Double.valueOf(record.get(2)));
					stock.setLow(Double.valueOf(record.get(3)));
					stock.setClose(Double.valueOf(record.get(4)));
					stock.setVolume(Long.valueOf(record.get(5)));
					mapper.insert(stock);
				}
				i++;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			session.commit();
			session.close();
		}
	}

}
