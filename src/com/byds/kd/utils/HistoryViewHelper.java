package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.byds.kd.R;
import com.byds.kd.bean.OrderInfo;

public class HistoryViewHelper implements OnItemClickListener,IContants{
	private TextView mNoHistory;
	private ListView mHistory;
	private BaseAdapter mAdapter;
	private Handler mHandler;
	private ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
	private List<OrderInfo> orderData = new ArrayList<OrderInfo>();
	public HistoryViewHelper(View history,Handler mHandler) {
		this.mHandler = mHandler;
		mNoHistory =(TextView) history.findViewById(R.id.no_history);
		mHistory =(ListView)history.findViewById(R.id.history_list);
		mAdapter = new SimpleAdapter(history.getContext(), mData, R.layout.history_item,
				new String[]{"number","info"}, new int[]{R.id.item_number,R.id.item_info});
		
		mHistory.setOnItemClickListener(this);
		mHistory.setAdapter(mAdapter);
		checkShow() ;
	}
	private void checkShow() {
		setHistoryShow(mData!=null&&mData.size()>0);
	}
	private void setHistoryShow(boolean show) {
		LogUtils.i("history", String.format("show =%b", show));
		mNoHistory.setVisibility(show?View.GONE:View.VISIBLE);
		mHistory.setVisibility(show?View.VISIBLE:View.GONE);
	}
	private void getMapData(List<OrderInfo> data) {
		mData.clear();
		for(OrderInfo order:data) {
			Map<String,Object> tmp = new HashMap<String, Object>();
			LogUtils.i("order:", order.getNumber());
			tmp.put("number", order.getNumber());
			tmp.put("info", String.format("[%s] [%s] [%s]", getState(order.getState()),order.getCompany(),order.getDate()));
			mData.add(tmp);
		}
	}
	public void clearData() {
		mData.clear();
		this.mAdapter.notifyDataSetChanged();
		checkShow();
	}
	private String getState(int state) {
		switch (state) {
		case STATE_GET: return "已签收";
		case STATE_INVILID: return "未查到";
		case STATE_SENDING: return "派送中";
		case STATE_PROBLEM: return "问题件";
		default:
			return "--";
		}
	}
	public void updateData(List<OrderInfo> data) {
		if(data!=null)orderData = data;
		getMapData(data);
		this.mAdapter.notifyDataSetChanged();
		checkShow();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(orderData!=null&&position<orderData.size()) {
			Message msg = new Message();
			msg.what =UPDATE_HISTORY_KEY;
			Bundle data = new Bundle();
			data.putString("number", orderData.get(position).getNumber());
			data.putString("code", orderData.get(position).getCompany());
			msg.setData(data);
			mHandler.sendMessage(msg);
		}
		
	}
}
