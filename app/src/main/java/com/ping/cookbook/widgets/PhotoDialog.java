package com.ping.cookbook.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.ping.cookbook.R;


public class PhotoDialog extends Dialog implements View.OnClickListener {
    private Activity activity;

    public PhotoDialog(Activity activity) {
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
        setContentView(R.layout.dialog_photo);
        findViewById(R.id.tv_image).setOnClickListener(this);
        findViewById(R.id.tv_photo).setOnClickListener(this);
        setCanceledOnTouchOutside(true);
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
        if (listener == null)
            return;
        switch (view.getId()) {
            case R.id.tv_photo:
                listener.OnImageListener(2);
                break;
            case R.id.tv_image:
                listener.OnImageListener(1);
                break;
        }
    }

    private PhotoDialogListener listener;

    public void setPhotoDialogListener(PhotoDialogListener listener) {
        this.listener = listener;
    }

    public interface PhotoDialogListener {
        public void OnImageListener(int position);
    }
}