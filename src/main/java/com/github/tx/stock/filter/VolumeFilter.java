package com.github.tx.stock.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.tx.stock.util.TechnicalIndicators;

/**
 * 量比过滤器
 * 
 * @author tangx
 * @since 2014年11月28日
 */

@Component
public class VolumeFilter implements Filter {

	private static final int RATIO = 2;// 量比界限

	@Autowired
	TechnicalIndicators indicators;

	@Override
	public boolean doFilter(String symbol, int date) {
		Double volumeRatio = indicators.volumeRatio(symbol, date);
		return volumeRatio >= RATIO;
	}

}
