package com.byds.kd.adapter;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.byds.kd.R;
import com.byds.kd.utils.DateTools;

public class TimeLineAdapter extends BaseAdapter implements StickyListHeadersAdapter,SectionIndexer{
	private Context mContext;
	private LayoutInflater mInflater;
	private String mNumber;
	private String [] mData;
	private String [] mTimes;
	
	private String [] mHeaders;
	private int [] mHeadIndexs;
	private final String TIME_FORMAT="yyyy-MM-dd hh:mm:ss";
	public TimeLineAdapter(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
	}
	public TimeLineAdapter(Context context, String number,String[] mTimes,String[] mData) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mNumber = number;
		this.mData = mData;
		this.mTimes = mTimes;
		conHeaderAndData();
	}
	
	public void updateData(String number,String[] mTimes,String[] mData) {
		this.mNumber = number;
		this.mData = mData;
		this.mTimes = mTimes;
		conHeaderAndData();
		notifyDataSetChanged();
	}
	public void clear() {
		this.mNumber = "";
		this.mData = new String[0];
		this.mTimes = new String[0];
		conHeaderAndData();
		notifyDataSetChanged();
	}
	public void conHeaderAndData() {
		ArrayList<Integer> timeIndexs =new  ArrayList<Integer>();
		ArrayList<String> times = new  ArrayList<String>();
		if(mTimes!=null) {
			String tmpDate="";
			for(int i=0;i<mTimes.length;i++) {
				String time= getDateFormTime(mTimes[i]);
				if(time!=null&& time.compareTo(tmpDate)!=0) {
					tmpDate = time;
					timeIndexs.add(i);
					times.add(time);
				}
			}
		}
		mHeadIndexs = new int[timeIndexs.size()];
		for(int i=0;i<timeIndexs.size();i++) {
			mHeadIndexs[i] = timeIndexs.get(i);
		}
		mHeaders =new String[times.size()];
		for(int i=0;i<times.size();i++) {
			mHeaders[i] = times.get(i);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData==null?0:mData.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData==null?null:mData[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.time_line_item, parent, false);
            holder.time = (TextView) convertView.findViewById(R.id.time_tv);
            holder.content = (TextView) convertView.findViewById(R.id.content_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.time.setText(getTimeFormTime(mTimes[position]));
        holder.content.setText(mData[position]);

        return convertView;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return mHeaders;
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		// TODO Auto-generated method stub
		if(mHeadIndexs==null||mHeadIndexs.length <=0) return 0;
		if(sectionIndex >=mHeadIndexs.length) {
			sectionIndex = mHeadIndexs.length-1;
		}else if(sectionIndex <0) {
			sectionIndex =0;
		}
		return mHeadIndexs[sectionIndex];
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		for(int i=0;i<mHeadIndexs.length;i++) {
			if(position <mHeadIndexs[i]) {
				return i-1;
			}
		}
		return mHeadIndexs==null||mHeadIndexs.length <=0?0:mHeadIndexs.length-1;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		  HeaderViewHolder holder;

	        if (convertView == null) {
	            holder = new HeaderViewHolder();
	            convertView = mInflater.inflate(R.layout.time_line_header, parent, false);
	            holder.text = (TextView) convertView.findViewById(R.id.date);
	            holder.day =(TextView)convertView.findViewById(R.id.date_day);
	            convertView.setTag(holder);
	        } else {
	            holder = (HeaderViewHolder) convertView.getTag();
	        }

	        // set header text as first char in name
	        String title = mHeaders[getSectionForPosition(position)];
	        holder.text.setText(title);
	        holder.day.setText(getDistanceForNow(title));
	        return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		return getSectionForPosition(position);
	}
	private String getDistanceForNow(String date){
	    long before = DateTools.stringDate2long(date);
	    long now = DateTools.stringDate2long(DateTools.getFormatString(System.currentTimeMillis(), "yyyy-MM-dd"));
        int distance = (int)((System.currentTimeMillis()-before)/(1000*60*60*24));
        String display=mContext.getResources().getString(R.string.today);
        if(distance==2) {
        	display =mContext.getResources().getString(R.string.yestoday);
        } else if(distance==-2) {
        	display =mContext.getResources().getString(R.string.tomorrow);
        } else if(distance>2) {
        	display =distance+mContext.getResources().getString(R.string.before_day);
        } else if(distance<-2) {
        	display =distance+mContext.getResources().getString(R.string.after_day);
        }
        return display;
	}
	private String getDateFormTime(String time) {
		return DateTools.string2FomatDate(time, TIME_FORMAT);
	}
	private String getTimeFormTime(String time) {
		return DateTools.string2FomatTime(time, TIME_FORMAT);
	}
	class HeaderViewHolder {
        TextView text;
        TextView day;
    }

    class ViewHolder {
        TextView time;
        TextView content;
    }
}
