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
package simple.compoment.db;

import simple.compoment.db.SQLiteDBManager.DBUpdateListener;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Description: 管理数据库的创建和版本更新
 * @author: ethanchiu@126.com
 * @date: 2013-6-21
 */
public class DBHelper extends SQLiteOpenHelper
{
	/**
	 * 数据库更新监听器
	 */
	private DBUpdateListener mTadbUpdateListener;

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库名字
	 * @param factory
	 *            可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标
	 * @param version
	 *            数据库版本
	 */
	public DBHelper(Context context, String name, CursorFactory factory,
			int version)
	{
		super(context, name, factory, version);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据库名字
	 * @param factory
	 *            可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标
	 * @param version
	 *            数据库版本
	 * @param dbUpdateListener
	 *            数据库更新监听器
	 */
	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DBUpdateListener dbUpdateListener)
	{
		super(context, name, factory, version);
		this.mTadbUpdateListener = dbUpdateListener;
	}

	/**
	 * 设置数据库更新监听器
	 * 
	 * @param mTadbUpdateListener
	 *            数据库更新监听器
	 */
	public void setOndbUpdateListener(DBUpdateListener dbUpdateListener)
	{
		this.mTadbUpdateListener = dbUpdateListener;
	}

	public void onCreate(SQLiteDatabase db)
	{
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if (mTadbUpdateListener != null)
		{
			mTadbUpdateListener.onUpgrade(db, oldVersion, newVersion);
		}
	}

}
