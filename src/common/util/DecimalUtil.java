package common.util;

import java.math.BigDecimal;

public class DecimalUtil
{
	/**
	 * 获取整数
	 * @param data
	 * @return
	 */
	public static int getInteger(double data)
	{
		BigDecimal decimal = new BigDecimal(data);
		return decimal.intValue();
	}
	
	/**
	 * 获取一位小数
	 * @param data
	 * @return
	 */
	public static int getOneDecimal(double data)
	{
		return getInteger(data * 10) / 10;
	}
}
