package com.fxkj.huabei.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ResetPswActivity extends AppActivity {


    @BindView(R.id.et_new_password)
    AppCompatEditText etNewPassword;
    @BindView(R.id.et_confirm_password)
    AppCompatEditText etConfirmPassword;
    @BindView(R.id.btn_change_password)
    AppCompatButton btnChangePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_psw_layout;
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

    @OnClick({R.id.et_new_password, R.id.et_confirm_password, R.id.btn_change_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_new_password:
                break;
            case R.id.et_confirm_password:
                break;
            case R.id.btn_change_password:
                break;
        }
    }
}