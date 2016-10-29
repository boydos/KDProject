package com.byds.kd;

import java.util.ArrayList;
import java.util.List;

import com.byds.kd.adapter.CustomPagerAdapter;
import com.byds.kd.bean.OrderDetialInfo;
import com.byds.kd.utils.InitConfig;
import com.byds.kd.utils.LocationViewHelper;
import com.byds.kd.utils.LogUtils;
import com.byds.kd.utils.SearchHelper;
import com.byds.kd.views.SelectedLoadViewPager;
import com.byds.kd.views.TabViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private final String TAG="MainActivity";
	private final int SEARCH_KEY=100;
	private final String SEARCH_NUMBER="search_number";
	private final String SEARCH_COMPANY="search_company";
	View locationView;
	View historyView;
	View aboutView;
	SelectedLoadViewPager viewPager;
	EditText mNumberText;
	Spinner mCompanySpinner;
	Button mSearch;
	
	SearchHelper searchHelper;
	LocationViewHelper locationHelper;
	public static int currentPosition=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		searchHelper = new SearchHelper(this);
		initViews();
		initData();
	}
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SEARCH_KEY:
				String result =msg.obj.toString();
				LogUtils.i(TAG, result);
				List<OrderDetialInfo> data = searchHelper.getDetailFromResult(result);
				
				String [] times=new String [data.size()];
				String [] locations =new String [data.size()];
				for(int i=0;i<data.size();i++) {
					times[i]=data.get(i).getDate();
					locations[i]=data.get(i).getLocation();
				}
				
				locationHelper.updateData(mNumberText.getText().toString(), times,locations);
				break;

			default:
				break;
			}
		}
		
	};
	private void initViews() {
		mSearch = (Button)findViewById(R.id.search);
		mNumberText = (EditText)findViewById(R.id.number);
		mCompanySpinner = (Spinner)findViewById(R.id.company);
		
		LayoutInflater mLi= LayoutInflater.from(this);
		locationView =mLi.inflate(R.layout.location, null);
		historyView =mLi.inflate(R.layout.history, null);
		aboutView = mLi.inflate(R.layout.about, null);
		
		locationHelper = new LocationViewHelper(locationView);
		
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
		mNumberText.setText("415895927310");
		ArrayAdapter<String> adapter=  new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, 
						getResources().getStringArray(R.array.company));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCompanySpinner.setAdapter(adapter);
		mCompanySpinner.setOnItemSelectedListener(new CompanyItemSelectedListener());
		mSearch.setOnClickListener(new SearchClickListener());
	}
	/**
	 * use custom view to show info ,SelectedLoadViewPager
	 * @param views
	 * @param titles
	 */
	private void useSelectLoadView(ArrayList<View> views,ArrayList<String>titles) {
		viewPager = (SelectedLoadViewPager)findViewById(R.id.myviewpager);
		viewPager.setVisibility(View.VISIBLE);
		viewPager.setAdapter(new CustomPagerAdapter(views, titles));
	}
	/**
	 * use custom view to show info,TabViewPager
	 * @param views
	 * @param titles
	 */
	private void useTabView(ArrayList<View> views,ArrayList<String>titles) {
		TabViewPager tabPager = (TabViewPager)findViewById(R.id.tabviewpager);
		tabPager.setVisibility(View.VISIBLE);
		String []tabs =new String[titles.size()];
		titles.toArray(tabs);
		tabPager.initTabs(tabs);
		tabPager.setAdapter(new CustomPagerAdapter(views));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void hideSoftInput() {
		InitConfig.hiddenSoftInput(this, mNumberText.getWindowToken());
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
	/**
	 * which company be selected
	 * @param position
	 */
	public void setCurrentIndex(int position) {
		currentPosition = position;
	}
	/**
	 * click event for search button
	 * @author tongdongsheng
	 *
	 */
	private class SearchClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			final String[] array = v.getResources().getStringArray(R.array.company_values);
			final String number =mNumberText.getText().toString();
			viewPager.setCurrentItem(0);
			if(InitConfig.isEmptyString(number)) {
				locationHelper.clear();
				Toast.makeText(v.getContext(), v.getResources().getString(R.string.search_number_empty), Toast.LENGTH_SHORT).show();
				return;
			}
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String company=array[currentPosition];
					LogUtils.i(TAG, String.format("number=%s company=%s", number,company));
					String result = searchHelper.search(number, company);
					mHandler.sendMessage(mHandler.obtainMessage(SEARCH_KEY, result));
				}
			}).start();
			
		}
		
	}

	private class CompanyItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			setCurrentIndex(position);
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 * for hide soft input when touch other view;
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if(isShouldHideKeyboard(v, event)){
				hideSoftInput();
			}
		}
		return super.dispatchTouchEvent(event);
	}
	/**
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	private boolean isShouldHideKeyboard(View v,MotionEvent event) {
		if(v!=null&& v instanceof EditText) {
			int[] location={0,0};
			v.getLocationInWindow(location);
			int left = location[0];
			int top = location[1];
			int bottom = top+v.getHeight();
			int right = left+v.getWidth();
			if(event.getX()>left && event.getX()<right
			   &&event.getY()>top && event.getY()<bottom) {
				return false;
			} 
			return true;
		}
		return false;
	}
}
