package com.ping.cookbook.module.cookbook.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.Step;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.widgets.photoview.PhotoView;

/**
 * @describe <图集浏览的ViewPager的适配器>
 * @author 温华平
 * @time 2015-10-22
 */
public class ImagePagerAdapter extends PagerAdapter implements OnClickListener {
	private List<Step> urls;
	private OnImageTapListener listener;
	private Context context;

	public ImagePagerAdapter(Context context) {
		urls = new ArrayList<Step>();
		this.context=context;
	}

	public void setDatas(List<Step> tmp) {
		urls.clear();
		urls.addAll(tmp);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		int size = urls.size();
		return size;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = View.inflate(context, R.layout.layout_imagepager, null);
		PhotoView photoview = (PhotoView) view.findViewById(R.id.pv_view);
		TextView pv_text = (TextView) view.findViewById(R.id.pv_text);
		photoview.enable();
		photoview.setOnClickListener(this);
		String[] split = urls.get(position).getStep().split("\\.");
		pv_text.setText(split[1]);
		Glide.with(context).load(urls.get(position).getImg()).placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(photoview);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View view = (View) object;
		container.removeView(view);
		System.gc();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	public void setOnImageTapListener(OnImageTapListener listener) {
		this.listener = listener;
	}

	public interface OnImageTapListener {
		public void onImageViewTapped();
	}

	@Override
	public void onClick(View v) {
		if (listener != null) {
			listener.onImageViewTapped();
		}
	}
}
