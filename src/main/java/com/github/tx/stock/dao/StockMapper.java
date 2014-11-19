package com.github.tx.stock.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.tx.stock.entity.Stock;

/**
 * 
 * @author tangx
 * @since 2014年10月22日
 */

public interface StockMapper {

	public void createTable(@Param("tableName")String tableName);
	
	public void insertData(Stock stock);
	
	public List<Stock> select(@Param("tableName")String tableName);
}
