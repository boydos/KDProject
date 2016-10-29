package com.byds.kd.utils;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.byds.kd.R;
import com.byds.kd.adapter.TimeLineAdapter;

public class LocationViewHelper implements
AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener,
StickyListHeadersListView.OnStickyHeaderOffsetChangedListener,
StickyListHeadersListView.OnStickyHeaderChangedListener{
	private StickyListHeadersListView stickView;
	private TextView noDataView;
	private TimeLineAdapter mAdapter;
	private boolean fadeHeader=true;
	public LocationViewHelper (View locationView) {
		mAdapter =new TimeLineAdapter(locationView.getContext());
		stickView = (StickyListHeadersListView)locationView.findViewById(R.id.locations);
		noDataView = (TextView)locationView.findViewById(R.id.no_data);
		stickView.setStickyHeaderTopOffset(-20);
		
		stickView.setOnItemClickListener(this);
		stickView.setOnHeaderClickListener(this);
		stickView.setOnStickyHeaderChangedListener(this);
		stickView.setOnStickyHeaderOffsetChangedListener(this);
		stickView.setDrawingListUnderStickyHeader(true);
		stickView.setAreHeadersSticky(true);
		
		stickView.setAdapter(mAdapter);
		setShowDataView(false);
	}
	
	
	public void updateData(String mNumber,String [] mTimes,String[] mData) {
		if(mData!=null&&mData.length>0) {
			setShowDataView(true);
			mAdapter.updateData(mNumber, mTimes, mData);
		} else {
			clear();
		}
	}
	
	public void clear() {
		mAdapter.clear();
		setShowDataView(false);
	}
	private void setShowDataView(boolean show) {
		stickView.setVisibility(show?View.VISIBLE:View.GONE);
		noDataView.setVisibility(show?View.GONE:View.VISIBLE);
	}
	
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onStickyHeaderChanged(StickyListHeadersListView l, View header,
			int offset, long headerId) {
		// TODO Auto-generated method stub
		if (fadeHeader && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            header.setAlpha(1 - (offset / (float) header.getMeasuredHeight()));
        }
	}
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onStickyHeaderOffsetChanged(StickyListHeadersListView l,
			View header, int offset) {
		// TODO Auto-generated method stub
		header.setAlpha(1);
	}
	@Override
	public void onHeaderClick(StickyListHeadersListView l, View header,
			int itemPosition, long headerId, boolean currentlySticky) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
}
