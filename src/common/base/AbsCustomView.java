package common.base;

import simple.framework.ioc.annotation.impl.ActivityInjector;
import simple.framework.ioc.annotation.impl.ViewInjector;
import simple.framework.mvc.BaseApp;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import common.exception.NoSuchNameLayoutException;

/**
 * @ClassName: AbsCustomView
 * @Description: 抽象自定义控件基类
 * @author Ethan
 * @date 2013-4-12 下午5:30:11
 */
public abstract class AbsCustomView extends LinearLayout{

	/** 模块的名字 */
	private String moduleName = "";

	protected Context mContext;
	private AttributeSet attrs;

	protected LayoutInflater mInflater;

	protected Resources mResources;

	public AbsCustomView(Context context) {
		super(context);
		this.mContext = context;
		this.mResources = getResources();
		inject();
	}

	public AbsCustomView(Context context, AttributeSet attrs) {

		super(context, attrs);
		
		this.mContext = context;
		this.attrs = attrs;
		this.mResources = getResources();
		inject();
	}
	
	private void inject()
	{
		
		if(!isNeedInject())return;
		
		this.mInflater = LayoutInflater.from(mContext);

		getModuleName();
		loadDefautLayout();
		onAfterOnCreate(attrs);
	}
	
	protected boolean isNeedInject(){
		return true;
	}
	
	
	protected void onAfterOnCreate(AttributeSet attrs){
		
	}

	/**
	 * 实例化了布局layout和activity的view成员
	 */
	private void loadDefautLayout()
	{
		try
		{
			setContentView(getLayoutId());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private int getLayoutId() throws IllegalArgumentException, NameNotFoundException, NoSuchNameLayoutException, ClassNotFoundException, IllegalAccessException{
		int layoutResID = -100;

		layoutResID = ActivityInjector.getInstance().getLayoutId(this.getClass());

		if(layoutResID < 0)
			layoutResID = getApp().getLayoutLoader().getLayoutID(moduleName);


		return layoutResID;
	}

	protected void setContentView(int resLayoutId) {
		mInflater.inflate(resLayoutId, this);
		initMemberView();
	}

	private void initMemberView(){
		// 由于view必须在视图记载之后添加注入
		ViewInjector.getInstance().injectView(this);
		onAfterInitView();
	}

	/**
	 * TODO 生命周期调用有问题： onAfterInitView在成员实例化之前调用，导致时间注册的时候监听者都为空
	 * 
	 * 所有view都实例化完成，可以进行事件绑定
	 */
	protected void onAfterInitView(){
	}

	/**
	 * 根据当前View名映射布局文件名
	 */
	public String getModuleName()
	{
		String moduleName = this.moduleName;
		if (moduleName == null || moduleName.equalsIgnoreCase(""))
		{
			//TODO 这里指定了View类名和对应的layout的映射规则
			moduleName = "view_" + getClass().getSimpleName().substring(0,
					getClass().getSimpleName().length() - 4);

			this.moduleName = moduleName.toLowerCase();

		}
		return moduleName;
	}


	public BaseApp getApp()
	{
		return BaseApp.getApp();
	}
}
