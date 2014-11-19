package com.github.tx.stock;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.dao.StockMapper;
import com.github.tx.stock.entity.Stock;

/** 
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class BreakStrategy extends Strategy {
	
	@Override
	public void buy() {
		 getNdayHighestClose("", "", 0);
	}

	@Override
	public void sell() {

	}

}
