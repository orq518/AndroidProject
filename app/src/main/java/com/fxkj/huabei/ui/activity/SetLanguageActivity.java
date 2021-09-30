package com.fxkj.huabei.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;
import com.hjq.language.MultiLanguages;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public final class SetLanguageActivity extends AppActivity {


    @BindView(R.id.iv_endlish_selected)
    ImageView ivEndlishSelected;
    @BindView(R.id.layout_english)
    RelativeLayout layoutEnglish;
    @BindView(R.id.iv_japanese_selected)
    ImageView ivJapaneseSelected;
    @BindView(R.id.layout_japanese)
    RelativeLayout layoutJapanese;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_languages_layout;
    }

    @Override
    protected void initView() {
//        Locale locale = MultiLanguages.getAppLanguage();
//        locale.getCountry();
//        LogUtil.d("locale.getCountry():"+locale.getCountry());
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

    @OnClick({R.id.layout_english, R.id.layout_japanese})
    public void onClick(View view) {
        // 是否需要重启
        boolean restart;
        switch (view.getId()) {
            case R.id.layout_english:
                restart = MultiLanguages.setAppLanguage(this, Locale.ENGLISH);
                ivEndlishSelected.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_japanese:
                restart = MultiLanguages.setAppLanguage(this, Locale.JAPAN);
                ivJapaneseSelected.setVisibility(View.VISIBLE);
                break;
            default:
                restart = false;
                break;
        }
        if (restart) {
            // 我们可以充分运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果
            startActivity(new Intent(this, SplashActivity.class));
            overridePendingTransition(R.anim.right_in_activity, R.anim.right_out_activity);
            finish();
        }
    }
}