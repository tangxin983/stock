package com.github.tx.stock.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;

public class InitStockData extends MybatisTestUnit {

	@Test
	public void test() {
		List<String> symbols = SysUtil.getSymbolList();
		int i = 1;
		for (String symbol : symbols) {
			SqlSession session = sqlSessionFactory.openSession();
			try {
				StockMapper mapper = session.getMapper(StockMapper.class);
				List<Stock> entitys = getStockEntitys(symbol);
				for (Stock stock : entitys) {
					mapper.insertData(stock);
				}
			} finally {
				session.commit();
				session.close();
			}
			logger.info("finish init table:{},index:{}", symbol, i);
			i++;
		}
	}

	private List<Stock> getStockEntitys(String symbol) {
		List<Stock> entitys = new ArrayList<Stock>();
		Iterable<CSVRecord> records = SysUtil.getCsvRecord(symbol, 30000);
		int i = 0;
		if(records != null){
			for (CSVRecord record : records) {
				if (i != 0) {
					Stock stock = new Stock();
					stock.setTableName(symbol);
					stock.setDate(Integer.valueOf(record.get(0).replace("-", "")));
					stock.setOpen(Double.valueOf(record.get(1)));
					stock.setHigh(Double.valueOf(record.get(2)));
					stock.setLow(Double.valueOf(record.get(3)));
					stock.setClose(Double.valueOf(record.get(4)));
					stock.setVolume(Long.valueOf(record.get(5)));
					stock.setAdjClose(Double.valueOf(record.get(6)));
					entitys.add(stock);
				}
				i++;
			}
		}
		return entitys;
	}

}
