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
package simple.compoment.log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import simple.framework.mvc.BaseApp;
import common.globe.ReleaseConfig;
import common.globe.TAGLOG;


/**
 * @Description: 日志打印类
 * @author: Ethan
 * @date: 2013-6-14
 */
public class LogUtil
{
	/**
	 * Priority constant for the println method; use Logger.v.
	 */
	public static final int VERBOSE = 2;

	/**
	 * Priority constant for the println method; use Logger.d.
	 */
	public static final int DEBUG = 3;

	/**
	 * Priority constant for the println method; use Logger.i.
	 */
	public static final int INFO = 4;

	/**
	 * Priority constant for the println method; use Logger.w.
	 */
	public static final int WARN = 5;

	/**
	 * Priority constant for the println method; use Logger.e.
	 */
	public static final int ERROR = 6;
	/**
	 * Priority constant for the println method.
	 */
	public static final int ASSERT = 7;
	
	private static HashMap<String, ILogger> loggerHashMap = new HashMap<String, ILogger>();
	private static final ILogger defaultLogger = new PrintToLogCatLogger();

	public static void addLogger(ILogger logger)
	{
		String loggerName = logger.getClass().getName();
		String defaultLoggerName = defaultLogger.getClass().getName();
		if (!loggerHashMap.containsKey(loggerName)
				&& !defaultLoggerName.equalsIgnoreCase(loggerName))
		{
			logger.open();
			loggerHashMap.put(loggerName, logger);
		}

	}

	public static void removeLogger(ILogger logger)
	{
		String loggerName = logger.getClass().getName();
		if (loggerHashMap.containsKey(loggerName))
		{
			logger.close();
			loggerHashMap.remove(loggerName);
		}
	}
	
	//============特殊日志 begin==============================
	public static void printList(Object obj, Object[] objs)
	{
		if(objs == null) return ;
		
		int len = objs.length;
		String mesg = "";
		for(int i = 0 ; i < len; i ++)
		{
			int j = i + 1;
			mesg += "[" + j + "." + objs[i].getClass().getSimpleName() + "] "; 
		}
		
		if(obj instanceof String){
			d((String)obj, mesg);
		}else{
			d(obj, mesg);
		}
		
	}
	
	
	
	//============特殊日志 end==============================
	
	
	//============跟踪日志 begin===============================
	
	public static void d(String tag, Object object, String message)
	{
		d(tag, "track:" + object.getClass().getSimpleName() + "-->" + message);
	}
	
	public static void e(String tag, Object object, String message)
	{
		d(tag, "track:" + object.getClass().getSimpleName() + "-->" + message);
	}
	
	public static void i(String tag, Object object, String message)
	{
		d(tag, "track:" + object.getClass().getSimpleName() + "-->" + message);
	}
	
	public static void v(String tag, Object object, String message)
	{
		d(tag, "track:" + object.getClass().getSimpleName() + "-->" + message);
	}
	
	public static void w(String tag, Object object, String message)
	{
		d(tag, "track:" + object.getClass().getSimpleName() + "-->" + message);
	}
	
	//============跟踪日志 end===============================
	
	/**
	 * @Description: 默认 tag
	 * @author: ethanchiu@126.com
	 * @date: Dec 2, 2013
	 * @param message
	 */
	public static void d(String message)
	{
		printLoger(DEBUG, TAGLOG.TAG_COMMON, message);
	}
	
	/**
	 * @Description: 默认 tag
	 * @author: ethanchiu@126.com
	 * @date: Dec 2, 2013
	 * @param message
	 */
	public static void e(String message)
	{
		printLoger(ERROR, TAGLOG.TAG_COMMON, message);
	}
	
	/**
	 * @Description: 默认 tag
	 * @author: ethanchiu@126.com
	 * @date: Dec 2, 2013
	 * @param message
	 */
	public static void i(String message)
	{
		printLoger(INFO, TAGLOG.TAG_COMMON, message);
	}
	
	
	public static void d(Object object, String message)
	{

		printLoger(DEBUG, object, message);

	}

	public static void e(Object object, String message)
	{

		printLoger(ERROR, object, message);

	}

	public static void i(Object object, String message)
	{

		printLoger(INFO, object, message);

	}

	public static void v(Object object, String message)
	{

		printLoger(VERBOSE, object, message);

	}

	public static void w(Object object, String message)
	{

		printLoger(WARN, object, message);

	}

	public static void d(String tag, String message)
	{

		printLoger(DEBUG, tag, message);

	}

	public static void e(String tag, String message)
	{
		
		printLoger(ERROR, tag, message);

	}

	public static void i(String tag, String message)
	{

		printLoger(INFO, tag, message);

	}

	public static void v(String tag, String message)
	{

		printLoger(VERBOSE, tag, message);

	}

	public static void w(String tag, String message)
	{

		printLoger(WARN, tag, message);

	}

	public static void println(int priority, String tag, String message)
	{
		printLoger(priority, tag, message);
	}

	private static void printLoger(int priority, Object object, String message)
	{
		Class<?> cls = object.getClass();
		String tag = cls.getName();
		String arrays[] = tag.split("\\.");
		tag = arrays[arrays.length - 1];
		printLoger(priority, tag, message);
	}

	private static void printLoger(int priority, String tag, String message)
	{
		if (ReleaseConfig.LOG_DEBUG)
		{
			printLoger(defaultLogger, priority, tag, message);
			Iterator<Entry<String, ILogger>> iter = loggerHashMap.entrySet()
					.iterator();
			while (iter.hasNext())
			{
				Map.Entry<String, ILogger> entry = iter.next();
				ILogger logger = entry.getValue();
				if (logger != null)
				{
					printLoger(logger, priority, tag, message);
				}
			}
		}
	}

	private static void printLoger(ILogger logger, int priority, String tag,
			String message)
	{

		switch (priority)
		{
		case VERBOSE:
			logger.v(tag, message);
			break;
		case DEBUG:
			logger.d(tag, message);
			break;
		case INFO:
			logger.i(tag, message);
			break;
		case WARN:
			logger.w(tag, message);
			break;
		case ERROR:
			logger.e(tag, message);
			break;
		default:
			break;
		}
	}
}
