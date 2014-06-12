package simple.framework.mvc.controller.command.queue;

import simple.framework.mvc.controller.command.base.ICommand;
import simple.framework.mvc.controller.command.thread.ThreadPool;

/**
 * @Description: 具体的command队列管理者，出队，入队，清空等。同时负责线程池开启。
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public final class CommandQueueManager
{
	private static CommandQueueManager instance;
	private boolean initialized = false;
	private ThreadPool pool;
	private CommandQueue queue;

	private CommandQueueManager()
	{
	}

	public static CommandQueueManager getInstance()
	{
		if (instance == null)
		{
			instance = new CommandQueueManager();
		}
		return instance;
	}
	
	/**
	 * 初始化好任务队列和线程池
	 */
	public void initialize()
	{
		if (!initialized)
		{
			queue = new CommandQueue();
			pool = ThreadPool.getInstance();

			pool.start();
			initialized = true;
		}
	}
	
	/**
	 * 从队列中获取Command
	 * 
	 * @return ICommand
	 */
	public ICommand getNextCommand()
	{
		ICommand cmd = queue.getNextCommand();
		return cmd;
	}

	/**
	 * 添加Command到队列中
	 */
	public void enqueue(ICommand cmd)
	{
		queue.enqueue(cmd);
	}

	/**
	 * 清除队列
	 */
	public void clear()
	{
		queue.clear();
	}

	/**
	 * 关闭队列
	 */
	public void shutdown()
	{
		if (initialized)
		{
			queue.clear();
			pool.shutdown();
			initialized = false;
		}
	}
}
