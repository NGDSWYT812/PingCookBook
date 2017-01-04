package com.ping.cookbook.module.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.module.cookbook.activity.CookDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class CollectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<CookIndex> mList;
    private Context context;

    public CollectListAdapter(Context context) {
        mList = new ArrayList<CookIndex>();
        this.context = context;
    }

    public void setData(List<CookIndex> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new RecommendListHolder(View.inflate(context, R.layout.listview_item_recommend, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((RecommendListHolder) holder).itemView.setTag(R.id.position, position);
        CookIndex info = mList.get(position);
        Glide.with(context).load(info.getAlbums()).asBitmap().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(((RecommendListHolder) holder).iv_image);
        ((RecommendListHolder) holder).tv_tags.setText(info.getTags());
        ((RecommendListHolder) holder).tv_title.setText(info.getTitle());
        ((RecommendListHolder) holder).pic1Layout.setVisibility(View.GONE);
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

    class RecommendListHolder extends RecyclerView.ViewHolder {
        private FrameLayout pic1Layout;
        ImageView iv_image;
        TextView tv_tags;
        TextView tv_title;

        public RecommendListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(CollectListAdapter.this);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            pic1Layout = (FrameLayout) itemView.findViewById(R.id.pic1Layout);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
