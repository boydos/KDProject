package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Map;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.byds.kd.R;

public class HistoryViewHelper implements OnItemClickListener{
	private TextView mNoHistory;
	private ListView mHistory;
	private BaseAdapter mAdapter;
	private Handler mHandler;
	private ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
	public HistoryViewHelper(View history,Handler mHandler) {
		this.mHandler = mHandler;
		mNoHistory =(TextView) history.findViewById(R.id.no_history);
		mHistory =(ListView)history.findViewById(R.id.history_list);
		mAdapter = new SimpleAdapter(history.getContext(), mData, android.R.layout.simple_list_item_2,
				new String[]{"number","info"}, new int[]{android.R.id.text1,android.R.id.text2});
		mHistory.setOnItemClickListener(this);
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		mHandler.sendMessage(mHandler.obtainMessage(1, ""));
	}
}
