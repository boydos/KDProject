package com.byds.kd.utils;

import android.util.Log;

public class LogUtils {
	private static final String MY_TAG="tds";
	public static void d(String tag,String msg) {
		Log.d(tag, String.format("[%s] [%s]", MY_TAG,msg));
	}
	
	public static void i(String tag,String msg) {
		Log.i(tag, String.format("[%s] [%s]", MY_TAG,msg));
	}
	
	public static void e(String tag,String msg) {
		Log.e(tag, String.format("[%s] [%s]", MY_TAG,msg));
	}
	
	public static void w(String tag,String msg) {
		Log.w(tag, String.format("[%s] [%s]", MY_TAG,msg));
	}
}
