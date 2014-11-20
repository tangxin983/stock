package com.github.tx.stock.test;

import org.junit.Test;

import com.github.tx.stock.util.NumberUtils;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

public class TaLibTest {

	/**
	 * The total number of periods to generate data for.
	 */
	public static final int TOTAL_PERIODS = 100;

	/**
	 * The number of periods to average together.
	 */
	public static final int PERIODS_AVERAGE = 30;

	@Test
	public void test() {
		double[] closePrice = new double[TOTAL_PERIODS];
		double[] out = new double[TOTAL_PERIODS];
		MInteger begin = new MInteger();
		MInteger length = new MInteger();

		for (int i = 0; i < closePrice.length; i++) {
			closePrice[i] = (double) i;
		}

		Core c = new Core();
		RetCode retCode = c.sma(0, closePrice.length - 1, closePrice,
				PERIODS_AVERAGE, begin, length, out);

		
		
		if (retCode == RetCode.Success) {
			// for (int i = begin.value; i < length.value; i++) {
			// StringBuilder line = new StringBuilder();
			// line.append("Period #");
			// line.append(i + 1);
			// line.append(" close= ");
			// line.append(closePrice[i]);
			// line.append(" mov avg=");
			// line.append(out[i]);
			// System.out.println(line.toString());
			// }
			for (int i = begin.value; i < closePrice.length; i++) {
				StringBuilder line = new StringBuilder();
				line.append("Period #");
				line.append(i + 1);
				line.append(" close= ");
				line.append(closePrice[i]);
				line.append(" mov avg=");
				line.append(out[i - begin.value]);
				System.out.println(line.toString());
			}
		} else {
			System.out.println("Error");
		}
	}
	
	@Test
	public void test1(){
		int sum = 0;
		for(int i=1; i<31;i++){
			sum += i;
		}
		System.out.println(sum);
		System.out.println(NumberUtils.divide(sum, 30, 2));
	}

}
