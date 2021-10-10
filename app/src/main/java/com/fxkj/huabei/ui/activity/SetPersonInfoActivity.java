package com.fxkj.huabei.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class SetPersonInfoActivity extends AppActivity {


    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_set_ski)
    TextView tvSetSki;
    @BindView(R.id.tv_set_snowboard)
    TextView tvSetSnowboard;
    @BindView(R.id.tv_set_female)
    TextView tvSetFemale;
    @BindView(R.id.tv_set_male)
    TextView tvSetMale;
    @BindView(R.id.tv_set_select_country)
    TextView tvSetSelectCountry;
    @BindView(R.id.btn_finish)
    AppCompatButton btnFinish;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_person_info_layout;
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
        super.onDestroy();
    }


    @OnClick({R.id.iv_header, R.id.tv_set_ski, R.id.tv_set_snowboard, R.id.tv_set_female, R.id.tv_set_male, R.id.tv_set_select_country, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                break;
            case R.id.tv_set_ski:
                break;
            case R.id.tv_set_snowboard:
                break;
            case R.id.tv_set_female:
                break;
            case R.id.tv_set_male:
                break;
            case R.id.tv_set_select_country:
                startActivity(new Intent(mActivity, SelectCountryActivity.class));
                break;
            case R.id.btn_finish:
                break;
        }
    }
}