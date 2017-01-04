package com.ping.cookbook.module.cookbook.widgets;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ping.cookbook.R;
import com.ping.cookbook.bean.Step;
import com.ping.cookbook.module.cookbook.adapter.ImagePagerAdapter;

import java.util.List;


/**
 * @describe <图片详情(大图)组图>
 * @author 温华平
 * @time 2015-10-22
 */
public class ImageDetailDialog extends Dialog implements ImagePagerAdapter.OnImageTapListener {
	private ImagePagerAdapter adapter;
	private ViewPager pager_image;
	private Context context;

	public ImageDetailDialog(Context context,List<Step> imageUrls) {
		super(context, R.style.dialog_image_detail);
		this.context = context;
		init(imageUrls);
	}

	private void init(List<Step> imageUrls) {
		setContentView(R.layout.layout_image_detail);
		pager_image = (ViewPager) findViewById(R.id.pager_image);
		adapter = new ImagePagerAdapter(context);
		adapter.setDatas(imageUrls);
		pager_image.setAdapter(adapter);
		adapter.setOnImageTapListener(this);
	}

	public void setPosition(int position) {
		pager_image.setCurrentItem(position);
	}

	public void show() {
		super.show();
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.width = dm.widthPixels; // 设置宽度
		lp.height=dm.heightPixels;
		getWindow().setAttributes(lp);
	}

	@Override
	public void onImageViewTapped() {
		super.dismiss();
	}
}