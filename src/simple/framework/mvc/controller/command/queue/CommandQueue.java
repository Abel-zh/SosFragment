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
package simple.framework.mvc.controller.command.queue;

import java.util.concurrent.LinkedBlockingQueue;

import simple.compoment.log.LogUtil;
import simple.framework.mvc.controller.command.base.ICommand;

/**
 * @Description: 线程安全的任务队列
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class CommandQueue
{
	private LinkedBlockingQueue<ICommand> theQueue = new LinkedBlockingQueue<ICommand>();

	public CommandQueue()
	{
	}

	public void enqueue(ICommand cmd)
	{
		theQueue.add(cmd);
	}

	public synchronized ICommand getNextCommand()
	{
		ICommand cmd = null;
		try
		{
			cmd = theQueue.take();
		} catch (InterruptedException e)
		{
			LogUtil.i(CommandQueue.this, "没有获取到Command");
			e.printStackTrace();
		}
		return cmd;
	}

	public synchronized void clear()
	{
		LogUtil.i(CommandQueue.this, "清空所有Command");
		theQueue.clear();
	}
}
