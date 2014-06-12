package simple.framework.ioc.annotation.setting;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 是否使用背景布局
 * @author: ethanchiu@126.com
 * @date: 2013-7-29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseLayout
{
	/** 是否注入布局 */
	public boolean isNeedBase() default false;
	
	public int rootBackGround() default -1;
	
}