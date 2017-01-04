package com.ping.cookbook.module.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ping.cookbook.R;
import com.ping.cookbook.base.BaseActivity;

import ping.Lib.Utils.NetUtil;
import ping.Lib.Utils.ToastUtil;
import ping.Lib.Utils.ToolUtils;



public class FeedBackActivity extends BaseActivity implements OnClickListener {
    private EditText editFeedBack, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }

    private void initView() {
        setToolbarVisible(View.GONE);
        editFeedBack = (EditText) findViewById(R.id.editFeedBack);
        editPhone = (EditText) findViewById(R.id.editPhone);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            String suggestion = editFeedBack.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            if (suggestion != null && suggestion.length() > 0) {
                if (NetUtil.networkEnable()) {
                    if (phone != null && phone.length() > 0 && !ToolUtils.isPhone(phone)) {
                        ToastUtil.showShort("请输入正确的手机号");
                        return;
                    } else {
                        editPhone.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showShort("感谢您的反馈");
                            }
                        }, 1000);
                    }
                } else {
                    ToastUtil.showShort("网络异常，请检测您的网络");
                }
            } else {
                ToastUtil.showShort("请输入您的意见或建议");
            }
        } else if (view.getId() == R.id.iv_back) {
            finish();
        }
    }
}
