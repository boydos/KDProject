package com.byds.kd.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

public class InitConfig {
	
	public static void hiddenSoftInput(Context context,IBinder token) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive())imm.hideSoftInputFromWindow(token, 0);
	}
	public static boolean isEmptyString(String str) {
		return str==null||"".equals(str.trim());
	}
}
