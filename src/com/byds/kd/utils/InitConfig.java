package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.byds.kd.bean.OrderDetialInfo;

public class InitConfig {

	public static void hiddenSoftInput(Context context,IBinder token) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())imm.hideSoftInputFromWindow(token, 0);
	}
	public static boolean isEmptyString(String str) {
		return str==null||"".equals(str.trim());
	}
	
	public static List<OrderDetialInfo>getTestKDInfos() {
		List<OrderDetialInfo> infos = new ArrayList<OrderDetialInfo>();
		for(int i=0;i<10;i++) {
			infos.add(new OrderDetialInfo("2016-10-28 12:30:0"+i, "28包裹在"+i+"这里"));
			infos.add(new OrderDetialInfo("2016-10-27 12:30:0"+i, "27包裹在"+i+"这里"));
			infos.add(new OrderDetialInfo("2016-10-26 12:30:0"+i, "26包裹在"+i+"这里"));
			infos.add(new OrderDetialInfo("2016-10-24 12:30:0"+i, "24包裹在"+i+"这里"));
			infos.add(new OrderDetialInfo("2016-10-23 12:30:0"+i, "23包裹在"+i+"这里"));
		}
		Collections.sort(infos,new OrderDetialComparator());
		return infos;
	}
	
	public static boolean isWifiEnable(Context context) {
		return true;
	}
}
