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
package simple.framework.mvc.controller.command.thread;

import simple.compoment.log.LogUtil;

/**
 * @Description: ThreadPool是command的线程池
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class ThreadPool
{
	// 线程的最大数量
	private static final int MAX_THREADS_COUNT = 2;
	private CommandThread threads[] = null;
	private boolean started = false;
	private static ThreadPool instance;

	private ThreadPool()
	{
	}

	public static ThreadPool getInstance()
	{
		if (instance == null)
		{
			instance = new ThreadPool();
		}
		return instance;
	}

	public void start()
	{
		if (!started)
		{
			LogUtil.i(ThreadPool.this, "线程池开始运行！");
			int threadCount = MAX_THREADS_COUNT;

			threads = new CommandThread[threadCount];
			for (int threadId = 0; threadId < threadCount; threadId++)
			{
				threads[threadId] = new CommandThread(threadId);
				threads[threadId].start();
			}
			started = true;
			LogUtil.i(ThreadPool.this, "线程池运行完成！");
		}
	}

	public void shutdown()
	{
		LogUtil.i(ThreadPool.this, "关闭所有线程中！");
		if (started)
		{
			for (CommandThread thread : threads)
			{
				thread.stop();
			}
			threads = null;
			started = false;
		}
		LogUtil.i(ThreadPool.this, "关闭完所有线程！");
	}
}
