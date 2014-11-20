package com.github.tx.stock.util;

import java.math.BigDecimal;

public class NumberUtils {

	/**
	 * 精确的加法运算
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 差
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 积
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.multiply(b2).doubleValue();
	}
	
	/**
	 * 精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @param scale 小数点后保留几位
	 * @return 积
	 */
	public static double multiply(double v1, double v2, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return round(b1.multiply(b2).doubleValue(), scale);
	}
	
	/**
	 * 相对精确的除法运算
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 小数点后保留几位
	 * @return
	 */
	public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

	/**
	 * 四舍五入
	 * @param v 要四舍五入的实数
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的数字
	 */
	public static double round(double v, int scale){
		if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
		 
}
