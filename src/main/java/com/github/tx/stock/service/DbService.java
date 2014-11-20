package com.github.tx.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.tx.mybatis.criteria.Criteria;
import com.github.tx.mybatis.criteria.QueryCondition;
import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.TimeUtil;

/**
 * 
 * @author tangx
 * @since 2014年11月20日
 */

@Service
public class DbService {

	@Autowired
	private StockMapper mapper;

	/**
	 * 获取某天数据
	 * 
	 * @param symbol
	 * @param date
	 * @return
	 */
	public Stock selectByDate(String symbol, int date) {
		QueryCondition condition = new QueryCondition();
		condition.or(Criteria.newCriteria().eq("date", date));
		List<Stock> entitys = mapper.selectByCondition(symbol, condition);
		if (entitys.size() > 0) {
			return entitys.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取所有volume大于0的数据
	 * 
	 * @param symbol
	 * @return
	 */
	public List<Stock> select(String symbol) {
		QueryCondition condition = new QueryCondition();
		condition.or(Criteria.newCriteria().gt("volume", 0));
		return mapper.selectByCondition(symbol, condition);
	}

	/**
	 * 获取某个id段的最高close(只统计volume>0的数据)
	 * 
	 * @param symbol
	 * @param begin
	 * @param end
	 * @return
	 */
	public Double getHighestCloseByIdRange(String symbol, int begin, int end) {
		QueryCondition condition = new QueryCondition();
		condition.or(Criteria.newCriteria().between("id", begin, end).gt("volume", 0));
		return mapper.getHighestClose(symbol, condition);
	}
}
