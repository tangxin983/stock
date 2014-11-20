package com.github.tx.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.tx.stock.entity.Stock;
import com.github.tx.stock.util.CollectionUtils;
import com.github.tx.stock.util.TimeUtil;

/**
 * 
 * @author tangx
 * @since 2014年11月19日
 */

@Component
public class BreakStrategy extends Strategy {

	private static final int BREAK_DAYS = 20;

	private static final int RATIO = 2;

	private static final int BEGIN_DATE = 20110101;// 起始回测日

	private static final int END_DATE = 20111231;// 结束回测日

	@Override
	public void buy(String symbol) {
		List<Stock> entitys = service.select(symbol);
		if (entitys.size() == 0) {
			return;
		}
		List<Integer> dates = CollectionUtils.extractToList(entitys, "date",
				true);
		List<Integer> ztList = new ArrayList<Integer>();
		int i = 0, index = 0;
		int day1 = 0, day2 = 0, day3 = 0, day4 = 0, day5 = 0;// Nday后的获利次数
		int date = BEGIN_DATE;
		while (date != END_DATE) {
			if (dates.contains(date)) {
				index = dates.indexOf(date);
				if (index < BREAK_DAYS) {
					logger.info("代码{}的回测日太小，需推后", symbol);
					return;
				}
				Stock stock = entitys.get(index);
				Double highestClose = getHighestClose(entitys, index,
						BREAK_DAYS);
				Double volumeRatio = getVolumeRatio(entitys, index);
				Double changeRatio = getChangeRatio(entitys, index);
				if (stock.getClose() > highestClose && volumeRatio >= RATIO) {
					if (changeRatio >= 9.95) {
						ztList.add(date);
					}
					logger.info(
							"以{}为基准, 当天change为{}, 当前close为{}, vratio为{}, 近{}天的最高close为{}",
							stock.getDate(), changeRatio, stock.getClose(),
							volumeRatio, BREAK_DAYS, highestClose);
					if (entitys.size() - index > 5) {// 当前index后必须有够做测试的天数否则不计入
						if (getNdayChangeRatio(entitys, index, 1) > 0) {
							day1++;
						}
						if (getNdayChangeRatio(entitys, index, 2) > 0) {
							day2++;
						}
						if (getNdayChangeRatio(entitys, index, 3) > 0) {
							day3++;
						}
						if (getNdayChangeRatio(entitys, index, 4) > 0) {
							day4++;
						}
						if (getNdayChangeRatio(entitys, index, 5) > 0) {
							day5++;
						}
						i++;
					}
					// 计算1天，2天，3天，4天，5天后的ratio
					logger.info("1天:{},2天:{},3天:{},4天:{},5天:{}",
							getNdayChangeRatio(entitys, index, 1),
							getNdayChangeRatio(entitys, index, 2),
							getNdayChangeRatio(entitys, index, 3),
							getNdayChangeRatio(entitys, index, 4),
							getNdayChangeRatio(entitys, index, 5));
				}
			}
			date = Integer.valueOf(TimeUtil.getAfterDateStr(
					String.valueOf(date), 1));
		}
		logger.info("============{}summary===============", symbol);
		logger.info("从{}到{}共确认{}次", BEGIN_DATE, END_DATE, i);
		logger.info("其中zt{}次，日期为{}", ztList.size(), ztList.toString());
		logger.info("1day{}次,2day{}次,3day{}次,4day{}次,5day{}次,", day1, day2,
				day3, day4, day5);
	}

	@Override
	public void sell(String symbol) {

	}

}
