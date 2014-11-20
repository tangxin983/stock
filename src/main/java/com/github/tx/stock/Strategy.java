package com.github.tx.stock;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.service.DbService;
import com.github.tx.stock.util.CollectionUtils;
import com.github.tx.stock.util.NumberUtils;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

public abstract class Strategy {

	protected static final String SYMBOL = "sh600330";// 回测标的

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected DbService service;

	public abstract void buy(String symbol);

	public abstract void sell(String symbol);

	/**
	 * 获取基准日前n天的最高close
	 * @param entitys 实体列表
	 * @param index 基准日期序号
	 * @param n N天
	 * @return
	 */
	public Double getHighestClose(List<Stock> entitys, int index, int n) {
		if (index < n) {
			throw new IllegalArgumentException("index必须大等于n");
		}
		List<Stock> sub = entitys.subList(index - n, index);
		List<Double> closes = CollectionUtils.extractToList(sub, "close", true);
		return Collections.max(closes);
	}

	/**
	 * 获取VolumeRatio(基准日过去5天内)
	 * @param entitys 实体列表
	 * @param index 基准日期序号
	 * @return
	 */
	public Double getVolumeRatio(List<Stock> entitys, int index) {
		if (index < 5) {
			throw new IllegalArgumentException("index必须大等于5");
		}
		Stock today = entitys.get(index);
		Long sumVolume = 0L;
		for (int i = 1; i <= 5; i++) {
			Stock stock = entitys.get(index - i);
			sumVolume += stock.getVolume();
		}
		Double averageVolume = NumberUtils.divide(sumVolume, 5, 2);
		return NumberUtils.divide(today.getVolume(), averageVolume, 2);
	}

	/**
	 * 获取某具体日期的changeRatio
	 * <p>
	 * 公式:(todayClose-yesterdayClose/yesterdayClose)X100
	 * @param entitys 实体列表
	 * @param index 具体日期序号
	 * @return
	 */
	public Double getChangeRatio(List<Stock> entitys, int index) {
		if (index < 1) {
			throw new IllegalArgumentException("index必须大等于1");
		}
		Stock today = entitys.get(index);
		Stock yesterday = entitys.get(index - 1);
		Double minus = NumberUtils.subtract(today.getClose(),
				yesterday.getClose());
		Double ratio = NumberUtils.divide(minus, yesterday.getClose(), 4);
		return NumberUtils.multiply(ratio, 100);
	}

	/**
	 * 获取某具体日期n天后的changeRatio
	 * <p>
	 * 公式:(nDayClose-todayClose/todayClose)X100
	 * @param entitys 实体列表
	 * @param index 具体日期序号
	 * @param n N天
	 * @return
	 */
	public Double getNdayChangeRatio(List<Stock> entitys, int index, int n) {
		if (index < 1) {
			throw new IllegalArgumentException("index必须大等于1");
		}
		Stock today = entitys.get(index);
		Stock afterNday = entitys.get(index + n);
		Double minus = NumberUtils.subtract(afterNday.getClose(),
				today.getClose());
		Double ratio = NumberUtils.divide(minus, today.getClose(), 4);
		return NumberUtils.multiply(ratio, 100);
	}
}
