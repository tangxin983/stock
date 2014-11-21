package com.github.tx.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tx.mybatis.criteria.Criteria;
import com.github.tx.mybatis.criteria.QueryCondition;
import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.dao.SummaryMapper;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.entity.Summary;

/**
 * 
 * @author tangx
 * @since 2014年11月20日
 */

@Service
@Transactional
public class DbService {

	@Autowired
	private StockMapper stock;

	@Autowired
	private SummaryMapper summary;

	@Cacheable(value = "symbolCache")
	public List<Stock> getSymbolData(String symbol) {
		QueryCondition condition = new QueryCondition();
		condition.or(Criteria.newCriteria().gt("volume", 0));
		return stock.selectByCondition(symbol, condition);
	}

	public void insertSummary(List<Summary> entitys) {
		for (Summary entity : entitys) {
			summary.insert(entity);
		}
	}
	
	public int insertSummary(Summary entity) {
		return summary.insert(entity);
	}
}
