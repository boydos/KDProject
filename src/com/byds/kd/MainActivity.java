package com.byds.kd;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.byds.kd.views.CustomPagerAdapter;
import com.byds.kd.views.SelectedLoadViewPager;

public class MainActivity extends ActionBarActivity {
	SelectedLoadViewPager viewPager;
	View locationView;
	View historyView;
	View aboutView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		viewPager = (SelectedLoadViewPager)findViewById(R.id.vp_list);
		LayoutInflater mLi= LayoutInflater.from(this);
		locationView =mLi.inflate(R.layout.location, null);
		historyView =mLi.inflate(R.layout.history, null);
		aboutView = mLi.inflate(R.layout.about, null);
		ArrayList<View> views = new ArrayList<View>();
		views.add(locationView);
		views.add(historyView);
		views.add(aboutView);
		
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("订单轨迹");
		titles.add("历史订单");
		titles.add("关于我们");
		viewPager.setAdapter(new CustomPagerAdapter(views, titles));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
