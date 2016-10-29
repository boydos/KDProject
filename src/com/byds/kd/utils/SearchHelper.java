package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bigknow.minero.model.ModelBean;
import com.byds.kd.bean.OrderDetialInfo;

import android.content.Context;
import android.util.Log;

public class SearchHelper {
	private Context mContext;
	private KdniaoQueryAPI query;
	public SearchHelper(Context context) {
		mContext = context;
		query = new KdniaoQueryAPI();
	}
	
	public List<OrderDetialInfo> search(String number,String company) {
		List<OrderDetialInfo> traces = new ArrayList<OrderDetialInfo>();
		try {
			String msg = query.getOrderTracesByJson(company, number);
			Log.d("tds", "tds info"+msg);
			ModelBean model = new ModelBean(msg);
			List<ModelBean> locations = model.getList("Traces");
			
			for(ModelBean m:locations) {
				if(m!=null) {
					OrderDetialInfo detail = new OrderDetialInfo(m.getString("AcceptTime"), m.getString("AcceptStation"));
					traces.add(detail);
				}
			}
			Collections.sort(traces,new OrderDetialComparator());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("tds", "tds error"+e.getMessage());
		}
		return traces;
	}
}
