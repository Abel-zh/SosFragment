package common.util;

import simple.framework.mvc.BaseApp;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @Description: (适配不同系统版本) 在低版本中可以线程同步的Toast，高版本中不适用，不要用
 * @author: Ethan
 * @date: 2013-4-16
 */
public class ToastUtil {  

	private static Handler handler = new Handler(Looper.getMainLooper());  

	private static Toast toast = null;  

	private static Object synObj = new Object();  

	public static Context getContext(){
		return BaseApp.getApp();
	}

	public static void show(final String msg) {  
		show(msg, false);  
	}  

	public static void show(final int resId) {  
		show(resId, false);  
	}  
	
	public static void show(final String msg, final boolean isSync){
		show(msg, Toast.LENGTH_SHORT, isSync);
	}
	
	public static void show(final int resId, final boolean isSync){
		show(resId, Toast.LENGTH_SHORT, isSync);
	}
	
	
	public static void show(final String msg, final int len, final boolean isSync) {  

		if(isSync){
			new Thread(new Runnable() {  
				public void run() {  
					handler.post(new Runnable() {  
						@Override  
						public void run() {  
							synchronized (synObj) {  
								if (toast != null) {  
									toast.cancel();  
									toast.setText(msg);  
									toast.setDuration(len);  
								} else {  
									toast = Toast.makeText(getContext(), msg, len);  
								}  
								toast.show();  
							}  
						}  
					});  
				}  
			}).start();  
		}else{
			Toast.makeText(getContext(), msg, len).show();
//			if (toast != null) {  
//				toast.setText(msg);  
//				toast.setDuration(len);  
//			} else {  
//				toast = Toast.makeText(getContext(), msg, len);  
//			}   
//			toast.show();
		}
	}  


	public static void show(final int resId, final int len, final boolean isSync) {

		String msg = (String) getContext().getText(resId);
		show(msg, len, isSync);
	}  

}  