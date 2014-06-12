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
package simple.framework.mvc.controller.command.base;

import java.lang.ref.SoftReference;

import simple.compoment.log.LogUtil;
import simple.framework.mvc.model.IResponseListener;
import simple.framework.mvc.model.ResponseEntity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @Description: 一个富有执行力的Command管理类。封装接口回调的调度。
 * @author: ethanchiu@126.com
 * @date: 2013-6-13
 */
public abstract class AbsCommand extends BaseCommand
{
	protected final static int command_start = 1;
	protected final static int command_runing = 2;
	protected final static int command_failure = 3;
	protected final static int command_success = 4;
	protected final static int command_finish = 5;
	private IResponseListener listener;
	
	public AbsCommand(){
		
		if ( Looper.myLooper() != null ){//表示在UI线程中
			LogUtil.d("主 thread");
			handler = new MyHandler(this);
		}else{
			LogUtil.d("非主 thread");
		}
	}
	
	private MyHandler handler;
	
	/**
	 * @Description: 使用静态handler防止内存泄露
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 */
	static class MyHandler extends Handler{
		
		private final SoftReference<AbsCommand> cmdRdf; 
		
		public MyHandler(AbsCommand cmd){
			cmdRdf = new SoftReference<AbsCommand>(cmd);
		}
		
		public void handleMessage(Message msg)
		{
			
			try//TODO 这里把所有执行任务的一场都捕获了,不便于定位，后期要改
			{
				//FIXME 这里的cmd可能会空，fix 
				AbsCommand cmd = cmdRdf.get();
				
				IResponseListener resplistener = cmd.getResponseListener();
				
				switch (msg.what)
				{
					case command_success:
						resplistener.onSuccess(cmd.getResponse());
						break;
					case command_failure:
						resplistener.onFailure(cmd.getResponse());
						break;
					default:
						break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public final void execute()
	{

		onPreExecuteCommand();//接口不为空，就执行IResponseListener中onStart()方法
		executeCommand();
		onPostExecuteCommand();
	}

	protected abstract void executeCommand();

	/**
	 * 执行command之前的工作
	 */
	protected void onPreExecuteCommand()
	{
	}

	/**
	 * executeCommand()完成之后的执行任务
	 */
	protected void onPostExecuteCommand()
	{
		//TODO 在这里隐藏等待框
		//		BaseApp.getApp().getCurrentActivity().dismissDialog();
	}


	/**
	 * @Description: 发送成功任务
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 * @param object
	 */
	public void sendSuccessMessage(Object object)
	{
		ResponseEntity response = new ResponseEntity();
		response.setData(object);
		setResponse(response);
		
		listener = getResponseListener();
		
		if (listener != null)
		{
			if(handler == null){
				listener.onSuccess(getResponse());
			}else if(!Thread.currentThread().isInterrupted()){
				handler.sendEmptyMessage(command_success);
			}else{
				LogUtil.e("curentthread is interrupted");
			}
		}
		
	}

	/**
	 * @Description: 发送失败任务
	 * @author: ethanchiu@126.com
	 * @date: Jun 10, 2014
	 * @param object
	 */
	public void sendFailureMessage(Object object)
	{

		ResponseEntity response = new ResponseEntity();
		
		if(object instanceof Object[]){
			Object[] objs = (Object[])object;
			response.setData(objs[0]);
			response.setData2(objs[1]);
		}else{
			response.setData(object);
		}
		
		setResponse(response);
		
		listener = getResponseListener();
		
		if (listener != null)
		{
			if(handler == null){
				listener.onFailure(getResponse());
			}else if(!Thread.currentThread().isInterrupted()){
				handler.sendEmptyMessage(command_failure);
			}else{
				LogUtil.e("curentthread is interrupted");
			}
		}
		
	}

}
