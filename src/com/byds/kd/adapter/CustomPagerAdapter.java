package com.byds.kd.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class CustomPagerAdapter extends PagerAdapter{
	ArrayList<View> views = new ArrayList<View>();
	ArrayList<String> titles = new ArrayList<String>();
	public CustomPagerAdapter(ArrayList<View> views) {
		if(views!=null)this.views = views;
	}
	public CustomPagerAdapter(ArrayList<View> views,ArrayList<String> titles) {
		if(views!=null)this.views = views;
		if(titles!=null)this.titles = titles;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if(titles==null||titles.size()==0) {
			return super.getPageTitle(position);
		} else {
			return titles.get(position);
		}
	}
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		// TODO Auto-generated method stub
		return view ==obj;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(views.get(position));
		return views.get(position);
	}
	
	
}
