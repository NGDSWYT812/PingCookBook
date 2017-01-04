package com.ping.cookbook.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.ping.cookbook.R;


public class CommonDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    public CommonDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        init();
    }

    public void show() {
        super.show();
    }

    public void hide() {
        super.dismiss();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(R.layout.dialog_common);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.ok).setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.height = LayoutParams.WRAP_CONTENT;
        lp.width = LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onClick(View view) {
        hide();
        switch (view.getId()) {
            case R.id.ok:
                activity.finish();
                System.exit(0);
                break;
        }
    }

}