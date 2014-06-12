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
package simple.framework.mvc;

import simple.compoment.log.LogUtil;
import simple.framework.ioc.annotation.impl.ActivityInjector;
import simple.framework.mvc.model.IResponseListener;
import simple.framework.mvc.model.RequestEntity;
import simple.framework.mvc.model.ResponseEntity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.abel.naicha.R;
import com.naicha.view.titlebar.TitleBar;
import common.base.AbsBaseApp;
import common.exception.NoSuchNameLayoutException;
import common.globe.CMDKey;
import common.globe.TAGLOG;
import common.util.check.NetWorkUtil.NetType;

/**
 * @Description: activity基类，所有activity都继承这个类
 * @author: ethanchiu@126.com
 * @date: 2013-7-8
 */
public abstract class BaseActivity extends FragmentActivity {
	private LinearLayout mBaseLayout;

	/** 模块的名字 */
	private String moduleName = "";
	/** 布局文件的名字 */
	private String layouName = "";

	private TitleBar mTitleBar;

	private Bundle bundle;// TODO 暂时为getIntent().getExtras()获得的Bundle

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		notifiyApplicationActivityCreating();
		onPreOnCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		getApp().getAppManager().addActivity(this);
		initViews();
		onAfterOnCreate(savedInstanceState);
		notifiyApplicationActivityCreated();

		LogUtil.d(TAGLOG.TAG_ACTIVITY, this, "onCreate");
		LogUtil.printList(TAGLOG.TAG_ACTIVITY, ActivityMgr.getActivityMgr().peekAllActivity());

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		// TODO activity管理：销毁时，把当前activity移除
		getApp().getAppManager().removeActivity(this);

		LogUtil.d(TAGLOG.TAG_ACTIVITY, this, "onDestroy");
		LogUtil.printList(TAGLOG.TAG_ACTIVITY, ActivityMgr.getActivityMgr().peekAllActivity());

