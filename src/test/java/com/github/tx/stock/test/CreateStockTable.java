package com.github.tx.stock.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.github.tx.stock.dao.StockMapper;

public class CreateStockTable extends MybatisTestUnit {

	@Test
	public void test() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			StockMapper mapper = session.getMapper(StockMapper.class);
			List<String> stockList = SysUtil.getSymbolList();
			if(stockList != null && stockList.size() >0){
				for(String stock : stockList){
					mapper.createTable(stock);
				}
			}
		} finally {
			session.commit();
			session.close();
		}
	}

}
