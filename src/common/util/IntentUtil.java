package common.util;

import simple.compoment.log.LogUtil;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * @Description: IntentUtil
 * @author: ethanchiu@126.com
 * @date: 2013-8-8
 */
public class IntentUtil {

	private static final String TAG = IntentUtil.class.getSimpleName();

	/**
	 * @Title: openURL
	 * @Description: 在网页中打开URL地址 【浏览器可以实现自动下载】
	 * @param context
	 * @param url
	 * @returnType void
	 */
	public static void openURL(Context context,String url){
		Intent intent= new Intent();        
		intent.setAction("android.intent.action.VIEW");    
		Uri data = Uri.parse(url);   
		intent.setData(data);  
		context.startActivity(intent);
	}


	/**
	 * @Title: launchOtherApp
	 * @Description: 启动外部应用程序
	 * @param @param context
	 * @param @param packageName
	 * @return void
	 */
	public static boolean launchOtherApp(Context context,String packageName){
		try {
			PackageManager pm = context.getPackageManager();
			Intent intent = pm.getLaunchIntentForPackage(packageName);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			LogUtil.d(TAG, e.toString());
			LogUtil.d(TAG, "Not Found the app.....");
			return false;
		}
	}


	/**
	 * @Title: callPhone
	 * @Description: 拨打电话
	 * @param context
	 * @param phoneNo
	 * @returnType void
	 */
	public static void callPhone(Context context,String phoneNo){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNo));
		context.startActivity(intent);
	}

	/**
	 * @Title: goToSystemSetting
	 * @Description: 跳转到系统设置界面
	 * @param context
	 * @returnType void
	 */
	public static void  goToSystemSetting(Context context){
		Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		context.startActivity(intent);
	}


	/**
	 * @Title: installApk
	 * @Description: install Apk 
	 * @param context
	 * @param apkFilePath
	 * @author scofieldandroid@gmail.com
	 * @returnType void
	 */
	public static void installApk(Context context , String apkFilePath){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkFilePath),"application/vnd.android.package-archive");
		context.startActivity(intent);
	}


}
