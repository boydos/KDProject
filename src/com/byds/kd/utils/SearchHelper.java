package com.byds.kd.utils;

import com.byds.kd.R;

import android.content.Context;
import android.widget.Toast;

public class SearchHelper {
	private Context mContext;
	public SearchHelper(Context context) {
		mContext = context;
	}
	
	public void search(String number,String company) {
		if(InitConfig.isEmptyString(number)) {
			Toast.makeText(mContext, mContext.getResources().getString(R.string.search_number_empty), Toast.LENGTH_SHORT).show();
		}
	}
}
