package com.ping.cookbook.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ping.Lib.Utils.CommonUtil;

public abstract class TinyBaseAdapter<T> extends BaseAdapter {

	protected List<T> mList;
	public Context context;
	private int layoutId;

	public TinyBaseAdapter(Context context, List<T> mList, int layoutId) {
		this.context = context;
		this.mList = mList;
		this.layoutId = layoutId;
	}

	public TinyBaseAdapter(List<T> mList, int layoutId){
		this(CommonUtil.getApplicationContext(),mList,layoutId);
	}
	@Override
	public int getCount() {
		if(mList!=null)
			return mList.size();
		return 0;
	}

	@Override
	public T getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parents) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(layoutId, parents,false);
		View v = convert(convertView,mList.get(position) ,position);
		return v;
	}

	public abstract View convert(View convertView,T bean,int position);
}
