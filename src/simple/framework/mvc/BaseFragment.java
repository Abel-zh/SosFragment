package simple.framework.mvc;

import simple.compoment.log.LogUtil;
import simple.framework.ioc.annotation.impl.ActivityInjector;
import simple.framework.ioc.annotation.impl.FragmentInjector;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import common.base.AbsBaseActivity;
import common.exception.NoSuchNameLayoutException;
import common.globe.TAGLOG;

/**
 * @Description: Fragment基类
 * @author: ethanchiu@126.com
 * @date: 2013-7-29
 */
public class BaseFragment extends Fragment
{

	protected Activity mParentActivity;
	protected FragmentManager mFragmentManager;

	protected ProgressDialog mProgressDialog;

	private String moduleName = "";

	/** 这个fragment 的布局view */
	private View layoutView = null;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		mParentActivity = activity;
	}

	@SuppressLint("CommitTransaction")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		LogUtil.d(TAGLOG.TAG_FRAGMENT, "fragment-->" + this.getClass().getSimpleName() + "--onCreate");

		mFragmentManager = getFragmentManager();

		getModuleName();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = null;
		try
		{
			view = inflater.inflate(getLayoutId(), container, false);

			initMemberView(view);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		layoutView = view;

		onAfterOnCreate();

		return view;
	}

	/**
	 * 根据当前View名映射布局文件名
	 */
	public String getModuleName()
	{
		String moduleName = this.moduleName;
		if ( moduleName == null || moduleName.equalsIgnoreCase("") )
		{
			// TODO 这里指定了View类名和对应的layout的映射规则
			moduleName = "fragment_" + getClass().getSimpleName().substring(0, getClass().getSimpleName().length() - 8);

			this.moduleName = moduleName.toLowerCase();

		}
		return moduleName;
	}

	// /**
	// * 实例化了布局layout和activity的view成员
	// */
	// private View loadDefautLayout()
	// {
	//
	// }
	private int getLayoutId() throws IllegalArgumentException, NameNotFoundException, NoSuchNameLayoutException, ClassNotFoundException, IllegalAccessException
	{
		int layoutResID = -100;

		layoutResID = ActivityInjector.getInstance().getLayoutId(this.getClass());

		if ( layoutResID < 0 )
			layoutResID = getApp().getLayoutLoader().getLayoutID(moduleName);

		return layoutResID;
	}

	private void initMemberView(View view)
	{
		// 由于view必须在视图记载之后添加注入
		FragmentInjector.getInstance().injectView(BaseFragment.this, view);
		FragmentInjector.getInstance().inject(BaseFragment.this);
		onAfterInitView();
	}

	protected void onAfterOnCreate()
	{
	}

	/**
	 * 所有view都实例化完成，可以进行事件绑定
	 */
	protected void onAfterInitView()
	{
	}

	public BaseApp getApp()
	{
		return BaseApp.getApp();
	}

	public AbsBaseActivity getAbsActivity()
	{
		Activity a = mParentActivity == null ? getActivity() : mParentActivity;
		return ((AbsBaseActivity) a);
	}

	/**
	 * @Description: 获得activity的view
	 * @author: ethanchiu@126.com
	 * @date: 2013-8-5
	 * @param viewId
	 * @return
	 */
	public View getActivityView(int viewId)
	{
		Activity ac = mParentActivity == null ? getActivity() : mParentActivity;
		return ac.findViewById(viewId);
	}

	/**
	 * @Description: 获得 FragmentManager
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @return
	 */
	public FragmentManager getFragmentMgr()
	{
		return mFragmentManager == null ? getFragmentManager() : mFragmentManager;
	}

	
	@SuppressLint("CommitTransaction")
	/**
	 * @Description: 获得FragmentTransaction
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @return
	 */
	public FragmentTransaction getTransaction()
	{
		return getFragmentMgr().beginTransaction();
	}

	/**
	 * @Description: 显示进度条对话框
	 * @author: ethanchiu@126.com
	 * @date: Sep 9, 2013
	 * @param msg
	 */
	public void showProgress(String msg)
	{
		if ( mProgressDialog == null )
		{
			ProgressDialog progressDialog = new ProgressDialog(getAbsActivity());
			// 【STYLE_SPINNER，STYLE_HORIZONTAL】
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.setMessage(msg);

			mProgressDialog = progressDialog;
		}
		if ( !mProgressDialog.isShowing() )
		{
			mProgressDialog.show();
		}
	}

	public void showProgress()
	{
		showProgress("加载中···");
	}

	/**
	 * @Description: 隐藏进度条对话框			
	 * @author: ethanchiu@126.com
	 * @date: Sep 9, 2013
	 */
	public void dismissDialog()
	{
		if ( mProgressDialog != null && mProgressDialog.isShowing() )
		{
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		LogUtil.d(TAGLOG.TAG_FRAGMENT, "fragment-->" + this.getClass().getSimpleName() + "--onResume");
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		LogUtil.d(TAGLOG.TAG_FRAGMENT, "fragment-->" + this.getClass().getSimpleName() + "--onDestroy");
	}
	
	
}
