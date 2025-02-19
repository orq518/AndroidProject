package com.fxkj.huabei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class RegistActivity extends AppActivity {

    @BindView(R.id.et_name)
    AppCompatEditText etName;
    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist_layout;
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


    @OnClick({R.id.btn_regist, R.id.tv_login_terms_use, R.id.tv_login_privacy_policy, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regist:
                break;
            case R.id.tv_login_terms_use:
                break;
            case R.id.tv_login_privacy_policy:
                break;
            case R.id.tv_login:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
    }
}