package com.byds.kd.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	public static  void showMsg(Context context,int id) {
		Toast.makeText(context, context.getResources().getString(id),Toast.LENGTH_SHORT).show();
	}
	public static  void showMsg(Context context,String msg) {
		Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
	}
}
