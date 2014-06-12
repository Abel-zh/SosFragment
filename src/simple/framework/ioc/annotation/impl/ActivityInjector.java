package simple.framework.ioc.annotation.impl;

import java.lang.reflect.Field;

import simple.framework.ioc.annotation.commen.Inject;
import simple.framework.ioc.annotation.view.InjectResource;
import simple.framework.ioc.annotation.view.InjectView;
import simple.framework.ioc.annotation.view.InjectViewMulti;
import android.app.Activity;
import android.content.res.Resources;

/**
 * @Description: Activity注入器
 * @author: ethanchiu@126.com
 * @date: 2013-7-31
 */
public class ActivityInjector extends AbsInjector{
	
	private static ActivityInjector instance;

	private ActivityInjector()
	{
	}

	public static ActivityInjector getInstance()
	{
		if (instance == null)
		{
			instance = new ActivityInjector();
		}
		return instance;
	}
	

	/**
	 * 实例化InjectView，InjectResource，Inject注解的成员
	 * @param activity
	 */
	public void inJectAll(Activity activity)
	{
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				if (field.isAnnotationPresent(InjectView.class))
				{
					injectView(activity, field);
				} else if (field.isAnnotationPresent(InjectResource.class))
				{
					injectResource(activity, field);
				} else if (field.isAnnotationPresent(Inject.class))
				{
					inject(activity, field);
				}
			}
		}
	}

	/**
	 * 实例化Inject注解的成员
	 * @param activity
	 * @param field
	 */
	private void inject(Activity activity, Field field)
	{
		try
		{
			field.setAccessible(true);
			Class<?> cls = field.getType();
			field.set(activity, cls.newInstance());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 实例化InjectView注解的成员，activity中的view
	 * @param activity
	 * @param field
	 */
	private void injectView(Activity activity, Field field)
	{
		if (field.isAnnotationPresent(InjectView.class))
		{

//			LogUtil.d(this, activity.getClass().getSimpleName() + 
//					".injectView() field-->" + field.getName()); 

			InjectView viewInject = field.getAnnotation(InjectView.class);//获得该成员的annotation
			int viewId = viewInject.id();
			try
			{
				field.setAccessible(true);
				field.set(activity, activity.findViewById(viewId));//实例化该view
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void injectViewMulti(Activity activity, Field field, int position)
	{
		if (field.isAnnotationPresent(InjectViewMulti.class))
		{
			
//			LogUtil.d(this, activity.getClass().getSimpleName() + 
//					".injectView() field-->" + field.getName()); 
			
			InjectViewMulti viewInject = field.getAnnotation(InjectViewMulti.class);//获得该成员的annotation
			int[] viewIds = viewInject.ids();
			
			try
			{
				field.setAccessible(true);
				field.set(activity, activity.findViewById(viewIds[position]));//实例化该view
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void injectResource(Activity activity, Field field)
	{
		if (field.isAnnotationPresent(InjectResource.class))
		{
//			LogUtil.d(this, activity.getClass().getSimpleName() + 
//					".injectResource() field-->" + field.getName()); 

			InjectResource resourceJect = field
					.getAnnotation(InjectResource.class);
			int resourceID = resourceJect.id();
			try
			{
				field.setAccessible(true);
				Resources resources = activity.getResources();
				String type = resources.getResourceTypeName(resourceID);
				if (type.equalsIgnoreCase("string"))
				{
					field.set(activity,
							activity.getResources().getString(resourceID));
				} else if (type.equalsIgnoreCase("drawable"))
				{
					field.set(activity,
							activity.getResources().getDrawable(resourceID));
				} else if (type.equalsIgnoreCase("layout"))
				{
					field.set(activity,
							activity.getResources().getLayout(resourceID));
				} else if (type.equalsIgnoreCase("array"))
				{
					if (field.getType().equals(int[].class))
					{
						field.set(activity, activity.getResources()
								.getIntArray(resourceID));
					} else if (field.getType().equals(String[].class))
					{
						field.set(activity, activity.getResources()
								.getStringArray(resourceID));
					} else
					{
						field.set(activity, activity.getResources()
								.getStringArray(resourceID));
					}

				} else if (type.equalsIgnoreCase("color"))
				{
					if (field.getType().equals(Integer.TYPE))
					{
						field.set(activity,
								activity.getResources().getColor(resourceID));
					} else
					{
						field.set(activity, activity.getResources()
								.getColorStateList(resourceID));
					}

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 * 实例化Inject注解的成员
	 * @param activity
	 */
	public void inject(Activity activity)
	{
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				if (field.isAnnotationPresent(Inject.class))
				{
//					LogUtil.d(this, activity.getClass().getSimpleName() + 
//							".inject() field-->" + field.getName()); 
					inject(activity, field);
				}
			}
		}
	}

	/**
	 * 实例化InjectView注解的成员，activity中的view
	 * 
	 * @param activity
	 */
	public void injectView(Activity activity)
	{
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				injectView(activity, field);
			}
		}
	}
	
	public void injectViewMulti(Activity activity)
	{
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			int len = fields.length;
			for(int i = 0; i < len; i++){
				injectViewMulti(activity, fields[i], i);
			}
		}
	}

	public void injectResource(Activity activity)
	{
		Field[] fields = activity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				injectResource(activity, field);
			}
		}
	}


	
}
