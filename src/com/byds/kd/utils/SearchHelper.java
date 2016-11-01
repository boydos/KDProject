package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.bigknow.minero.model.ModelBean;
import com.byds.kd.R;
import com.byds.kd.bean.OrderDetialInfo;
import com.byds.kd.bean.OrderInfo;

public class SearchHelper implements IContants{
	private Context mContext;
	private KdniaoQueryAPI query;
	private String [] companies;
	private DataBaseHelper dbHelper;
	public SearchHelper(Context context) {
		mContext = context;
		companies=context.getResources().getStringArray(R.array.company_values);
		query = new KdniaoQueryAPI();
		dbHelper = DataBaseHelper.getInstance(mContext);
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
		updateOrInsert(number, company, getSate(msg));
		return msg;
	}
	private String searchAll(String number) {
		int len = companies==null?0:companies.length;
		if(len>1) {
			for(int i=1;i<len;i++) {
				String msg =search(number, companies[i]);
				if(isHasInfo(msg)) {
					updateOrInsert(number, companies[i], getSate(msg));
					return msg;
				}
			}
		}
		updateOrInsert(number, "AUTO", STATE_INVILID);
		return null;
	}
	private int getSate(String msg) {
		ModelBean result= getResultModel(msg);
		int state = result.getInt("State", -1);
		if(state == 2) {
			return STATE_SENDING;
		} else if(state==3) {
			return STATE_GET;
		} else if(state==4) {
			return STATE_PROBLEM;
		} else return STATE_INVILID;
	}
	
	public void updateOrInsert(String number,String company,int state){
		List<OrderInfo> list = dbHelper.query(KEY_NUMBER+"=?", new String[]{number});
		if(list==null|| list.size()==0) {
			dbHelper.insert(company, number, DateTools.getNowTime(), state);
		} else {
			OrderInfo order= list.get(0);
			order.setCompany(company);
			order.setDate(DateTools.getNowTime());
			order.setState(state);
			dbHelper.update(order);
		}
	}
	private List<OrderInfo> getHistory() {
		return dbHelper.query(null, null);
	}
	
}
