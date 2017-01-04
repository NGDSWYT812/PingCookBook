package com.ping.cookbook.module.recommend.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.module.cookbook.activity.CookDetailActivity;
import com.ping.cookbook.module.recommend.widgets.ConvenientImageHolderView;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.Utils.NetUtil;


public class RecommendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, OnItemClickListener {
    private List<CookIndex> BannerList = new ArrayList<CookIndex>();
    private List<CookIndex> TopList = new ArrayList<CookIndex>();
    private static final int TYPE_TOP = 0;
    private static final int TYPE_ITEM = 1;
    private BannerView banner;
    private List<CookIndex> mList;
    private Context context;

    public RecommendListAdapter(Context context, BannerView banner) {
        mList = new ArrayList<CookIndex>();
        this.context = context;
        this.banner = banner;
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_TOP) {
            holder = new RecommendTopHolder(View.inflate(context, R.layout.fragment_recommend_head, null));
        } else if (viewType == TYPE_ITEM) {
            holder = new RecommendListHolder(View.inflate(context, R.layout.listview_item_recommend, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TOP:
                BannerList.clear();
                TopList.clear();
                ((RecommendTopHolder) holder).tv_notification.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                ((RecommendTopHolder) holder).tv_notification.setSingleLine(true);
                ((RecommendTopHolder) holder).tv_notification.setSelected(true);
                ((RecommendTopHolder) holder).tv_notification.setFocusable(true);
                ((RecommendTopHolder) holder).tv_notification.setMarqueeRepeatLimit(-1);
                ((RecommendTopHolder) holder).tv_notification.setFocusableInTouchMode(true);
                BannerList.add(mList.get(0));
                BannerList.add(mList.get(1));
                BannerList.add(mList.get(2));
                TopList.add(mList.get(3));
                TopList.add(mList.get(4));
                ((RecommendTopHolder) holder).tv_one.setTag(R.id.position, 3);
                ((RecommendTopHolder) holder).tv_two.setTag(R.id.position, 4);
                ((RecommendTopHolder) holder).tv_one.setText(TopList.get(0).getTitle());
                ((RecommendTopHolder) holder).tv_two.setText(TopList.get(1).getTitle());
                Glide.with(context).load(TopList.get(0).getAlbums()).crossFade().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(((RecommendTopHolder) holder).iv_one);
                Glide.with(context).load(TopList.get(1).getAlbums()).crossFade().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(((RecommendTopHolder) holder).iv_two);
                ((RecommendTopHolder) holder).convenientBanner.setPages(new CBViewHolderCreator<ConvenientImageHolderView>() {
                    @Override
                    public ConvenientImageHolderView createHolder() {
                        return new ConvenientImageHolderView();
                    }
                }, BannerList).setPageIndicator(new int[]{R.drawable.page_indicator, R.drawable.page_indicator_focused})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
                if (!((RecommendTopHolder) holder).convenientBanner.isTurning()) {
                    ((RecommendTopHolder) holder).convenientBanner.startTurning(3000).setOnItemClickListener(this);
                }
                break;
            case TYPE_ITEM:
                ((RecommendListHolder) holder).itemView.setTag(R.id.position, position + 4);
                CookIndex info = mList.get(position + 4);
                Glide.with(context).load(info.getAlbums()).asBitmap().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(((RecommendListHolder) holder).iv_image);
                ((RecommendListHolder) holder).tv_tags.setText(info.getTags());
                ((RecommendListHolder) holder).tv_title.setText(info.getTitle());
                if (position == 1 && NetUtil.networkEnable()) {
                    ((RecommendListHolder) holder).pic1Layout.setVisibility(View.VISIBLE);
                    banner.loadAD();
                    banner.setRefresh(30);
                    banner.setADListener(new AbstractBannerADListener() {
                        @Override
                        public void onNoAD(int i) {
                            ((RecommendListHolder) holder).pic1Layout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onADReceiv() {
                            RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            banner.setLayoutParams(layout);
                            ViewGroup p = (FrameLayout) banner.getParent();
                            if (p != null) {
                                p.removeAllViewsInLayout();
                            }
                            ((RecommendListHolder) holder).pic1Layout.addView(banner);
                        }
                    });
                } else {
                    ((RecommendListHolder) holder).pic1Layout.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() - 4;
    }

    @Override
    public void onClick(View v) {
        final int position = (int) v.getTag(R.id.position);
        CookIndex info = mList.get(position);
        Intent intent = new Intent(context, CookDetailActivity.class);
        intent.putExtra("CookIndex", info);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
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
            itemView.setOnClickListener(RecommendListAdapter.this);
            tv_tags = (TextView) itemView.findViewById(R.id.tv_tags);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            pic1Layout = (FrameLayout) itemView.findViewById(R.id.pic1Layout);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    class RecommendTopHolder extends RecyclerView.ViewHolder {
        private ConvenientBanner convenientBanner;
        private ImageView iv_one, iv_two;
        private TextView tv_one, tv_two, tv_notification;

        public RecommendTopHolder(View itemView) {
            super(itemView);
            tv_one = (TextView) itemView.findViewById(R.id.tv_one);
            tv_two = (TextView) itemView.findViewById(R.id.tv_two);
            iv_one = (ImageView) itemView.findViewById(R.id.iv_one);
            iv_two = (ImageView) itemView.findViewById(R.id.iv_two);
            tv_one.setOnClickListener(RecommendListAdapter.this);
            tv_two.setOnClickListener(RecommendListAdapter.this);
            tv_notification = (TextView) itemView.findViewById(R.id.tv_notification);
            convenientBanner = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner);
        }
    }
}
