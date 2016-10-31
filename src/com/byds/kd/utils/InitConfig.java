package com.byds.kd.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
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
	public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifi.isConnected() || mobile.isConnected();
    }
	public static boolean isWifiEnable(Context context) {
		WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
	}
	public static boolean getMobileDataEnable(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> ownerClass = connectionManager.getClass();

        try {
            Class<?>[] parent = null;
            Object[] args = null;
            Method method = ownerClass
                    .getMethod("getMobileDataEnabled", parent);
            Boolean isOpen = (Boolean) method.invoke(connectionManager, args);
            return isOpen;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
