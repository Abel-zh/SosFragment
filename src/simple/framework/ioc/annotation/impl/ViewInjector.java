package simple.framework.ioc.annotation.impl;

import java.lang.reflect.Field;

import simple.framework.ioc.annotation.view.InjectView;
import android.view.View;

/**
 * @Description: View注入器
 * @author: ethanchiu@126.com
 * @date: 2013-7-31
 */
public class ViewInjector extends AbsInjector{
	
	private static ViewInjector instance;

	private ViewInjector()
	{
	}

	public static ViewInjector getInstance()
	{
		if (instance == null)
		{
			instance = new ViewInjector();
		}
		return instance;
	}
	
	private void injectView(View view, Field field)
	{
		if (field.isAnnotationPresent(InjectView.class))
		{

//			LogUtil.d(this, view.getClass().getSimpleName() + 
//					".injectView() field-->" + field.getName()); 

			InjectView viewInject = field.getAnnotation(InjectView.class);//获得该成员的annotation
			int viewId = viewInject.id();
			try
			{
				field.setAccessible(true);
				field.set(view, view.findViewById(viewId));//实例化该view
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void injectView(View view)
	{
		Field[] fields = view.getClass().getDeclaredFields();//获得所有成员
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				injectView(view, field);
			}
		}
	}
}
