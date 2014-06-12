package simple.framework.ioc.annotation.impl;

import java.lang.reflect.Field;

import simple.framework.ioc.annotation.commen.Inject;
import simple.framework.ioc.annotation.view.InjectView;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * @Description: Fragment注入器
 * @author: ethanchiu@126.com
 * @date: 2013-7-31
 */
public class FragmentInjector extends AbsInjector{
	
	private static FragmentInjector instance;

	private FragmentInjector()
	{
	}

	public static FragmentInjector getInstance()
	{
		if (instance == null)
		{
			instance = new FragmentInjector();
		}
		return instance;
	}
	
	
	private void injectView(Fragment fragment,View view, Field field)
	{
		if (field.isAnnotationPresent(InjectView.class))
		{

//			LogUtil.d(this, fragment.getClass().getSimpleName() + 
//					".injectView() field-->" + field.getName()); 

			InjectView viewInject = field.getAnnotation(InjectView.class);//获得该成员的annotation
			int viewId = viewInject.id();
			try
			{
				field.setAccessible(true);
				field.set(fragment, view.findViewById(viewId));//实例化该view
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 实例化Inject注解的成员
	 * @param activity
	 * @param field
	 */
	private void inject(Fragment fragment, Field field)
	{
		try
		{
			field.setAccessible(true);
			Class<?> cls = field.getType();
			field.set(fragment, cls.newInstance());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 实例化InjectView注解的成员，view中的view
	 */
	public void injectView(Fragment fragment, View view)
	{
		Field[] fields = fragment.getClass().getDeclaredFields();//获得所有成员
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				injectView(fragment, view, field);
			}
		}
	}
	
	
	/**
	 * 实例化Inject注解的成员
	 * @param activity
	 */
	public void inject(Fragment fragment)
	{
		Field[] fields = fragment.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				if (field.isAnnotationPresent(Inject.class))
				{
//					LogUtil.d(this, fragment.getClass().getSimpleName() + 
//							".inject() field-->" + field.getName()); 
					inject(fragment, field);
				}
			}
		}
	}
}
