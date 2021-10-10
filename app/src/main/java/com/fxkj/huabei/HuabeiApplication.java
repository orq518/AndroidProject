package com.fxkj.huabei;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.facebook.FacebookSdk;
import com.fxkj.huabei.http.glide.GlideApp;
import com.fxkj.huabei.utils.LogUtil;
import com.hjq.bar.TitleBar;
import com.hjq.bar.initializer.LightBarInitializer;
import com.fxkj.huabei.aop.DebugLog;
import com.fxkj.huabei.http.model.RequestHandler;
import com.fxkj.huabei.http.model.RequestServer;
import com.fxkj.huabei.manager.ActivityManager;
import com.fxkj.huabei.other.AppConfig;
import com.fxkj.huabei.other.CrashHandler;
import com.fxkj.huabei.other.DebugLoggerTree;
import com.fxkj.huabei.other.SmartBallPulseFooter;
import com.fxkj.huabei.other.ToastInterceptor;
import com.hjq.http.EasyConfig;
import com.hjq.language.MultiLanguages;
import com.hjq.language.OnLanguageListener;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastBlackStyle;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Locale;

import okhttp3.OkHttpClient;
import timber.log.Timber;

public final class HuabeiApplication extends Application {

    @DebugLog("启动耗时")
    @Override
    public void onCreate() {
        super.onCreate();
        initSdk(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // 清理所有图片内存缓存
        GlideApp.get(this).onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // 根据手机内存剩余情况清理图片内存缓存
        GlideApp.get(this).onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context base) {
        // 绑定语种
        super.attachBaseContext(MultiLanguages.attach(base));
    }

//    //FaceBook  相关 todo fb相关
//    private static void initFacebook(Context context) {
//        FacebookSdk.setApplicationId("xxxxx");
//        FacebookSdk.sdkInitialize(context);
//    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSdk(Application application) {
//        initFacebook(application);
        // 设置调试模式
        XXPermissions.setDebugMode(AppConfig.isDebug());
        // 初始化语种切换框架
        MultiLanguages.init(application);
        // 设置语种变化监听器
        MultiLanguages.setOnLanguageListener(new OnLanguageListener() {

            @Override
            public void onAppLocaleChange(Locale oldLocale, Locale newLocale) {
                LogUtil.d("监听到应用切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale);
            }

            @Override
            public void onSystemLocaleChange(Locale oldLocale, Locale newLocale) {
                LogUtil.d("监听到系统切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale + "，是否跟随系统：" + MultiLanguages.isSystemLanguage());
            }
        });

        // 初始化吐司
        ToastUtils.init(application, new ToastBlackStyle(application) {

            @Override
            public int getCornerRadius() {
                return (int) application.getResources().getDimension(R.dimen.button_round_size);
            }
        });

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor());

        // 设置标题栏初始化器
        TitleBar.setDefaultInitializer(new LightBarInitializer() {

            @Override
            public Drawable getBackgroundDrawable(Context context) {
                return new ColorDrawable(ContextCompat.getColor(application, R.color.common_primary_color));
            }

            @Override
            public Drawable getBackIcon(Context context) {
                return ContextCompat.getDrawable(context, R.drawable.arrows_left_ic);
            }

            @Override
            protected TextView createTextView(Context context) {
                return new AppCompatTextView(context);
            }
        });

        // 本地异常捕捉
        CrashHandler.register(application);

        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, AppConfig.getBuglyId(), AppConfig.isDebug());

        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) ->
                new MaterialHeader(context).setColorSchemeColors(ContextCompat.getColor(context, R.color.common_accent_color)));
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new SmartBallPulseFooter(context));
        // 设置全局初始化器
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            // 刷新头部是否跟随内容偏移
            layout.setEnableHeaderTranslationContent(true)
                    // 刷新尾部是否跟随内容偏移
                    .setEnableFooterTranslationContent(true)
                    // 加载更多是否跟随内容偏移
                    .setEnableFooterFollowWhenNoMoreData(true)
                    // 内容不满一页时是否可以上拉加载更多
                    .setEnableLoadMoreWhenContentNotFull(false)
                    // 仿苹果越界效果开关
                    .setEnableOverScrollDrag(false);
        });

        // Activity 栈管理初始化
        ActivityManager.getInstance().init(application);

        // 网络请求框架初始化
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        EasyConfig.with(okHttpClient)
                // 是否打印日志
                .setLogEnabled(AppConfig.isLogEnable())
                // 设置服务器配置
                .setServer(new RequestServer())
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 添加全局请求参数
                //.addParam("token", "6666666")
                // 添加全局请求头
                //.addHeader("time", "20191030")
                // 启用配置
                .into();

        // 初始化日志打印
        if (AppConfig.isLogEnable()) {
            Timber.plant(new DebugLoggerTree());
        }

        // 注册网络状态变化监听
        ConnectivityManager connectivityManager = ContextCompat.getSystemService(application, ConnectivityManager.class);
        if (connectivityManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
                @Override
                public void onLost(@NonNull Network network) {
                    Activity topActivity = ActivityManager.getInstance().getTopActivity();
                    if (topActivity instanceof LifecycleOwner) {
                        LifecycleOwner lifecycleOwner = ((LifecycleOwner) topActivity);
                        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                            ToastUtils.show(R.string.common_network_error);
                        }
                    }
                }
            });
        }
    }
}