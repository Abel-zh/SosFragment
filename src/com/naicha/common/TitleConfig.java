package com.naicha.common;

import com.abel.naicha.R;
import com.naicha.view.titlebar.TitleBar;

/**
 * @Description: 标题配置
 * @author: ethanchiu@126.com
 * @date: May 25, 2014
 */
public class TitleConfig {
	public static enum PAGE_TYPE {
		HOME, // 首页
	}

	public static void toTitle(PAGE_TYPE pageType, TitleBar titleBar) {
		switch (pageType) {
		case HOME: {
			titleBar.setTitle(R.string.app_name);
			titleBar.setLeft1Image(R.drawable.ic_launcher);
			break;
		}
		default:
			break;
		}
	}

}
