package com.naicha.util;

import java.util.List;
import java.util.UUID;

/**
 * @Description: 值工具类
 * @author: ethanchiu@126.com
 * @date: 2013-7-26
 */
public class ValueUtil {

	/**
	 * @Description: 判断List不为为空
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param noteList
	 * @return
	 */
	public static boolean isListNotEmpty(List<?> noteList) {
		return null != noteList && noteList.size() > 0;
	}
	
	/**
	 * @Description: 判断List为空
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param noteList
	 * @return
	 */
	public static boolean isListEmpty(List<?> noteList) {
		return null == noteList || noteList.size() == 0;
	}
	
	/**
	 * @Description: 判断字符串不为空
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param str
	 * @return
	 */
	public static boolean isStrNotEmpty(String str) {
        if (str == null || str.length() == 0)
            return false;
        else
            return true;
	}
	
	public static boolean isStrEmpty(String value) {
		return !isStrNotEmpty(value);
	}
	
	/**
	 * 创建一个uuid
	 */
	public static String newUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	

	/**
	 * 从apk开始截断文件名
	 * @param fileName
	 * @return
	 */
	public static CharSequence cutFileNameWithOutAPK(String fileName) {
		if (null == fileName || !fileName.contains(".apk")) {
			return fileName;
		}
		return fileName.subSequence(0, fileName.lastIndexOf(".apk"));
	}
	
	/**
	 * 去除空格
	 * 
	 * @param value
	 * @return
	 */
	public static String trim(String value) {
		if (null != value) {
			return value.trim();
		} else {
			return "";
		}
	}
	
	private static int ONLY_NUM = 800000;
	/**
	 * 获取一个唯一数
	 * @return
	 */
	public static int getOnlyNum() {
		final int num = ONLY_NUM++;
		return num;
	}
	
	
}