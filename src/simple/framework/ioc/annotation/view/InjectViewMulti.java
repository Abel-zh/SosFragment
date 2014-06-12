package simple.framework.ioc.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectViewMulti
{
	/** 多个View的ID */
	public int[] ids() default {1,2};//TODO 返回值有问题
}
