package simple.framework.ioc.annotation.impl;

import simple.framework.ioc.annotation.setting.BaseLayout;
import simple.framework.ioc.annotation.view.InjectContentView;

/**
 * @Description: 注射器基类
 * @author: ethanchiu@126.com
 * @date: 2013-8-1
 */
public abstract class AbsInjector {

	public int getLayoutId(Class<?> clazz){
		
		InjectContentView contentView = clazz.getAnnotation(InjectContentView.class);
		if(contentView == null){
			return -1;
		}else{
			return  contentView.id();
		}
	}
	
	public boolean isNeedBaseLayout(Class<?> clazz){
		BaseLayout anno = clazz.getAnnotation(BaseLayout.class);
		
		if(anno == null){
			return false;
		}else{
			return anno.isNeedBase();
		}
	}
	
	public int getBackGruandId(Class<?> clazz){
		BaseLayout anno = clazz.getAnnotation(BaseLayout.class);
		return anno.rootBackGround();
	}
}
