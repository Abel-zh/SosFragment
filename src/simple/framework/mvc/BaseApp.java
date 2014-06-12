package simple.framework.mvc;

import java.util.HashMap;

import simple.compoment.log.LogUtil;
import simple.framework.ioc.layoutloader.ILayoutLoader;
import simple.framework.ioc.layoutloader.LayoutLoader;
import simple.framework.mvc.controller.command.CommandExecutor;
import simple.framework.mvc.controller.command.base.ICommand;
import simple.framework.mvc.model.IResponseListener;
import simple.framework.mvc.model.RequestEntity;
import simple.framework.mvc.model.ResponseEntity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import common.exception.NoSuchCommandException;
import common.util.check.NetWorkUtil.NetType;

/**
 * @Description: Application
 * @author: Ethan
 * @date: 2013-6-14
 */
public abstract class BaseApp extends Application
{

	/** 获取布局文件ID加载器 */
	private ILayoutLoader mLayoutLoader;
	
	private CommandExecutor mCommandExecutor;

	private BaseActivity currentActivity;

	private final HashMap<String, Class<? extends BaseActivity>> registeredActivities = 
			new HashMap<String, Class<? extends BaseActivity>>();
	
	private ActivityMgr mAppManager;
	
	protected static BaseApp application;
	
	public static BaseApp getApp(){
		return application;
	}
	
	@Override
	public void onCreate()
	{
		application = this;
		
		
		onPreCreateApplication();
		super.onCreate();
		doOncreate();
		onAfterCreateApplication();
		getAppManager();
	}

	private void doOncreate()
	{
		mCommandExecutor = CommandExecutor.getInstance();
	}

	protected void onPreCreateApplication()
	{
	}

	protected void onAfterCreateApplication()
	{
	}

	public void onDisConnect()
	{
		if (currentActivity != null)
		{
			currentActivity.onDisConnect();
		}
	}

	protected void onConnect(NetType type)
	{
		if (currentActivity != null)
		{
			currentActivity.onConnect(type);
		}
	}

	public ILayoutLoader getLayoutLoader()
	{
		if (mLayoutLoader == null)
		{
			mLayoutLoader = LayoutLoader.getInstance(this);
		}
		return mLayoutLoader;
	}

	public void setLayoutLoader(ILayoutLoader layoutLoader)
	{
		this.mLayoutLoader = layoutLoader;
	}

	/**
	 * @Description: 在activity创建之前获得Reponse的信息
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param activity
	 */
	public void onActivityCreating(BaseActivity activity)
	{
		Bundle b = activity.getIntent().getExtras();
		activity.preProcessData(b);

	}

	/**
	 * @Description: 销毁栈顶activity，显示当前activity
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param activity
	 */
	public void onActivityCreated(BaseActivity activity)
	{
		currentActivity = activity;
	}


	/**
	 * @Description: 执行command
	 * @author: ethanchiu@126.com
	 * @date: Dec 23, 2013
	 * @param commandKey
	 * @param request
	 * @param listener
	 */
	public void doCommand(String commandKey, RequestEntity request, IResponseListener listener){
		try
		{
			CommandExecutor.getInstance().enqueueCommand(commandKey,
					request, listener);
		} catch (NoSuchCommandException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 执行activity
	 * @author: ethanchiu@126.com
	 * @date: Sep 11, 2013
	 * @param commandKey  请求的类型
	 * @param request     请求参数
	 * @param listener    响应的监听
	 * @param record      是否要保存在栈中。
	 * @param resetStack  是否要重置栈中的数据。
	 */
	public void doActivity(String commandKey, RequestEntity request,
			IResponseListener listener, boolean record, boolean resetStack)
	{
		if(request == null)return;

		ResponseEntity response = new ResponseEntity();
		response.setTag(request.getTag());
		response.setData(request.getData());

		String targetActivityKey = (String) response.getTag();
		Class<? extends BaseActivity> clazz = registeredActivities.get(targetActivityKey);

		if (clazz != null)
		{
			Intent i = new Intent(currentActivity, clazz);

			if(response.getData() != null){
				Bundle b = (Bundle) response.getData();
				i.putExtras(b);
			}
			currentActivity.startActivity(i);
			//TODO activity跳转动画

		}
	}

	public BaseActivity getCurrentActivity(){
		return currentActivity;
	}

	public void registerActivity(int resID, Class<? extends BaseActivity> clz)
	{
		String activityKey = getString(resID);
		registerActivity(activityKey, clz);
	}

	public void registerActivity(String activityKey, Class<? extends BaseActivity> clz)
	{
		registeredActivities.put(activityKey, clz);

		LogUtil.d(this, "registeredActivities.put-->" + activityKey + "   all activity-->" + registeredActivities.toString());

	}

	public void unregisterActivity(int resID)
	{
		String activityKey = getString(resID);
		unregisterActivity(activityKey);
	}

	public void unregisterActivity(String activityKey)
	{
		registeredActivities.remove(activityKey);
	}

	public void registerCommand(int resID, Class<? extends ICommand> command)
	{
		String commandKey = getString(resID);
		registerCommand(commandKey, command);
	}

	public void registerCommand(String commandKey,
			Class<? extends ICommand> command)
	{
		if (command != null)
		{
			mCommandExecutor.registerCommand(commandKey, command);
		}
	}

	public void unregisterCommand(int resID)
	{
		String commandKey = getString(resID);
		unregisterCommand(commandKey);
	}

	public void unregisterCommand(String commandKey)
	{
		mCommandExecutor.unregisterCommand(commandKey);
	}
	

	public ActivityMgr getAppManager()
	{
		if (mAppManager == null)
		{
			mAppManager = ActivityMgr.getActivityMgr();
		}
		return mAppManager;
	}

	public void exitApp(){
		exitApp(false);
	}

	/**
	 * 退出应用程序
	 * 
	 * @param isBackground
	 *            是否开开启后台运行,如果为true则为后台运行
	 */
	public void exitApp(Boolean isBackground)
	{
		mAppManager.AppExit(this, isBackground);
	}


}
