package simple.framework.ioc.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 注入布局文件
 * @author: ethanchiu@126.com
 * @date: 2013-7-29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectContentView
{
	/** Layout的ID */
	public int id() default -1;
	
}