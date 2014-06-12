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

import simple.framework.mvc.controller.command.base.ICommand;
import simple.framework.mvc.controller.command.queue.CommandQueueManager;

/**
 * @Description: 专门执行command的线程
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class CommandThread implements Runnable
{
	private int threadId;
	private Thread thread = null;
	private boolean running = false;
	private boolean stop = false;

	public CommandThread(int threadId)
	{
		this.threadId = threadId;
		thread = new Thread(this);
	}

	public void run()
	{
		while (!stop)
		{
			ICommand cmd = CommandQueueManager.getInstance().getNextCommand();
			cmd.execute();
		}
	}

	public void start()
	{
		thread.start();
		running = true;
	}

	public void stop()
	{
		stop = true;
		running = false;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getThreadId()
	{
		return threadId;
	}
}
