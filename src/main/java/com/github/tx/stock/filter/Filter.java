package com.github.tx.stock.filter;

/**
 * 过滤器
 * @author tangx
 * @since 2014年11月27日
 */

public interface Filter {

	public boolean doFilter(String symbol, int date);

}
