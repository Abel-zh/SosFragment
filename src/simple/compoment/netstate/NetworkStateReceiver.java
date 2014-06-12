/*
 * Copyright (C) 2013  ethanchiu@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simple.compoment.netstate;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import common.util.ToastUtil;
import common.util.check.NetWorkUtil;
import common.util.check.NetWorkUtil.NetType;

/**
 * @Description:检测网络状态改变
 * 需要配置 
 * <receiver android:name="com.ta.util.netstate.NetworkStateReceiver" >
 *   <intent-filter> 
 *   	<action android:name="android.net.conn.CONNECTIVITY_CHANGE" /> 
 *   	<action android:name="android.gzcpc.conn.CONNECTIVITY_CHANGE" />
 *   </intent-filter> 
 * </receiver>
 * 需要开启权限 
 * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * 
 * @author: ethanchiu@126.com
 * @date: 2013-7-1
 */
public class NetworkStateReceiver extends BroadcastReceiver
{
	private static Boolean networkAvailable = false;
	private static NetType netType;
	private static ArrayList<NetChangeObserver> netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
	private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public final static String TA_ANDROID_NET_CHANGE_ACTION = "ta.android.net.conn.CONNECTIVITY_CHANGE";
	private static BroadcastReceiver receiver;

	private static BroadcastReceiver getReceiver()
	{
		if (receiver == null)
		{
			receiver = new NetworkStateReceiver();
		}
		return receiver;
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		receiver = NetworkStateReceiver.this;
		if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)
				|| intent.getAction().equalsIgnoreCase(
						TA_ANDROID_NET_CHANGE_ACTION))
		{
			if (!NetWorkUtil.isNetworkAvailable(context))
			{
				networkAvailable = false;
				ToastUtil.show("网络断开连接");
				
			} else
			{
				netType = NetWorkUtil.getAPNType(context);
				networkAvailable = true;
				ToastUtil.show("网络连接成功");
			}
			notifyObserver();
		}
	}

	/**
	 * 注册网络状态广播
	 * 
	 * @param mContext
	 */
	public static void registerNetworkStateReceiver(Context mContext)
	{
		IntentFilter filter = new IntentFilter();
		filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
		filter.addAction(ANDROID_NET_CHANGE_ACTION);
		mContext.getApplicationContext()
				.registerReceiver(getReceiver(), filter);
	}

	/**
	 * 检查网络状态
	 * 
	 * @param mContext
	 */
	public static void checkNetworkState(Context mContext)
	{
		Intent intent = new Intent();
		intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
		mContext.sendBroadcast(intent);
	}

	/**
	 * 注销网络状态广播
	 * 
	 * @param mContext
	 */
	public static void unRegisterNetworkStateReceiver(Context mContext)
	{
		if (receiver != null)
		{
			try
			{
				mContext.getApplicationContext().unregisterReceiver(receiver);
			} catch (Exception e)
			{
			}
		}

	}

	/**
	 * 获取当前网络状态，true为网络连接成功，否则网络连接失败
	 * 
	 * @return
	 */
	public static Boolean isNetworkAvailable()
	{
		return networkAvailable;
	}

	public static NetType getAPNType()
	{
		return netType;
	}

	private void notifyObserver()
	{

		for (int i = 0; i < netChangeObserverArrayList.size(); i++)
		{
			NetChangeObserver observer = netChangeObserverArrayList.get(i);
			if (observer != null)
			{
				if (isNetworkAvailable())
				{
					observer.onConnect(netType);
				} else
				{
					observer.onDisConnect();
				}
			}
		}

	}

	/**
	 * 注册网络连接观察者
	 * 
	 * @param observerKey
	 *            observerKey
	 */
	public static void registerObserver(NetChangeObserver observer)
	{
		if (netChangeObserverArrayList == null)
		{
			netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
		}
		netChangeObserverArrayList.add(observer);
	}

	/**
	 * 注销网络连接观察者
	 * 
	 * @param resID
	 *            observerKey
	 */
	public static void removeRegisterObserver(NetChangeObserver observer)
	{
		if (netChangeObserverArrayList != null)
		{
			netChangeObserverArrayList.remove(observer);
		}
	}

}