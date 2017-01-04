package com.ping.cookbook.module.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.module.cookbook.activity.CookDetailActivity;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.widgets.RoundedImageView;


public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<CookIndex> mList;
    private Context context;

    public SearchListAdapter(Context context) {
        mList = new ArrayList<CookIndex>();
        this.context = context;
    }

    public void setData(List<CookIndex> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<CookIndex> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CookBookListHolder(View.inflate(context, R.layout.listview_item_search, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CookBookListHolder cookBookListHolder = (CookBookListHolder) holder;
        cookBookListHolder.itemView.setTag(R.id.position, position);
        CookIndex info = mList.get(position);
        Glide.with(context).load(mList.get(position).getAlbums()).asBitmap().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(cookBookListHolder.iv_image);
        cookBookListHolder.tv_tags.setText(info.getTags().length() > 1 ? "标签：" + info.getTags().replace(";", "、").substring(0, info.getTags().length() - 1) : "标签：无");
        cookBookListHolder.tv_title.setText(info.getTitle());
        cookBookListHolder.tv_tintro.setText("食材：" + info.getIngredients());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        final int position = (int) v.getTag(R.id.position);
        CookIndex info = mList.get(position);
        Intent intent = new Intent(context, CookDetailActivity.class);
        intent.putExtra("CookIndex", info);
        context.startActivity(intent);
    }

    class CookBookListHolder extends RecyclerView.ViewHolder {
        RoundedImageView iv_image;
        TextView tv_tags;
        TextView tv_title;
        TextView tv_tintro;

        public CookBookListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(SearchListAdapter.this);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_tintro = (TextView) itemView.findViewById(R.id.tv_tintro);
            iv_image = (RoundedImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
