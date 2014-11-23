package com.github.tx.stock.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.SysUtil;

@Component
@Transactional 
public class SysInit {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StockMapper mapper;

	public void createTable() {
		List<String> stockList = SysUtil.getSymbolList();
		if (stockList != null && stockList.size() > 0) {
			for (String stock : stockList) {
				mapper.createTable(stock);
			}
		}
	}

	public void importData(String symbol) {
		List<Stock> entitys = getStockEntitys(symbol);
		if (entitys.size() > 0) {
			for (Stock stock : entitys) {
				mapper.insertData(stock);
			}
		}
	}

	private List<Stock> getStockEntitys(String symbol) {
		List<Stock> entitys = new ArrayList<Stock>();
		Iterable<CSVRecord> records = SysUtil.getCsvRecord(symbol, 15000);
		int i = 0;
		if (records != null) {
			for (CSVRecord record : records) {
				if (i != 0) {
					Stock stock = new Stock();
					stock.setTableName(symbol);
					stock.setDate(Integer.valueOf(record.get(0)
							.replace("-", "")));
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
			Collections.reverse(entitys);
		}
		return entitys;
	}
}
