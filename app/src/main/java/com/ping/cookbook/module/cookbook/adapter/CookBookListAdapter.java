package com.ping.cookbook.module.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ping.cookbook.R;
import com.ping.cookbook.bean.CookIndex;
import com.ping.cookbook.module.cookbook.activity.CookDetailActivity;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.util.ArrayList;
import java.util.List;

import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.ToolUtils;


public class CookBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    private BannerView banner;
    private List<CookIndex> mList;
    private Context context;

    public CookBookListAdapter(Context context, BannerView banner) {
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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeader(position)? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    /**
     * * 复用getItemViewType方法，根据位置返回相应的ViewType    * @param position    * @return
     */
    @Override
    public int getItemViewType(int position) {     //如果是0，就是头，否则则是其他的item
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
            return new HeaderViewHolder(View.inflate(context, R.layout.listview_item_cookbook_head, null));
        } else {
            // 对于Body中的item，我们也返回所对应的ViewHolder
            return new CookBookListHolder(View.inflate(context, R.layout.listview_item_cookbook, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position)) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            if (NetUtil.networkEnable() && banner != null) {
                headerViewHolder.pic1Layout.setVisibility(View.VISIBLE);
                banner.loadAD();
                banner.setRefresh(30);
                banner.setADListener(new AbstractBannerADListener() {
                    @Override
                    public void onNoAD(int i) {
                        headerViewHolder.pic1Layout.setVisibility(View.GONE);
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
                        headerViewHolder.pic1Layout.addView(banner);
                    }
                });
            } else {
                headerViewHolder.pic1Layout.setVisibility(View.GONE);
            }
        } else {
            final CookBookListHolder cookBookListHolder = (CookBookListHolder) holder;
            cookBookListHolder.itemView.setTag(R.id.position, position-1);
            CookIndex info = mList.get(position-1);
            int imageHeight = ToolUtils.getImageHeight(context, 4, 2);
            Glide.with(context).load(info.getAlbums()).asBitmap().placeholder(R.drawable.image_default).error(R.drawable.image_failed).into(cookBookListHolder.iv_image);
            ViewGroup.LayoutParams layoutParams = cookBookListHolder.iv_image.getLayoutParams();
            layoutParams.height = imageHeight;
            cookBookListHolder.iv_image.setLayoutParams(layoutParams);
            cookBookListHolder.tv_title.setText(info.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public void onClick(View v) {
        final int position = (int) v.getTag(R.id.position);
        CookIndex info = mList.get(position);
        Intent intent = new Intent(context, CookDetailActivity.class);
        intent.putExtra("CookIndex", info);
        context.startActivity(intent);
    }

    /**
     * 给头部专用的ViewHolder
     */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout pic1Layout;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            pic1Layout = (FrameLayout) itemView.findViewById(R.id.pic1Layout);
        }
    }

    class CookBookListHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_title;

        public CookBookListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(CookBookListAdapter.this);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
