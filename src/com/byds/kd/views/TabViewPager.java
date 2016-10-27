package com.byds.kd.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.byds.kd.R;

public class TabViewPager extends LinearLayout {
	public final static String TAG="TabViewPager";
	private OnPageSelectedFlushListener onPageSelectedFlushListener=null;
	private Context mContext;
	private LinearLayout mTabHost;
	private ImageView mUnderLine;
	private ViewPager mViewPager;
	
	private int mTabWidth;

	public TabViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		inflate(context, R.layout.tab_view_pager, this);
		initViews();
		
	}
	private void initViews() {
		mTabHost =(LinearLayout)findViewById(R.id.tab_host);
		mUnderLine =(ImageView)findViewById(R.id.tab_underline);
		mViewPager =(ViewPager) findViewById(R.id.viewpager);
	}
	public void initTabs(String[] tabTitles) {
		initTabs(tabTitles, -1);
	}
	public void initTabs(String[] tabTitles,int parentWidth) {
		LinearLayout.LayoutParams tabHostLayoutParams;
		TextView tab;
		if(parentWidth <=0) {
			WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
			parentWidth=wm.getDefaultDisplay().getWidth();
		}
		mTabWidth = parentWidth/tabTitles.length;
		if(tabTitles.length>0) {
			tabHostLayoutParams = new LayoutParams(mTabWidth,LayoutParams.WRAP_CONTENT);
		} else return;
		
		for(int i=0;i<tabTitles.length;i++) {
			tab = new TextView(mContext);
			tab.setText(tabTitles[i]);
			tab.setTextSize(18);
			
			tabHostLayoutParams.weight=1;
			tabHostLayoutParams.gravity =Gravity.CENTER_VERTICAL;
			
			tab.setLayoutParams(tabHostLayoutParams);
			tab.setGravity(Gravity.CENTER);
			tab.setOnClickListener(new TabOnClickListener(i));
			mTabHost.addView(tab);
		}
		FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(mTabWidth,FrameLayout.LayoutParams.WRAP_CONTENT);
		frameLayoutParams.gravity = Gravity.BOTTOM;
		mUnderLine.setLayoutParams(frameLayoutParams);
		mUnderLine.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.underline));
		
	}
	public void setAdapter(PagerAdapter pagerAdapter) {
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			private int currentPosition=-1;
			private int nextPosition=-1;
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				nextPosition = position;
				moveUnderline(currentPosition, nextPosition);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				//currentPosition = position;
				if(onPageSelectedFlushListener != null){
			            if(positionOffset == 0 && positionOffsetPixels == 0){
			                if(currentPosition != position){
			                	currentPosition = position;
			                    onPageSelectedFlushListener.onPageSelected(position);
			                }
			           }
			     }
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private class TabOnClickListener implements OnClickListener {
		private int viewPosition =-1;
		public TabOnClickListener(int position) {
			viewPosition =position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			setCurrentItem(viewPosition);
		}
		
	}
	public void setCurrentItem(int position) {
		int currentPosition= mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(position);
		int nextPosition = mViewPager.getCurrentItem();
		moveUnderline(currentPosition, nextPosition);
	}
	private void moveUnderline(int currentPosition,int nextPosition) {
		mUnderLine.startAnimation(new UnderlineTraslateAnimation(currentPosition*mTabWidth, 
				nextPosition*mTabWidth, 0, 0));
	}
	private class UnderlineTraslateAnimation extends TranslateAnimation {

		public UnderlineTraslateAnimation(float fromXDelta, float toXDelta,
				float fromYDelta, float toYDelta) {
			super(fromXDelta, toXDelta, fromYDelta, toYDelta);
			// TODO Auto-generated constructor stub
			setFillAfter(true);
		}
		
	}
	public void setOnPageSelectedFlushListener(OnPageSelectedFlushListener onPageSelectedFlushListener){
	        this.onPageSelectedFlushListener = onPageSelectedFlushListener;
    }
	public interface OnPageSelectedFlushListener{
        void onPageSelected(int position);
    }
}
