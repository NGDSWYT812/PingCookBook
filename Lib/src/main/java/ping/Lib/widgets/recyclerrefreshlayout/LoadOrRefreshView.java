package ping.Lib.widgets.recyclerrefreshlayout;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import ping.Lib.R;


/**
 * fuction : RecylerView 上拉和刷新封装
 */
public class LoadOrRefreshView extends FrameLayout {
    private boolean isLoadMoreEnable = true;
    private boolean isLoading = false;
    private boolean isAllItemLoaded = false;
    private RecyclerRefreshLayout refresh_layout;
    private RecyclerView mRecyclerView;
    private PullCallBack mCallback;
//    private RelativeLayout rl_buttom;
    private RecyclerViewPositionUtil mRecyclerUtil;
    private int mLoadMoreOffset = 0;
    private Handler handler;

    /**
     * 回调
     */
    public interface PullCallBack {
        void onLoadMore();

        void onRefresh();
    }

    public LoadOrRefreshView(Context context) {
        this(context, null);
    }

    public LoadOrRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadOrRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.load_or_refresh_view, this, true);
        refresh_layout = (RecyclerRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        rl_buttom = (RelativeLayout) view.findViewById(R.id.rl_buttom);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        handler = new Handler(context.getMainLooper());
        init();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerUtil = RecyclerViewPositionUtil.createHelper(mRecyclerView);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRecyclerUtil = RecyclerViewPositionUtil.createHelper(mRecyclerView);
    }

    private void init() {
        refresh_layout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mCallback) {
                    setLoadComplete();
                    mCallback.onRefresh();
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 是否可以加载更多，以及判断是否是加载更多的操作
                if (isLoadMoreEnable && dy > 0) {
                    final int totalItemCount = mRecyclerUtil.getItemCount();
                    final int firstVisibleItem = mRecyclerUtil.findFirstVisibleItemPosition();
                    final int visibleItemCount = Math.abs(mRecyclerUtil.findLastVisibleItemPosition() - firstVisibleItem);
                    final int lastAdapterPosition = totalItemCount - 1;
                    final int lastVisiblePosition = (firstVisibleItem + visibleItemCount);
                    if (!isLoading && !isAllItemLoaded) {
                        // 偏移量预加载
                        if (lastVisiblePosition >= (lastAdapterPosition - mLoadMoreOffset)) {
                            if (null != mCallback) {
                                isLoading = true;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mCallback.onLoadMore();
                                    }
                                });
                                setRefreshing(false);
                            }
                        }
                    }

                    if (isLoading && !isAllItemLoaded) {
                        if (lastVisiblePosition >= lastAdapterPosition) {
//                            rl_buttom.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    public void setRefreshing(boolean refreshing) {
        refresh_layout.setRefreshing(refreshing);
    }

    public void setLoadComplete() {
//        rl_buttom.setVisibility(View.GONE);
        isLoading = false;
    }

    public void setPullCallBack(PullCallBack callback) {
        this.mCallback = callback;
    }

    public void setIsLoadMoreEnable(boolean isLoadMoreEnable) {
        this.isLoadMoreEnable = isLoadMoreEnable;
    }

    public void setLoadMoreOffset(int mLoadMoreOffset) {
        this.mLoadMoreOffset = mLoadMoreOffset;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

}
