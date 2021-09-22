package com.fxkj.huabei.ui.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;
import com.fxkj.huabei.ui.fragment.MainMenuLeftFragment;
import com.fxkj.huabei.manager.ActivityManager;
import com.fxkj.huabei.other.DoubleClickHelper;
import com.nineoldandroids.view.ViewHelper;

public class HomeActivity extends AppActivity {

    /**
     * 导航栏左侧的用户图标
     */
    private ImageView nav_userImg;

    /**
     * 导航栏左侧的侧边栏的父容器
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 导航栏左侧的侧边栏碎片界面
     */
    private MainMenuLeftFragment leftMenuFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.hb_home_activity;
    }

    @Override
    protected void initView() {
        nav_userImg = (ImageView) findViewById(R.id.nav_user);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        //关闭手势滑动：DrawerLayout.LOCK_MODE_LOCKED_CLOSED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);

        leftMenuFragment = (MainMenuLeftFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_leftmenu);
        initEvent();
    }

    @Override
    protected void initData() {

    }

    private void initEvent() {
        //用户图标的点击事件
        nav_userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLeftMenu();
            }
        });

        //侧边栏的事件监听
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置-0），STATE_DRAGGING（拖拽-1），STATE_SETTLING（固定-2）中之一。
             */
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * slideOffset 表示 滑动的幅度(0-1)
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.w("onDrawerSlide", "slideOffset=" + slideOffset);//0.0 -- 0.56 -- 1.0

                View mContent = mDrawerLayout.getChildAt(0);//内容区域view
                View mMenu = drawerView;

                float scale = 1 - slideOffset;

                if (drawerView.getTag().equals("LEFT")) {//左侧的侧边栏动画效果
                    //设置左侧区域的透明度0.6f + 0.4f * (0.0 ... 1.0)【也就是打开的时候透明度从0.6f ... 1.0f，关闭的时候反之】
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * slideOffset);
                    //移动内容区域：左侧侧边栏宽度 * (0.0 ... 1.0)【也就是打开的时候，内容区域移动从0 ... 左侧侧边栏宽度】
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * slideOffset);
                    mContent.invalidate();//重绘view

                } else {//右侧的侧边栏动画效果
                    //移动内容区域：-右侧侧边栏宽度 * (0.0 ... 1.0)【也就是打开的时候，内容区域移动从-0 ... -左侧侧边栏宽度】
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    mContent.invalidate();
                }

            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView.getTag().equals("LEFT")) {//如果感觉显示有延迟的话，可以放到nav_userImg的点击事件监听中执行
                    leftMenuFragment.setDefaultDatas();//打开的时候初始化默认数据【比如：请求网络，获取数据】
                }
            }

            /**
             * 当一个抽屉被完全关闭的时候被调用
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                //关闭手势滑动：DrawerLayout.LOCK_MODE_LOCKED_CLOSED（Gravity.LEFT：代表左侧的）
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
            }
        });
    }

    /**
     * 打开左侧的侧边栏
     */
    public void OpenLeftMenu() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
        //打开手势滑动：DrawerLayout.LOCK_MODE_UNLOCKED（Gravity.LEFT：代表左侧的）
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
    }
    @Override
    public void onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            toast(R.string.home_exit_hint);
            return;
        }

        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false);
        postDelayed(() -> {
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities();
            // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
            // System.exit(0);
        }, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
