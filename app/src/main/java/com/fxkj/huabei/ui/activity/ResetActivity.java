package com.fxkj.huabei.ui.activity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ResetActivity extends AppActivity {


    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_layout;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Deprecated
    @Override
    protected void onDestroy() {
        // 因为修复了一个启动页被重复启动的问题，所以有可能 Activity 还没有初始化完成就已经销毁了
        // 所以如果需要在此处释放对象资源需要先对这个对象进行判空，否则可能会导致空指针异常
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
    }
}