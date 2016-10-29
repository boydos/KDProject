package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bigknow.minero.model.ModelBean;
import com.byds.kd.bean.OrderDetialInfo;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class SearchHelper {
	private Context mContext;
	private KdniaoQueryAPI query;
	public SearchHelper(Context context) {
		mContext = context;
		query = new KdniaoQueryAPI();
	}
	
	public List<OrderDetialInfo> getDetailFromResult(String result) {
		List<OrderDetialInfo> traces = new ArrayList<OrderDetialInfo>();
		if(result==null||"".equals(result.trim())) return traces;
		ModelBean model = new ModelBean(result);
		List<ModelBean> locations = model.getList("Traces");
		if(locations!=null) {
			for(ModelBean m:locations) {
				if(m!=null) {
					OrderDetialInfo detail = new OrderDetialInfo(m.getString("AcceptTime"), m.getString("AcceptStation"));
					traces.add(detail);
				}
			}
		}
		Collections.sort(traces,new OrderDetialComparator());
		return traces;
	}
	
	public String search(String number,String company) {
		//return InitConfig.getTestKDInfos();
		String msg=null;
		try {
			msg = query.getOrderTracesByJson(company, number);//query.getOrderTraces(company, number);
			Log.d("tds", "tds info"+msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("tds", "tds error"+e.getMessage());
		}
		return msg;
	}
}
