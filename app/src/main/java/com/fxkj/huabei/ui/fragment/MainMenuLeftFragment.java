package com.fxkj.huabei.ui.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.activity.HomeActivity;
import com.fxkj.huabei.ui.activity.LoginActivity;
import com.fxkj.huabei.ui.activity.SetLanguageActivity;
import com.fxkj.huabei.utils.LogUtil;
import com.hjq.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Used 首页左侧侧边栏碎片界面
 */

public class MainMenuLeftFragment extends BaseFragment {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_preference)
    TextView tvPreference;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.tv_system_permission)
    TextView tvSystemPermission;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_terms_service)
    TextView tvTermsService;
    @BindView(R.id.tv_contact_us)
    TextView tvContactUs;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    HomeActivity homeActivity;

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("onResume");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_persion_info_layout;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        homeActivity = (HomeActivity) mActivity;
        LogUtil.d("ivPhoto:" + ivPhoto);
//        tvName.setText("222");
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化默认数据【这个需要在activity中执行，原因是：在布局文件中通过<fragment>的方式引用Fragment，打开Activity的时候，Fragment的生命周期函数均执行了】
     * 那么就存在一个问题，初始化fragment中的数据，可能会在activity数据初始化之前执行
     */
    public void setDefaultDatas() {
        LogUtil.d("");
//		tv_show.setText(tv_show.getText() + "\n执行了一次setDefaultDatas()");
    }

    @OnClick({R.id.tv_logout, R.id.iv_photo, R.id.tv_preference, R.id.tv_change_password, R.id.tv_system_permission, R.id.tv_language, R.id.tv_terms_service, R.id.tv_contact_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                break;
            case R.id.tv_preference:
                break;
            case R.id.tv_change_password:
                break;
            case R.id.tv_system_permission:
                break;
            case R.id.tv_language:
                startActivity(new Intent(mActivity, SetLanguageActivity.class));
                break;
            case R.id.tv_terms_service:
                break;
            case R.id.tv_contact_us:
                break;
            case R.id.tv_logout:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
        homeActivity.closeDrawer(Gravity.LEFT);
    }

}
