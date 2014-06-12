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
package simple.framework.ioc.layoutloader;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import common.exception.NoSuchNameLayoutException;

/**
 * @Description: SystemResLoader是获取系统资源加载器
 * @author: ethanchiu@126.com
 * @date: 2013-6-19
 */
public class LayoutLoader implements ILayoutLoader
{
	private static LayoutLoader instance;
	private Context mContext;

	private LayoutLoader(Context context)
	{
		this.mContext = context;
	}
	
	public static LayoutLoader getInstance(Context context)
	{
		if (instance == null)
		{
			instance = new LayoutLoader(context);
		}
		return instance;
	}
	public int getLayoutID(String resIDName) throws NameNotFoundException,
			ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, NoSuchNameLayoutException
	{
		int resID = readResID("layout", resIDName);
		if (resID == 0)
		{
			throw new NoSuchNameLayoutException();
		}
		return resID;
	}
	public int readResID(String type, String resIDName)
			throws NameNotFoundException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException
	{
		String packageName;
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
		packageName = pi.packageName;
		if (packageName == null || packageName.equalsIgnoreCase(""))
		{
			throw new NameNotFoundException("没有获取到系统包名！");
		}
		packageName = packageName + ".R";
		Class<?> clazz = Class.forName(packageName);
		Class<?> cls = readResClass(clazz, packageName + "$" + type);
		if (cls == null)
		{
			throw new NameNotFoundException("没发现资源包名！");
		}
		return readResID(cls, resIDName);

	}

	public Class<?> readResClass(Class<?> cls, String respackageName)
	{
		Class<?>[] classes = cls.getDeclaredClasses();
		for (int i = 0; i < classes.length; i++)
		{
			Class<?> tempClass = classes[i];
//			Log.v("TAReadSystemRes", tempClass.getName());
			if (tempClass.getName().equalsIgnoreCase(respackageName))
			{
				return tempClass;
			}
		}
		return null;
	}

	public int readResID(Class<?> cls, String resIDName)
			throws IllegalArgumentException, IllegalAccessException
	{
		Field[] fields = cls.getDeclaredFields();
		for (int j = 0; j < fields.length; j++)
		{
			
			if (fields[j].getName().equalsIgnoreCase(resIDName))
			{
				return fields[j].getInt(cls);
			}
		}
		return 0;
	}

}
