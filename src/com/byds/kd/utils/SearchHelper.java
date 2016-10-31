package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.bigknow.minero.model.ModelBean;
import com.byds.kd.R;
import com.byds.kd.bean.OrderDetialInfo;

public class SearchHelper {
	private Context mContext;
	private KdniaoQueryAPI query;
	private String [] companies;
	public SearchHelper(Context context) {
		mContext = context;
		companies=context.getResources().getStringArray(R.array.company_values);
		query = new KdniaoQueryAPI();
	}
	
	public List<OrderDetialInfo> getDetailFromResult(String result) {
		List<OrderDetialInfo> traces = new ArrayList<OrderDetialInfo>();
		ModelBean model = getResultModel(result);
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
	
	public ModelBean getResultModel(String result) {
		if(result==null||"".equals(result.trim())) return new ModelBean();
		return new ModelBean(result);		
	}
	public boolean isSuccess(String msg) {
		ModelBean result= getResultModel(msg);
		if(result.get("Success")!=null&&result.getBoolean("Success")) {
			return true;
		}
		return false;
	}
	public boolean isHasInfo(String msg) {
		ModelBean result= getResultModel(msg);
		if((result.getString("Reason")!=null&&result.getString("State")==null)||"此单无物流信息".equalsIgnoreCase(result.getString("Reason"))) {
			return false;
		}
		return true;
	}
	public String search(String number, int index) {
		if(index==0){
			return searchAll(number);
		}
		return search(number, companies[index]);
	}

	private String search(String number,String company) {
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
	private String searchAll(String number) {
		int len = companies==null?0:companies.length;
		if(len>1) {
			for(int i=1;i<len;i++) {
				String msg =search(number, companies[i]);
				if(isHasInfo(msg)) {
					return msg;
				}
			}
		}
		return null;
	}
	
}
