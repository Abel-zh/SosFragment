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
package simple.framework.mvc.controller.command;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import simple.compoment.log.LogUtil;
import simple.framework.mvc.controller.command.base.ICommand;
import simple.framework.mvc.controller.command.queue.CommandQueueManager;
import simple.framework.mvc.model.IResponseListener;
import simple.framework.mvc.model.RequestEntity;

import common.exception.NoSuchCommandException;

/**
 * @Description: 实际的Command的执行者，查询，注册，注销等。
 * 
 *               创建时保证CommandQueueManager创建。
 * 
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public class CommandExecutor {
	private final HashMap<String, Class<? extends ICommand>> commands = new HashMap<String, Class<? extends ICommand>>();

	private static final CommandExecutor instance = new CommandExecutor();
	private boolean initialized = false;

	public CommandExecutor() {
		ensureInitialized();
	}

	public static CommandExecutor getInstance() {
		return instance;
	}

	public void ensureInitialized() {
		if (!initialized) {
			LogUtil.i(CommandExecutor.this, "CommandExecutor初始化");
			CommandQueueManager.getInstance().initialize();
			initialized = true;
		}
	}

	/**
	 * 所有命令终止或标记为结束
	 */
	public void terminateAll() {
	}

	/**
	 * 命令入列
	 * 
	 * @param commandKey
	 *            命令ID
	 * @param request
	 *            提交的参数
	 * @param listener
	 *            响应监听器
	 * @throws NoSuchCommandException
	 */
	public void enqueueCommand(String commandKey, RequestEntity request, IResponseListener listener)
			throws NoSuchCommandException {
		final ICommand cmd = getCommand(commandKey);
		enqueueCommand(cmd, request, listener);
	}

	public void enqueueCommand(ICommand command, RequestEntity request, IResponseListener listener)
			throws NoSuchCommandException {
		if (command != null) {
			command.setRequest(request);
			command.setResponseListener(listener);
			CommandQueueManager.getInstance().enqueue(command);
		}
	}

	public void enqueueCommand(ICommand command, RequestEntity request) throws NoSuchCommandException {
		enqueueCommand(command, null, null);
	}

	public void enqueueCommand(ICommand command) throws NoSuchCommandException {
		enqueueCommand(command, null);
	}

	/**
	 * 拿到对应的功能command，不是队列中的command
	 * 
	 * @param commandKey
	 * @return
	 * @throws NoSuchCommandException
	 */
	private ICommand getCommand(String commandKey) throws NoSuchCommandException {
		ICommand rv = null;

		if (commands.containsKey(commandKey)) {
			Class<? extends ICommand> cmd = commands.get(commandKey);

			LogUtil.d(this, "current command-->" + commandKey);
			// LogMgr.d(this, "all commands-->" + commands.toString());

			if (cmd != null) {
				// 获取class的修饰符类型
				int modifiers = cmd.getModifiers();

				// LogMgr.d(this, "modifiers-->" + modifiers);

				// TODO 从计算结果看，修饰符不能为abstract
				if ((modifiers & Modifier.ABSTRACT) == 0 && (modifiers & Modifier.INTERFACE) == 0) {
					try {
						rv = cmd.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
						throw new NoSuchCommandException("没发现" + commandKey + "命令");
					}
				} else {
					throw new NoSuchCommandException("没发现" + commandKey + "命令");
				}
			}
		}

		return rv;
	}

	public void registerCommand(String commandKey, Class<? extends ICommand> command) {
		if (command != null) {
			commands.put(commandKey, command);

			LogUtil.d(this, "commands.put(commandKey)-->" + commandKey + "  commands-->" + commands.toString());
		}
	}

	public void unregisterCommand(String commandKey) {
		commands.remove(commandKey);
	}
}
