package common.globe;

import common.base.AbsBaseApp;
import common.util.check.NetWorkUtil.NetType;

/**
 * @Description: Application
 * @author: ethan.qiu@sosino.com
 * @date: Oct 23, 2013
 */
public class App extends AbsBaseApp {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * 获取Application
	 * 
	 * @return
	 */
	public static App getApp() {
		return (App) application;
	}

	@Override
	protected void onConnect(NetType type) {
		super.onConnect(type);
	}

	@Override
	public void onDisConnect() {
		super.onDisConnect();
	}

}
