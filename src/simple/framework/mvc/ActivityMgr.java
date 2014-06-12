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
package simple.framework.mvc;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @Description: 对activity栈管理
 * @author: ethanchiu@126.com
 * @date: 2013-6-21
 */
public class ActivityMgr
{
	private static Stack<Activity> activityStack;
	private static ActivityMgr instance;

	private ActivityMgr()
	{
	}

	public static ActivityMgr getActivityMgr()
	{
		if (instance == null)
		{
			instance = new ActivityMgr();
		}
		return instance;
	}
	
	public Object[] peekAllActivity(){
		
		if(activityStack == null || activityStack.isEmpty()) return null;
		
		return activityStack.toArray();
	}
	
	public void addActivity(Activity activity)
	{
		if (activityStack == null)
		{
			activityStack = new Stack<Activity>();
		}
		
		activityStack.add(activity);
	}

	public Activity currentActivity()
	{
		Activity activity = activityStack.lastElement();
		return activity;
	}
	
	public void removeActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity = null;
		}
	}
	
	public void finishActivity()
	{
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}
	
	public void finishActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	public void finishActivity(Class<?> cls)
	{
		for (Activity activity : activityStack)
		{
			if (activity.getClass().equals(cls))
			{
				finishActivity(activity);
			}
		}
	}
	
	/**
	 * 
	 * @Method: keepCurrentClass
	 * @Description: 保留传入的class，其他全部finish
	 * @param cls
	 *            保留的class
	 * @return void
	 */
	public void keepCurrentClass(Class<?> cls) {
		Iterator<Activity> iter = activityStack.iterator();
		while (iter.hasNext()) {
			Activity activity = iter.next();
			if (!activity.getClass().equals(cls) && activity != null) {
				iter.remove();
				activity.finish();
				activity = null;
			}
		}
	}
	
	public void finishAllActivity()
	{
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			if (null != activityStack.get(i))
			{
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	public void AppExit(Context context, Boolean isBackground)
	{
		try
		{
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
		} catch (Exception e)
		{

		} finally
		{
			// 注意，如果您有后台程序运行，请不要支持此句子
			if (!isBackground)
			{
				System.exit(0);
			}
		}
	}
}