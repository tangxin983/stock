package com.github.tx.stock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.tx.mybatis.criteria.QueryCondition;
import com.github.tx.stock.entity.Stock;

/**
 * 
 * @author tangx
 * @since 2014年10月22日
 */

public interface StockMapper {

	void createTable(@Param("tableName") String tableName);

	void insertData(Stock stock);

	List<Stock> select(@Param("tableName") String tableName);

	List<Stock> selectByCondition(@Param("tableName") String tableName,
			@Param("condition") QueryCondition condition);
	
	Double getHighestClose(@Param("tableName") String tableName,
			@Param("condition") QueryCondition condition);
}
