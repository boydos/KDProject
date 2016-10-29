package com.byds.kd.utils;

import java.util.Comparator;

import com.byds.kd.bean.OrderDetialInfo;

public class OrderDetialComparator implements Comparator<OrderDetialInfo>{

	@Override
	public int compare(OrderDetialInfo lhs, OrderDetialInfo rhs) {
		// TODO Auto-generated method stub
		if(lhs.getDate()==null) return 1;
		if(rhs.getDate()==null) return -1;
		return rhs.getDate().compareTo(lhs.getDate());
	}

}
