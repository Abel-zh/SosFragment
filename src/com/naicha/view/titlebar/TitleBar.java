package com.naicha.view.titlebar;

import simple.framework.ioc.annotation.view.InjectContentView;
import simple.framework.ioc.annotation.view.InjectView;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abel.naicha.R;
import com.naicha.common.TitleConfig;
import com.naicha.common.TitleConfig.PAGE_TYPE;
import common.base.AbsCustomView;

/**
 * @Description: TitleBar
 * @author: ethan.qiu@sosino.com
 * @date: 2013-7-31
 */
@InjectContentView(id = R.layout.view_titlebar)
public class TitleBar extends AbsCustomView {

	@InjectView(id = R.id.container)
	private ViewGroup container;
	@InjectView(id = R.id.leftImage1)
	private ImageView leftImage1;
	@InjectView(id = R.id.leftImage2)
	private ImageView leftImage2;
	@InjectView(id = R.id.rightImage1)
	private ImageView rightImage1;
	@InjectView(id = R.id.rightImage2)
	private ImageView rightImage2;
	@InjectView(id = R.id.titleTv)
	private TextView titleTv;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TitleBar(Context context) {
		super(context);
	}

	/**
	 * @Title: updateTitle
	 * @Description: 根据pageType更新标题
	 * @param pageType
	 * @author Ethan
	 * @returnType void
	 */
	public TitleBar updateTitle(PAGE_TYPE pageType) {
		String appName = getResources().getString(R.string.app_name);

		setTitle(appName);

		TitleConfig.toTitle(pageType, this);

		return this;
	}

	/**
	 * 设置左1边按钮的事件
	 * 
	 * @param listener
	 */
	public void setLeft1Listener(OnClickListener listener) {
		if (listener != null) {
			leftImage1.setVisibility(View.VISIBLE);
			leftImage1.setOnClickListener(listener);
		} else {
			leftImage1.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置左2边按钮的事件
	 * 
	 * @param listener
	 */
	public void setLeft2Listener(OnClickListener listener) {
		if (listener != null) {
			leftImage2.setVisibility(View.VISIBLE);
			leftImage2.setOnClickListener(listener);
		} else {
			leftImage2.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 设置右1边按钮的事件
	 * 
	 * @param listener
	 */
	public void setRight1Listener(OnClickListener listener) {
		if (listener != null) {
			rightImage1.setVisibility(View.VISIBLE);
			rightImage1.setOnClickListener(listener);
		} else {
			rightImage1.setVisibility(View.INVISIBLE);
		}
	}

	public void setRight1Enabled(Boolean is_Enabled) {
		rightImage1.setEnabled(is_Enabled);
	}

	/**
	 * 设置右2边按钮的事件
	 * 
	 * @param listener
	 */
	public void setRight2Listener(OnClickListener listener) {
		if (listener != null) {
			rightImage2.setVisibility(View.VISIBLE);
			rightImage2.setOnClickListener(listener);
		} else {
			rightImage2.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * @Description: 设置左1图
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setLeft1Image(int resId) {
		leftImage1.setVisibility(View.VISIBLE);
		leftImage1.setImageResource(resId);
	}

	/**
	 * @Description: 设置左2图
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setLeft2Image(int resId) {
		leftImage2.setVisibility(View.VISIBLE);
		leftImage2.setImageResource(resId);
	}

	/**
	 * @Description: 设置右1图
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setRight1Image(int resId) {
		rightImage1.setVisibility(View.VISIBLE);
		rightImage1.setImageResource(resId);
	}

	/**
	 * @Description: 设置右2图
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setRight2Image(int resId) {
		rightImage2.setVisibility(View.VISIBLE);
		rightImage2.setImageResource(resId);
	}

	/**
	 * 设置左边按钮的事件
	 * 
	 * @param listener
	 */
	public void setTitleListener(OnClickListener listener) {
		titleTv.setOnClickListener(listener);
	}

	/**
	 * @Title: setTitleTextSize
	 * @Description: 设置标题的字体大小
	 * @param fontSize
	 * @author Ethan
	 * @returnType void
	 */
	public void setTitleTextSize(int fontSize) {
		titleTv.setTextSize(fontSize);
	}

	/**
	 * @Title: setTitleTextColor
	 * @Description: 设置标题的字体颜色
	 * @param color
	 * @author byron
	 * @returnType void
	 */
	public void setTitleTextColor(int color) {
		titleTv.setTextColor(color);
	}

	/**
	 * @Title: setTitle
	 * @Description: 动态的设置标题
	 * @param title
	 * @author Ethan
	 * @returnType void
	 */
	public void setTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			titleTv.setText("");
		} else {
			titleTv.setText(title);
		}
	}

	/**
	 * @Description: 设置标题
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setTitle(int resId) {
		titleTv.setText(resId);
	}

	/**
	 * @Description: 设置背景颜色
	 * @author: ethanchiu@126.com
	 * @date: May 26, 2014
	 * @param resId
	 */
	public void setBackgroundColor(int resId) {
		container.setBackgroundColor(resId);
	}

}
