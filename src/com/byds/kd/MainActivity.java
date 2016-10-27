package com.byds.kd;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.byds.kd.adapter.CustomPagerAdapter;
import com.byds.kd.views.SelectedLoadViewPager;
import com.byds.kd.views.TabViewPager;

public class MainActivity extends ActionBarActivity {
	View locationView;
	View historyView;
	View aboutView;
	
	EditText mNumberText;
	Spinner mCompanyList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		initData();
	}
	
	private void initViews() {
		mNumberText = (EditText)findViewById(R.id.number);
		mCompanyList = (Spinner)findViewById(R.id.company);
		LayoutInflater mLi= LayoutInflater.from(this);
		locationView =mLi.inflate(R.layout.location, null);
		historyView =mLi.inflate(R.layout.history, null);
		aboutView = mLi.inflate(R.layout.about, null);
		ArrayList<View> views = new ArrayList<View>();
		views.add(locationView);
		views.add(historyView);
		views.add(aboutView);
		ArrayList<String>titles = new ArrayList<String>();
		titles.add(getResources().getString(R.string.kd_order));
		titles.add(getResources().getString(R.string.kd_history));
		titles.add(getResources().getString(R.string.about));
		
		//useTabView(views, titles);
		useSelectLoadView(views, titles);
	}
	private void initData() {
		mNumberText.setText("");
		mCompanyList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.company)));
		//mCompanyList.setOnItemClickListener(new CompanyItemClickListener());
	}
	
	private void useSelectLoadView(ArrayList<View> views,ArrayList<String>titles) {
		SelectedLoadViewPager viewPager = (SelectedLoadViewPager)findViewById(R.id.myviewpager);
		viewPager.setVisibility(View.VISIBLE);
		viewPager.setAdapter(new CustomPagerAdapter(views, titles));
	}
	private void useTabView(ArrayList<View> views,ArrayList<String>titles) {
		TabViewPager viewPager = (TabViewPager)findViewById(R.id.tabviewpager);
		viewPager.setVisibility(View.VISIBLE);
		String []tabs =new String[titles.size()];
		titles.toArray(tabs);
		viewPager.initTabs(tabs);
		viewPager.setAdapter(new CustomPagerAdapter(views));
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
	
	private class CompanyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
