package com.fxkj.huabei.aop;

import android.app.Activity;

import com.fxkj.huabei.manager.ActivityManager;
import com.fxkj.huabei.other.PermissionCallback;
import com.hjq.permissions.XXPermissions;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 *    time   : 2019/12/06
 *    desc   : 权限申请切面
 */
@Aspect
public class PermissionsAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.fxkj.huabei.aop.Permissions * *(..))")
    public void method() {}

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(permissions)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permissions permissions) {
        Activity activity = ActivityManager.getInstance().getTopActivity();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        XXPermissions.with(activity)
                .permission(permissions.value())
                .request(new PermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            try {
                                // 获得权限，执行原方法
                                joinPoint.proceed();
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}