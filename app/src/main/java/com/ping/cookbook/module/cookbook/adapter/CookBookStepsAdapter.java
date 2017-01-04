package com.ping.cookbook.module.cookbook.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.base.TinyBaseAdapter;
import com.ping.cookbook.base.ViewHolder;
import com.ping.cookbook.bean.Step;

import java.util.List;

import ping.Lib.layout.RoundTextView;
import ping.Lib.widgets.RoundedImageView;


/**
 * 漫画Grid列表
 */
public class CookBookStepsAdapter extends TinyBaseAdapter<Step> {
    private Context context;

    public CookBookStepsAdapter(List<Step> mList, Context context) {
        super(mList, R.layout.listview_item_steps);
        this.context = context;
    }

    @Override
    public View convert(View convertView, Step info, int position) {
        RoundedImageView iv_image = ViewHolder.get(convertView, R.id.iv_image);
        RoundTextView rtv_steps = ViewHolder.get(convertView, R.id.rtv_steps);
        TextView tv_steps = ViewHolder.get(convertView, R.id.tv_steps);
        String[] split = info.getStep().split("\\.");
        if (info != null) {
            rtv_steps.setText("第" + (position+1)+ "步");
            tv_steps.setText(split[1]);
            Glide.with(context).load(info.getImg()).asBitmap().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(iv_image);
        }
        return convertView;
    }
}
