package ping.Lib.widgets.recyclerrefreshlayout;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;


/**
*
*  Description : recycler帮助类
*  Creator     : Zhangguobao
*  Date        : 2015/11/11   16:58
*
*/

public class RecyclerViewPositionUtil {

    final RecyclerView recyclerView;
    final RecyclerView.LayoutManager layoutManager;


    RecyclerViewPositionUtil(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = recyclerView.getLayoutManager();
    }

    public static RecyclerViewPositionUtil createHelper(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("Recycler View is null");
        }
        return new RecyclerViewPositionUtil(recyclerView);
    }

    /**
    *
    *  Description :  返回个数
    *  Creator     : Zhangguobao
    *  Date        : 2015/11/11   16:58
    *
    */
    public int getItemCount() {
        return layoutManager == null ? 0 : layoutManager.getItemCount();
    }

    /**
     * Returns the adapter position of the first visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the first visible item or {@link RecyclerView#NO_POSITION} if
     * there aren't any visible items.
     */
    public int findFirstVisibleItemPosition() {
        if (layoutManager == null) {
            return NO_POSITION;
        }
        final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Returns the adapter position of the first fully visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the first fully visible item or
     * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
     */
    public int findFirstCompletelyVisibleItemPosition() {
        if (layoutManager == null) {
            return NO_POSITION;
        }
        final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Returns the adapter position of the last visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the last visible view or {@link RecyclerView#NO_POSITION} if
     * there aren't any visible items
     */
    public int findLastVisibleItemPosition() {
        if (layoutManager == null) {
            return NO_POSITION;
        }
        final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Returns the adapter position of the last fully visible view. This position does not include
     * adapter changes that were dispatched after the last layout pass.
     *
     * @return The adapter position of the last fully visible view or
     * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
     */
    public int findLastCompletelyVisibleItemPosition() {
        if (layoutManager == null) {
            return NO_POSITION;
        }
        final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, true, false);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    public View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                             boolean acceptPartiallyVisible) {
        if (layoutManager == null) {
            return null;
        }
        OrientationHelper helper;
        if (layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = layoutManager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}
