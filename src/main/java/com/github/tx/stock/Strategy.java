package com.github.tx.stock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

public abstract class Strategy {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StockMapper mapper;
	
	/**
	 * 
	 * @param symbol 代码（表名）
	 * @param date 基准日期（yyyyMMdd格式）
	 * @param n 回溯天数
	 * @return 从date-n到date的最高close
	 */
	protected Double getNdayHighestClose(String symbol, String date, int n){
		List<Stock> entitys = mapper.select("sh600329");
		logger.info(entitys.size() + "");
		return 0d;
	}
	
	public abstract void buy();

	public abstract void sell();
}
