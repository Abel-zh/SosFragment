package com.naicha.module;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.abel.naicha.R;
import com.naicha.view.AnimationLayout;
import com.naicha.view.MyPopupWindow;
import common.util.ToastUtil;

public class MainActivity extends Activity {

	private MyPopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		popupWindow = new MyPopupWindow(this);

		// recordsList.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// boolean flag = true;
		// if (scrollState ==
		// AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
		// flag = false;
		// } else {
		// flag = true;
		// }
		// int count = view.getChildCount();
		// for (int i = 0; i < count; i++) {
		// AnimationLayout layout = (AnimationLayout) view.getChildAt(i);
		// layout.setScollable(flag);
		// layout.resetState();
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem, int
		// visibleItemCount, int totalItemCount) {
		//
		// }
		// });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK && resultCode != 0) {
			return;
		}

		switch (requestCode) {
		// 读取系统相册
		case MyPopupWindow.REQUEST_GALLERY_IMAGE:
			if (null == data) {
				ToastUtil.show(R.string.data_error);
				return;
			}
			Uri _uri = data.getData();
			if (_uri != null) {
				Cursor cursor = getContentResolver().query(_uri, null, null, null, null);
				cursor.moveToFirst();
				String path = cursor.getString(1); // 返回图片的地址
				cursor.close();
				File f = new File(path);
				Intent intent = new Intent(this, UserCropImageActivity.class);
				intent.putExtra("path", f.getAbsolutePath());
				startActivityForResult(intent, MyPopupWindow.REQUEST_MODIFY_FINISH);
			}
			break;
		// 照相
		case MyPopupWindow.REQUEST_TAKE_PHOTO:
			if (popupWindow.getResultFilepath() != null && popupWindow.getResultFilepath().length() > 0
					&& new File(popupWindow.getResultFilepath()).exists()) {
				File f = new File(popupWindow.getResultFilepath());
				Intent intent = new Intent(this, UserCropImageActivity.class);
				intent.putExtra("path", f.getAbsolutePath());
				startActivityForResult(intent, MyPopupWindow.REQUEST_MODIFY_FINISH);
				return;
			}

			if (null == data) {
				ToastUtil.show(R.string.data_error);
				return;
			}
			break;
		// 相册、照相图片处理完成
		case MyPopupWindow.REQUEST_MODIFY_FINISH:
			if (data != null) {
				updateImg(data.getStringExtra("path"));
			}
			break;
		}
	}

	private void updateImg(String stringExtra) {

	}

}
