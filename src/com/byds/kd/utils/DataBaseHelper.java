package com.byds.kd.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.byds.kd.bean.OrderInfo;

public class DataBaseHelper extends SQLiteOpenHelper implements IContants{
	private static final String DATABASE_NAME="kd_database.db";
	private static final String DATABASE_TABLE="kd";
	private static final int DATABASE_VERSION=1;
	private static final String [] COLUMNS=new String[]{
		KEY_ID,
		KEY_COMPANY,
		KEY_DATE,
		KEY_NUMBER,
		KEY_STATE
	};
	private static final String DATABASE_CREATE="create table " +DATABASE_TABLE
			+" ("+KEY_ID +" integer primary key autoincrement, "
			+KEY_COMPANY +" text not null, "
			+KEY_DATE +" text not null, "
			+KEY_NUMBER+" text not null, "
			+KEY_STATE+" integer);";
	private static DataBaseHelper baseHelper =null;
	public  static DataBaseHelper getInstance(Context context) {
		if(baseHelper==null) {
			baseHelper= new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		return baseHelper;
	}
	private DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}
	public long insert(OrderInfo info) {
		return insert(info.getCompany(), info.getNumber(), info.getNumber(), info.getState());
	}
	public long insert(String company,String number,String date,int state) {
		return getWritableDatabase().insert(DATABASE_TABLE, null, getValues(company, number, date, state));
	}
	public List<OrderInfo> query(String where,String[] whereArgs) {
		return query(where, whereArgs, null, null, "order by "+KEY_DATE+" desc");
	}
	public List<OrderInfo> query(String where,String[] whereArgs,String groupBy,String having,String orderBy){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.query(DATABASE_TABLE, COLUMNS, where, whereArgs, groupBy, having, orderBy);
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		while(cursor!=null&&cursor.moveToNext()) {
			OrderInfo order = new OrderInfo();
			order.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
			order.setState(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_STATE)));
			order.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(KEY_COMPANY)));
			order.setNumber(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NUMBER)));
			order.setDate(cursor.getString(cursor.getColumnIndexOrThrow(KEY_DATE)));
			list.add(order);
		}
		cursor.close();
		return list;
	}
	public long delete(int id) {
		return delete(KEY_ID+"="+id,null);
	}
	public long delete(String where,String[]whereArgs) {
		return getWritableDatabase().delete(DATABASE_NAME, where, whereArgs);
	}
	public long update(OrderInfo info) {
		return update(info.getId(),info.getCompany(),info.getNumber(),info.getDate(),info.getState());
	}
	public long update(int id,String company,String number,String date,int state) {
		return getWritableDatabase().update(DATABASE_TABLE, getValues(company, number, date, state), KEY_ID+"="+id, null);
	}
	
	public ContentValues getValues(String company,String number,String date,int state) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_COMPANY, company);
		newValues.put(KEY_NUMBER, number);
		newValues.put(KEY_STATE, state);
		newValues.put(KEY_DATE, date);
		return newValues;
	}
}
