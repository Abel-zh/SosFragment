package common.base;

import simple.framework.mvc.BaseActivity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.abel.naicha.R;

public abstract class AbsBaseActivity extends BaseActivity {
	// =========doActivity end========

	protected ProgressDialog mProgressDialog;

	public void showProgress(String msg) {
		if (mProgressDialog == null) {
			ProgressDialog progressDialog = new ProgressDialog(this);
			// 【STYLE_SPINNER，STYLE_HORIZONTAL】
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(true);
			progressDialog.setMessage(msg);
			progressDialog.setCanceledOnTouchOutside(false);

			mProgressDialog = progressDialog;
		}
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	/**
	 * @Description: 显示Progress
	 * @author: ethanchiu@126.com
	 * @date: Dec 20, 2013
	 */
	public void showProgress() {
		showProgress("加载中···");
	}

	/**
	 * @Description: 影藏Progress
	 * @author: ethanchiu@126.com
	 * @date: Dec 20, 2013
	 */
	public void dismissProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	// <<<<<<<<<<<<<< fragment begin<<<<<<<<<<<<<<

	/**
	 * @Description: 获得FragmentManager
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @return
	 */
	protected FragmentManager getFragmentMgr() {
		return getSupportFragmentManager();
	}

	/**
	 * @Description: 获得FragmentTransaction
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @return
	 */
	protected FragmentTransaction beginTransaction() {

		return getFragmentMgr().beginTransaction();
	}

	/**
	 * @Description: 实例化Fragment
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @param id
	 * @return
	 */
	protected Fragment findFragmentById(int id) {
		return getFragmentMgr().findFragmentById(id);
	}

	/**
	 * @Description: 实例化Fragment
	 * @author: ethanchiu@126.com
	 * @date: Nov 7, 2013
	 * @param tag
	 * @return
	 */
	protected Fragment findFragmentByTag(String tag) {
		return getFragmentMgr().findFragmentByTag(tag);
	}

	// >>>>>>>>>>>>>>> fragment end >>>>>>>>>>>>>>

}
