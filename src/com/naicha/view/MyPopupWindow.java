package com.naicha.view;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.abel.naicha.R;
import com.naicha.util.date.DateUtils;
import common.util.ToastUtil;

/***
 * 
 * @ClassName: MyPopupWindow
 * @Description: "我的"头像更换pop
 * @author Abel
 * @date 2014-6-23 上午11:29:06
 * 
 */
@SuppressLint("ViewConstructor")
public class MyPopupWindow extends PopupWindow implements OnClickListener {

	private Activity activity;
	private String resultFilepath;

	public static final int REQUEST_TAKE_PHOTO = 0;
	public static final int REQUEST_GALLERY_IMAGE = 1;
	public static final int REQUEST_MODIFY_FINISH = 2;

	public MyPopupWindow(Activity ctx) {
		super(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflater.inflate(R.layout.img_uploading_pop, null);
		TextView b1 = (TextView) contentView.findViewById(R.id.uploading_b1);
		b1.setOnClickListener(this);
		TextView b2 = (TextView) contentView.findViewById(R.id.uploading_b2);
		b2.setOnClickListener(this);
		TextView b3 = (TextView) contentView.findViewById(R.id.uploading_b3);
		b3.setOnClickListener(this);

		setContentView(contentView);
		setFocusable(true); // 设置PopupWindow可获得焦点
		setTouchable(true); // 设置PopupWindow可触摸
		setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
		this.activity = ctx;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.uploading_b1:
			doTakePhoto();
			break;
		case R.id.uploading_b2:
			doPickPhotoFromGallery();
			break;
		case R.id.uploading_b3:
			dismissPopWindow();
			break;
		}
	}

	/**
	 * 
	 * @Method: showPopWindow
	 * @Description: 显示pop框在view底部且横排居中
	 * @param view
	 * @return void
	 */
	public void showPopWindow(View view) {
		showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	/**
	 * 
	 * @Method: dimissPopWindow
	 * @Description: 隐藏pop框
	 * @return void
	 */
	public void dismissPopWindow() {
		if (isShowing()) {
			dismiss();
		}
	}

	/**
	 * 
	 * @Method: doPickPhotoFromGallery
	 * @Description: 跳转相册
	 * @return void
	 */
	private void doPickPhotoFromGallery() {
		try {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY_IMAGE);
		} catch (ActivityNotFoundException e) {
			ToastUtil.show("图片集为空");
		}
	}

	/**
	 * 
	 * @Method: doTakePhoto
	 * @Description: 开启摄像头
	 * @return void
	 */
	private void doTakePhoto() {
		try {
			resultFilepath = Environment.getExternalStorageDirectory() + "/com.sosino.weshop/"
					+ DateUtils.getCurrentDate() + ".jpg";
			File mCurrentPhotoFile = new File(resultFilepath);// 给新照的照片文件命名
			if (!mCurrentPhotoFile.getParentFile().exists())
				mCurrentPhotoFile.getParentFile().mkdirs();
			Intent intent = getTakePickIntent(mCurrentPhotoFile);
			activity.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
		} catch (ActivityNotFoundException e) {
			ToastUtil.show("摄像头启动失败");
		}
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	/**
	 * 
	 * @Method: getResultFilepath
	 * @Description: 获取图片路径
	 * @return
	 * @return String
	 */
	public String getResultFilepath() {
		return resultFilepath;
	}

}
