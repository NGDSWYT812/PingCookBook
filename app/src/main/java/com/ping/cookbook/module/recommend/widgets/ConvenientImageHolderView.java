package com.ping.cookbook.module.recommend.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.CookIndex;

public class ConvenientImageHolderView implements Holder<CookIndex> {
    private ImageView imageView;
    private TextView tv_banns;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item_view, null);
        imageView = (ImageView) view.findViewById(R.id.img_banns);
        tv_banns = (TextView) view.findViewById(R.id.tv_banns);
        return view;
    }

    @Override
    public void UpdateUI(Context context, final int position, CookIndex info) {
        Glide.with(context).load(info.getAlbums()).crossFade().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(imageView);
        tv_banns.setText(info.getTitle());
    }
}