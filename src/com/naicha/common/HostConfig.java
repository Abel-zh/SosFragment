package com.naicha.common;

import com.naicha.util.ValueUtil;

/**
 * @ClassName: HostConfig
 * @Description: HostConfig
 * @author Ethan
 * @date 2013-3-9 下午1:19:44
 * 
 */
public class HostConfig {

	private final static String WHEATHER_HOST = "http://www.weather.com.cn/data/cityinfo/";// 天气地址
	private final static String WHEATHER_IMAGE_HOST = "http://m.weather.com.cn/img/";// 天气图片地址
	private final static String ADV_IMAGEURL_HOST = "http://180.169.94.194:8080/";// 广告地址,社区
	private final static String FILE_SERVER_HOST = "http://192.168.0.180:8080";// 文件服务器(注意：包括获得图片和各种文件)
	public final static String SOCKET_IP = "192.168.0.180"; // tcp 地址测试
	private final static String HOST_ADDRESS = "http://192.168.0.184:8080";// 公司服务器
	public static final int SOCKET_PROT = 4444;// tcp 端口 正式
	private final static String INTERFACE_PATH = "/service/service";
	public static String BAIDU_KEY = "8E61DE58a585c9ab4fa4c402f5310999";// 百度地图api

	public final static String CODE_MESSAGE_PATH = "sms";

	/***
	 * @title 从文件服务器获取图片url
	 * @param 在文件服务器所对应的id
	 * @return String
	 * @author michael.mao
	 * @date 2014年6月6日 下午4:23:04
	 * @version V1.0
	 */
	public static String getImage(String imageId) {
		if (!urlIsHasChar(FILE_SERVER_HOST, '/')) {
			return FILE_SERVER_HOST + "/" + imageId;
		} else {
			return FILE_SERVER_HOST + imageId;
		}

	}

	public static String getImagePath(String imageUrl) {
		if (imageUrl == null || "".equals(imageUrl)) {
			return ADV_IMAGEURL_HOST;
		}

		if (imageUrl.indexOf("||") > -1) {
			imageUrl = imageUrl.split("\\|\\|")[0];
		}

		if (imageUrl.indexOf("http://") > -1) {
			return imageUrl;
		}

		if ('/' != imageUrl.charAt(0)) {
			imageUrl = "/" + imageUrl;
		}

		return ADV_IMAGEURL_HOST + imageUrl;
	}

	public static String getAdvImagePath(String imageUrl) {
		if (imageUrl == null || "".equals(imageUrl)) {
			return ADV_IMAGEURL_HOST;
		}

		if (imageUrl.indexOf("http://") > -1) {
			return imageUrl;
		}

		if (imageUrl.indexOf("||") > -1) {
			imageUrl = imageUrl.split("\\|\\|")[0];
		}

		if ('/' != imageUrl.charAt(0)) {
			imageUrl = "/" + imageUrl;
		}

		return ADV_IMAGEURL_HOST + imageUrl;
	}

	public static String getHostPath(String hostUrl) {
		if (hostUrl == null || "".equals(hostUrl)) {
			return HOST_ADDRESS + INTERFACE_PATH;
		}

		if ('/' != hostUrl.charAt(0)) {
			hostUrl = "/" + hostUrl;
		}

		return HOST_ADDRESS + INTERFACE_PATH + hostUrl;
	}

	/**
	 * @Description: 获得下载路径
	 * @author: ethanchiu@126.com
	 * @date: Jun 9, 2014
	 * @return
	 */
	public static String getDownLoadPath() {

		if (ValueUtil.isStrEmpty(FILE_SERVER_HOST))
			return "";

		StringBuffer path = new StringBuffer(FILE_SERVER_HOST);

		if (!urlIsHasChar(FILE_SERVER_HOST, '/')) {
			path.append("/");
		}

		return path.append("fileserver/downloadFile").toString();
	}

	/**
	 * @Description: 获得上传路径
	 * @author: ethanchiu@126.com
	 * @date: Jun 9, 2014
	 * @return
	 */
	public static String getUpLoadPath() {

		if (ValueUtil.isStrEmpty(FILE_SERVER_HOST))
			return "";

		StringBuffer path = new StringBuffer(FILE_SERVER_HOST);

		if (!urlIsHasChar(FILE_SERVER_HOST, '/')) {
			path.append("/");
		}

		return path.append("fileserver/uploadIOFile").toString();

	}

	/**
	 * @Description: 检测url最后一个字符
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 * @param url
	 * @param c
	 * @return
	 */
	private static boolean urlIsHasChar(String url, char c) {

		if (ValueUtil.isStrEmpty(url)) {
			return false;
		}

		if (url.charAt(url.length() - 1) == c) {
			return true;
		}

		return false;

	}

	/**
	 * @Description: 获得天气请求地址
	 * @author: ethan.qiu@sosino.com
	 * @date: Oct 24, 2013
	 * @param param
	 * @return
	 */
	public static String getWeatherPath(String param) {

		StringBuffer sb = new StringBuffer();
		sb.append(WHEATHER_HOST).append(param).append(".html");
		return sb.toString();
	}

	public static String getWeatherImagePath(String param) {

		StringBuffer sb = new StringBuffer();

		String newParam = param.substring(1, param.length());

		sb.append(WHEATHER_IMAGE_HOST).append("b").append(newParam);
		return sb.toString();
	}
}
