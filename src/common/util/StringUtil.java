package common.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * @ClassName: StringUtil
 * @Description: 字符串工具类
 * @author scofieldandroid@gmail.com
 * @date 2012-12-24 下午7:54:17
 * 
 */
public class StringUtil {

	/**
	 * @Title: mergeMutableArgs
	 * @Description: 拼接字符串 可以提供指定的分割线
	 * @param splitLine
	 *            分割线
	 * @param args
	 *            可变参数
	 * @return
	 * @return String
	 */
	public static String mergeMutableArgs(String splitLine, Object... args) {
		if (splitLine == null) {
			splitLine = "";
		}

		StringBuffer sb = new StringBuffer();
		if (args != null && args.length > 0) {
			Object item;
			for (int i = 0; i < args.length; i++) {
				item = args[i] == null ? "" : args[i];
				if (i == 0) {
					sb.append("" + item);
				} else {
					sb.append(splitLine + item);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * @Title: mergeMutableArgs
	 * @Description: 拼接字符串
	 * @param array
	 *            数组
	 * @return
	 * @return String
	 */
	public static String mergeMutableArgs(Object[] array) {
		return StringUtil.mergeMutableArgs(null, array);
	}

	/**
	 * @Title: formatStringTo8Times
	 * @Description: 格式化字符串为8的倍数，低位补0
	 * @param content
	 * @return
	 * @returnType String
	 */
	public static String formatStringTo8Times(String content) {
		return formatStringTo_N_Times(8, content, true);
	}

	/**
	 * @Title: formatStringTo2TimesInFront
	 * @Description: 格式化为偶数位，在前面补0
	 * @param content
	 * @return
	 * @returnType String
	 */
	public static String formatStringTo2TimesInFront(String content) {
		return formatStringTo_N_Times(2, content, false);
	}

	/**
	 * @Title: formatStringTo_N_Times
	 * @Description: 格式化为N的倍数，不足部分低位补0
	 * @param n
	 *            倍数
	 * @param content
	 *            内容
	 * @param add0PositionIsEnd
	 *            true在最后补0， false在最前面补0
	 * @return
	 * @returnType String
	 */
	public static String formatStringTo_N_Times(int n, String content,
			boolean add0PositionIsEnd) {
		StringBuffer sb = new StringBuffer();
		if (content != null) {

			if (add0PositionIsEnd == true) {
				// /最后补0
				sb.append(content);
			}

			int length = content.length();
			if ((length % n) != 0) {
				int addCount = n - (length % n);
				for (int i = 0; i < addCount; i++) {
					sb.append("0");
				}
			}

			if (add0PositionIsEnd == false) {
				// 最前面补0
				sb.append(content);
			}
		}
		return sb.toString();
	}

	// =========================================

	/**
	 * @Title: stringToHexByte
	 * @Description: 普通字符串转化为16进制的字符串
	 * @param str
	 * @return
	 * @returnType byte[]
	 */
	public static byte[] stringToHexByte(String str) {
		String hexString = bytesToHexString(str.getBytes());
		return hexStringToBytes(hexString);
	}

	/**
	 * @Title: hexStringToCommonString
	 * @Description: 16进制的字符串转化为普通的字符串
	 * @param hexString
	 * @return
	 * @returnType String
	 */
	public static String hexStringToCommonString(String hexString) {
		byte[] bytes = hexStringToBytes(hexString);
		try {
			return new String(bytes, "gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(bytes);
		}
	}

	/**
	 * @Title: bytesToHexString
	 * @Description: 字节数组转化为16进制的字符串
	 * @param bytes
	 * @return
	 * @returnType String
	 */
	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder("");

		if (bytes == null || bytes.length <= 0) {
			return null;
		}

		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				sb.append(0);
			}
			sb.append(hv);
		}

		return sb.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	@SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int byteArrayLength = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();

		byte[] d = new byte[byteArrayLength];
		for (int i = 0; i < byteArrayLength; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * @param str
	 *            存： 普通字符串——>16进制字符串
	 * @return
	 * @returnType String
	 */
	public static String toHexString(String str) {
		byte[] byteArray;
		try {
			byteArray = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			byteArray = str.getBytes();
		}
		return bytesToHexString(byteArray);
	}

	// ======================================================
	/**
	 * @Title: isEmpty
	 * @Description: 判断是否为空，仅仅去掉首尾的空格,不去除中间的空格
	 * @param str
	 * @return
	 * @returnType boolean
	 */
	public static boolean isEmpty(String str) {
		boolean ret = false;
		if (str == null || str.trim().length() == 0) {
			ret = true;
		}
		return ret;
	}

	/**
	 * @Title: isNotEmpty
	 * @Description: 判断是否不为空，仅仅去掉首尾的空格,不去除中间的空格
	 * @param str
	 * @return
	 * @returnType boolean
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 移除最后指定字符
	 * 
	 * @version Jan 21, 2013
	 * @param src
	 *            源字符串，如："abcdefp:"
	 * @param cs
	 *            指定要移除字符，如：':','p'
	 * @return 如："abcdef"
	 */
	public static String removeLastChar(String src, char... cs) {
		if (src == null || cs == null || cs.length < 1) {
			return src;
		}
		String regex = getMatchLastCharRegex(false, cs);
		return src.replaceAll(regex, "");
	}

	/**
	 * 移除冒号
	 * 
	 * @param src
	 * @return
	 */
	public static String removeLastColon(String src) {
		return removeLastChar(src, ':', '：');
	}

	/**
	 * 获取匹配最后字符的正则表达式
	 * 
	 * @author fred.fu@magic-point.com
	 * @version Jan 21, 2013
	 * @param isOne
	 *            控制是否只匹配最后一个（true），还是匹配最后所有（false）
	 * @param cs
	 *            匹配字符
	 * @return 正则表达式
	 */
	private static String getMatchLastCharRegex(boolean isOne, char... cs) {
		return "[" + String.valueOf(cs) + "]" + (isOne == true ? "" : "+")
				+ "$";
	}

	/**
	 * 添加后缀
	 * 
	 * @version Jan 21, 2013
	 * @param src
	 *            如：abc
	 * @param suffix
	 *            如："："
	 * @return 如：abc：
	 */
	public static String addSuffix(String src, String suffix) {
		if (src == null || suffix == null) {
			return src;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(src);
		sb.append(suffix);
		return sb.toString();
	}

	/**
	 * 字符串末尾冒号转换（添加）：英文->中文冒号
	 * 
	 * @version Jan 21, 2013
	 * @param strColon
	 * @return
	 */
	public static String convertCnColon(CharSequence strColon) {
		return StringUtil.formatColon(strColon, true);
	}

	/**
	 * 格式化冒号
	 * 
	 * @author fred.fu@magic-point.com
	 * @version Jan 21, 2013
	 * @param strColon
	 * @param isCn
	 *            中文冒号（true），英文冒号（false）
	 * @return
	 */
	public static String formatColon(CharSequence strColon, boolean isCn) {

		if (strColon == null || strColon.toString().trim().length() < 1) {
			return "";
		}

		return StringUtil.addSuffix(
				StringUtil.removeLastChar(strColon.toString(),
						StringUtil.getColon(true), StringUtil.getColon(false)),
				String.valueOf(StringUtil.getColon(isCn)));
	}

	/**
	 * 
	 * @author fred.fu@magic-point.com
	 * @version Jan 21, 2013
	 * @param isCn
	 *            中文冒号（true），英文冒号（false）
	 * @return
	 */
	public static char getColon(boolean isCn) {
		return isCn == true ? '：' : ':';
	}

	/**
	 * 将手机号4-7位隐藏
	 */
	public static String hiddenPhoneNum(String num) {
		return hideSpecifiedPositionString(num, 3, 7);
	}

	public static boolean isDecimal(String str) {
		return Pattern.compile("[\\d]").matcher(str).matches();
	}


//	/**
//	 * 信用卡账单查询中隐藏账号
//	 * 
//	 * @author fred.fu@magic-point.com
//	 * @version Feb 4, 2013
//	 * @param num
//	 * @return
//	 */
//	public static String concealCreditAccount(String account) {
//		if (ValueUtil.isStrEmpty(account)) {
//			return "";
//		}
//		// 如果账号状态是打开，则直接返回
//		if (null != BaseApp.getApp().mLoginUser
//				&& "0".equals(BankApp.getApp().mLoginUser.getSAFE_OPEN_CLOSE())) {
//			int index = -1;
//			for (int i = 0; i < account.length(); i++) {
//				String s = account.substring(i, i + 1);
//				if (isDecimal(s)) {
//					index = i;
//					break;
//				}
//			}
//			if (index == -1) {
//				return account;
//			}
//
//			return hideSpecifiedPositionString(account, index + 7, index + 12);
//
//		} else {
//			return account;
//		}
//	}

//	/**
//	 * 账号是 8-12位隐藏
//	 * 
//	 * @author fred.fu@magic-point.com
//	 * @version Feb 4, 2013
//	 * @param num
//	 * @return
//	 */
//	public static String concealAccount(String account) {
//		if (ValueUtil.isStrEmpty(account)) {
//			return "";
//		}
//		// 如果账号状态是打开，则直接返回
//		try {
//			if (null != BankApp.getApp().mLoginUser
//					&& "0".equals(BankApp.getApp().mLoginUser
//							.getSAFE_OPEN_CLOSE())) {
//				return hideSpecifiedPositionString(account, 7, 12);
//			} else {
//				return account;
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return account;
//		}
//	}

	/**
	 * 隐藏指定位置的字符串
	 * 
	 * @author fred.fu@magic-point.com
	 * @version Feb 4, 2013
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String hideSpecifiedPositionString(String str, int start,
			int end) {

		if (str == null || str.length() < 1) {
			return str;
		}

		StringBuilder sb = new StringBuilder();

		if (start > end || end > str.length()) {

			throw new IllegalStateException(
					"Start 不能大于 end, 或 end 不能大于 str.length() 字符串的长度");

		} else {

			StringBuilder starSB = new StringBuilder();

			for (int i = 0; i < end - start; i++) {
				starSB.append("*");
			}

			String star = starSB.toString();

			sb.append(str);

			sb.replace(start, end, star);
		}

		return sb.toString();
	}

	/**
	 * 截取字符串
	 * 
	 * @param src
	 *            源字符串
	 * @param str1
	 *            起始字符
	 * @param str2
	 *            结束字符
	 * @return 如："abcdef"
	 */
	public static String InterceptionStr(String src, String str1, String str2) {
		if (src == null || src.length() < 1) {
			return src;
		}
		int beginIdx = src.indexOf(str1) + 1;
		int endIdx = src.indexOf(str2);
		return src.substring(beginIdx, endIdx);
	}

	
	
	
//	/**
//	 * @description:二维码，检查输入的网址，不全输入的网址
//	 * @author shiyou.xin@magic-point.com
//	 * @date: 2013-4-17-下午5:08:44
//	 */
//	public static String getFormatURLAddress(String url) {
//
//		if (ValueUtil.isStrEmpty(url)) {
//			// input String is empty, so return
//			// "The input value is Empty! Please check your input"
//			return null;
//		}
//
//		if (!RegexUtil.validateTwoDimensionMsg(url)) {
//			// input String is not a validate Web site address, so return
//			// "The input value is not a validate Web site! Please check your input"
//			return null;
//		}
//
//		if (!url.startsWith("http")) {
//			url = "http://" + url;
//		}
//		
//		return url;
//	}

}
