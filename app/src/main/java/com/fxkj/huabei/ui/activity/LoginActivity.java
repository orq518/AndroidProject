package com.fxkj.huabei.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class LoginActivity extends AppActivity {


    @BindView(R.id.et_email)
    AppCompatEditText etEmail;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
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

    @OnClick({R.id.tv_forgot_password, R.id.btn_login, R.id.tv_login_terms_use, R.id.tv_login_privacy_policy, R.id.iv_google, R.id.iv_fb, R.id.tv_sign_up})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_password:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_login_terms_use:
                break;
            case R.id.tv_login_privacy_policy:
                break;
            case R.id.iv_google:
                break;
            case R.id.iv_fb:
                break;
            case R.id.tv_sign_up:
                startActivity(new Intent(mActivity, RegistActivity.class));
                break;
        }
    }


}