		super.onDestroy();
	}

	public AbsBaseApp getApp() {
		return (AbsBaseApp) getApplication();
	}

	private void notifiyApplicationActivityCreating() {
		getApp().onActivityCreating(this);
	}

	private void notifiyApplicationActivityCreated() {
		getApp().onActivityCreated(this);
	}

	protected void onPreOnCreate(Bundle savedInstanceState) {
	}

	protected void onAfterOnCreate(Bundle savedInstanceState) {
	}

	private void initViews() {
		getModuleName();
		getLayouName();
		initInjector();
		loadDefautLayout();
	}

	/**
	 * 实例化InjectResource注解和Inject注解的资源文件
	 */
	private void initInjector() {
		ActivityInjector.getInstance().injectResource(this);
		ActivityInjector.getInstance().inject(this);
	}

	/**
	 * 实例化了布局layout和activity的view成员
	 */
	protected void loadDefautLayout() {
		try {
			ActivityInjector injector = ActivityInjector.getInstance();

			if (injector.isNeedBaseLayout(this.getClass())) {
				super.setContentView(R.layout.activity_base);

				if (injector.getBackGruandId(this.getClass()) != -1) {
					findViewById(R.id.rootLayout).setBackgroundResource(injector.getBackGruandId(this.getClass()));
				}

				mBaseLayout = (LinearLayout) findViewById(R.id.content);

				LayoutInflater inflater = LayoutInflater.from(BaseActivity.this);
				inflater.inflate(getLayoutId(), mBaseLayout);
				initMemberViews();
				initBaseView();
			} else {
				setContentView(getLayoutId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initBaseView() {
		mTitleBar = (TitleBar) findViewById(R.id.titlebar);
		mTitleBar.setLeft1Listener(new OnClickListener() {// TODO
															// TITLEBAR暂时在这里注册事件
					@Override
					public void onClick(View v) {
						BaseActivity.this.finish();
					}
				});
	}

	public TitleBar getTitleBar() {
		return mTitleBar;
	}

	/**
	 * @Description: 基类布局
	 * @author: ethanchiu@126.com
	 * @date: Dec 20, 2013
	 * @return
	 */
	abstract protected int getLayout();

	/**
	 * 获得注释或者默认的布局id
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NameNotFoundException
	 * @throws NoSuchNameLayoutException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 */
	private int getLayoutId() throws IllegalArgumentException, NameNotFoundException, NoSuchNameLayoutException,
			ClassNotFoundException, IllegalAccessException {
		int layoutResID = -100;

		layoutResID = ActivityInjector.getInstance().getLayoutId(this.getClass());
		if (layoutResID < 0)
			layoutResID = getApp().getLayoutLoader().getLayoutID(moduleName);

		return layoutResID;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initMemberViews();
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initMemberViews();
	}

	/**
	 * 由于view必须在视图记载之后添加注入
	 */
	protected void initMemberViews() {

		ActivityInjector.getInstance().injectView(this);
		ActivityInjector.getInstance().injectViewMulti(this);
		onSetClickListener();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		// 由于view必须在视图记载之后添加注入
		ActivityInjector.getInstance().injectView(this);
		onSetClickListener();
	}

	/**
	 * 所有view都实例化完成，可以进行事件绑定
	 */
	protected void onSetClickListener() {
	}

	/**
	 * 根据当前activity名映射布局文件名
	 */
	public String getModuleName() {
		String moduleName = this.moduleName;
		if (moduleName == null || moduleName.equalsIgnoreCase("")) {
			// TODO 这里指定了activity类名和对应的layout的映射规则
			moduleName = "activity_" + getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 8);

			this.moduleName = moduleName.toLowerCase();

		}
		return moduleName;
	}

	/**
	 * 设置模块的名字
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * 获取布局文件名
	 * 
	 * @return布局文件名
	 */
	public String getLayouName() {
		String layouName = this.layouName;

		if (layouName == null || layouName.equalsIgnoreCase("")) {
			this.layouName = this.moduleName;
		}
		return layouName;
	}

	/**
	 * 设置布局文件名
	 */
	protected void setLayouName(String layouName) {
		this.layouName = layouName;
	}

	/**
	 * 在activity创建之前，如何处理Response
	 * 
	 * @param response
	 */
	public void preProcessData(Bundle b) {
		if (b != null)
			bundle = b;
	}

	/**
	 * TODO 暂时的理解
	 * 
	 * 当activity创建完了，如何处理Response
	 * 
	 * @param response
	 */
	public void processData(ResponseEntity response) {
	}

	public final void doCommand(String commandKey, RequestEntity request, boolean showProgress) {
		doCommand(commandKey, request, null, showProgress);
	}

	public final void doCommand(String commandKey, RequestEntity request) {
		doCommand(commandKey, request, null, false);
	}

	public final void doCommand(String commandKey, RequestEntity request, IResponseListener listener) {
		doCommand(commandKey, request, listener, false);
	}

	public final void doCommand(String commandKey, RequestEntity request, IResponseListener listener,
			boolean showProgress) {
		doCommand(commandKey, request, listener, showProgress, true);
	}

	public final void doCommand(String commandKey, RequestEntity request, IResponseListener listener,
			boolean showProgress, boolean record) {
		doCommand(commandKey, request, listener, showProgress, record, false);
	}

	/**
	 * @Description: fianl
	 * @author: ethanchiu@126.com
	 * @date: Sep 11, 2013
	 * @param commandKey
	 * @param request
	 * @param listener
	 * @param showProgress
	 * @param record
	 * @param resetStack
	 */
	public final void doCommand(String commandKey, RequestEntity request, IResponseListener listener,
			boolean showProgress, boolean record, boolean resetStack) {
		// if (showProgress)showProgress();
		getApp().doCommand(commandKey, request, listener);
	}

	// =========doActivity begin========
	public final void doActivity(Class<? extends BaseActivity> activityClazz) {
		doActivity(activityClazz, null);
	}

	public final void doActivity(Class<? extends BaseActivity> activityClazz, boolean record) {
		doActivity(activityClazz, null, record);
	}

	public final void doActivity(Class<? extends BaseActivity> activityClazz, Bundle bundle) {
		doActivity(activityClazz, bundle, true);
	}

	public final void doActivity(Class<? extends BaseActivity> activityClazz, Bundle bundle, boolean record) {
		doActivity(activityClazz, bundle, record, false);
	}

	public final void doActivity(Class<? extends BaseActivity> activityClazz, Bundle bundle, boolean record,
			boolean resetStack) {
		String activityKey = activityClazz.getSimpleName();
		getApp().registerActivity(activityKey, activityClazz);

		RequestEntity request = new RequestEntity();
		request.setTag(activityKey);
		request.setData(bundle);
		getApp().doActivity(CMDKey.CMD_ACTIVITY_ID, request, null, false, resetStack);
	}

	// =========doActivity end========

	/**
	 * 网络连接连接时调用
	 */
	public void onConnect(NetType type) {
		// wifi, CMNET, CMWAP, noneNet
		switch (type) {
		case wifi: {
			LogUtil.d("当前网络:wifi");
			break;
		}
		case CMNET: {
			LogUtil.d("当前网络:CMNET");
			break;
		}
		case CMWAP: {
			LogUtil.d("当前网络:CMWAP");
			break;
		}
		case noneNet: {
			LogUtil.d("当前网络:noneNet");
			break;
		}

		default:
			break;
		}
	}

	/**
	 * 等待实现 当前没有网络连接
	 */
	public void onDisConnect() {
		LogUtil.d("当前没有网络连接");
	}

	/**
	 * 退出应用程序
	 * 
	 * @param isBackground
	 *            是否开开启后台运行,如果为true则为后台运行
	 */
	public void exitApp(Boolean isBackground) {
		getApp().exitApp(isBackground);
	}

	/**
	 * 退出应用程序
	 * 
	 */
	public void exitApp() {
		getApp().exitApp();
	}

	/**
	 * 退出应用程序，且在后台运行
	 */
	public void exitAppToBackground() {
		getApp().exitApp(true);
	}

	@Override
	public void finish() {
		getApp().getAppManager().removeActivity(this);
		super.finish();
		// TODO activity跳转动画
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	public Bundle getBundle() {
		return bundle;
	}

}
