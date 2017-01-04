package com.ping.cookbook.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;

import com.ping.cookbook.R;


public class EditMsgDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private EditText et_nickname, ed_sign;

    public EditMsgDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        init();
    }

    public void show() {
        super.show();
        et_nickname.setText("");
        ed_sign.setText("");
    }

    public void hide() {
        super.dismiss();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        setContentView(R.layout.dialog_editmsg);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        ed_sign = (EditText) findViewById(R.id.ed_sign);
        setCanceledOnTouchOutside(false);
        findViewById(R.id.ok).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
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
            case R.id.ok:
                listener.OnEditListener(et_nickname.getText().toString().trim(), ed_sign.getText().toString().trim());
                break;
        }
    }

    private EditMsgDialogListener listener;

    public void setEditMsgDialogListener(EditMsgDialogListener listener) {
        this.listener = listener;
    }

    public interface EditMsgDialogListener {
        public void OnEditListener(String nickname, String sign);
    }
}