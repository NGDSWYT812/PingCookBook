package ping.Lib.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 不可滚动的listview，不节省资源，因其一次性加载出全部
 *
 */
public class CJListView extends ListView {
	
	public CJListView(Context context) {
		super(context);
	}

	public CJListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CJListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}