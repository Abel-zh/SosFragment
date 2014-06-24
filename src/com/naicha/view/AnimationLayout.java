package com.naicha.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.abel.naicha.R;

public class AnimationLayout extends RelativeLayout implements OnTouchListener {

	private float startX;
	private float endX;
	private float startY;
	private float endY;
	private float lastY;
	private float lastX;
	private float width;
	private int dx;
	private int lastDx;
	private int position;

	private Scroller scroller;
	private Rect rect = new Rect();
	private SlideAnimation animation;
	private SlidingOvershootInterpolator interpolator;

	/** 是否在滑动 */
	private boolean mscollable = true;
	/** 接触屏幕状态:TOUCH_STATE_REST 重置、TOUCH_STATE_SCROLLING 滑动 */
	private int mTouchState = TOUCH_STATE_REST;

	private static final int SLIDE_LEFT = 1;
	private static final int SLIDE_RIGHT = 2;
	private static final int TOUCH_STATE_REST = 0; // 重置
	private static final int TOUCH_STATE_SCROLLING = 1; // 滑动中
	private static final float NANOTIME_DIV = 1000000000.0f;
	private static final float SMOOTHING_SPEED = 0.75f;
	private static final float SMOOTHING_CONSTANT = (float) (0.016 / Math.log(SMOOTHING_SPEED));

	public AnimationLayout(Context context) {
		super(context);
		init();
	}

	public AnimationLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AnimationLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setOnTouchListener(this);
		interpolator = new SlidingOvershootInterpolator();
		scroller = new Scroller(getContext(), interpolator);
		width = getResources().getDimension(R.dimen.animation_slide_width);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (!mscollable && mTouchState != TOUCH_STATE_SCROLLING)
			return false;

		lastX = event.getRawX();
		lastY = event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// 如果已经停止平滑，就滑动到最后的位置
			if (mTouchState != TOUCH_STATE_SCROLLING) {
				scrollTo(lastDx, 0);
				postInvalidate();
				return true;
			}

			if (Math.abs(dx - lastDx) * 3 < width) {
				int time = (int) (SMOOTHING_CONSTANT * Math.abs(lastDx - dx) / SMOOTHING_SPEED);
				scroller.startScroll(dx, 0, lastDx - dx, 0, time);
				dx = lastDx;
				postInvalidate();
			} else {

				int _final = (dx - lastDx) > 0 ? (int) width : 0;
				ViewGroup parent = (ViewGroup) getParent();
				for (int i = 0; i < parent.getChildCount(); i++) {
					((AnimationLayout) parent.getChildAt(i)).reset();
				}
				int time = (int) (SMOOTHING_CONSTANT * Math.abs(_final - dx) / SMOOTHING_SPEED);
				scroller.startScroll(dx, 0, _final - dx, 0, time);
				dx = _final;
				lastDx = dx;

				postInvalidate();
			}
			mTouchState = TOUCH_STATE_REST;

			break;
		case MotionEvent.ACTION_DOWN:
			// 点击下去，记录位置坐标
			endX = startX = event.getRawX();
			endY = startY = event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 移动时，mTouchState改变状态
			mTouchState = TOUCH_STATE_SCROLLING;
			if (Math.abs(lastX - endY) > ViewConfiguration.getTouchSlop()) {
				dx += (int) (endX - lastX);
				dx = (int) Math.max(Math.min(width, dx), 0);
				scrollTo(dx, 0);
				endX = lastX;
				endY = lastY;
				postInvalidate();
			}
			break;
		}
		return false;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) {
			scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
		}
	}

	/**
	 * 
	 * @Title: reset
	 * @Description: 恢复已显示出来的删除按钮为隐藏状态
	 * @author Abel
	 */
	public void reset() {
		if (mTouchState == TOUCH_STATE_SCROLLING)
			return;
		int time = (int) (SMOOTHING_CONSTANT * Math.abs(dx) / SMOOTHING_SPEED);
		scroller.startScroll(dx, 0, 0 - dx, 0, time);
		dx = 0;
		lastDx = 0;
		postInvalidate();
	}

	public void resetState() {
		if (animation != null)
			animation.cancel();
		postInvalidate();
	}

	@Override
	public void setOnClickListener(final OnClickListener l) {
		super.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (l != null)
					l.onClick(v);
				ViewGroup parent = (ViewGroup) getParent();
				for (int i = 0; i < parent.getChildCount(); i++) {
					((AnimationLayout) parent.getChildAt(i)).reset();
				}
			}
		});
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public boolean isScollable() {
		return mscollable;
	}

	public void setScollable(boolean mscollable) {
		this.mscollable = mscollable;
	}

	public void startSlideAnimation(int state) {
		startSlideAnimation(state, getResources().getInteger(android.R.integer.config_longAnimTime));
	}

	public void startSlideAnimation(int state, long durationMillis) {
		animation = new SlideAnimation(state);
		animation.setDuration(durationMillis);
		startAnimation(animation);
	}

	class SlideAnimation extends Animation {

		int state;

		public SlideAnimation(int state) {
			this.state = state;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);

			switch (state) {
			case SLIDE_LEFT:
				layout((int) (rect.left - interpolatedTime * width), rect.top, rect.right, rect.bottom);
				break;
			case SLIDE_RIGHT:
				layout((int) (rect.left + interpolatedTime * width), rect.top, rect.right, rect.bottom);
				break;
			}

		}

	}

	/***
	 * 
	 * @ClassName: SlidingOvershootInterpolator
	 * @Description: 动画插入器
	 * @author Abel
	 * @date 2014-6-24 下午3:45:25
	 * 
	 */
	private static class SlidingOvershootInterpolator implements Interpolator {
		private static final float DEFAULT_TENSION = 1.3f;
		private float mTension;

		public SlidingOvershootInterpolator() {
			mTension = DEFAULT_TENSION;
		}

		public void setDistance(int distance) {
			mTension = distance > 0 ? DEFAULT_TENSION / distance : DEFAULT_TENSION;
		}

		public void disableSettle() {
			mTension = 0.f;
		}

		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * ((mTension + 1) * t + mTension) + 1.0f;
		}
	}

}
