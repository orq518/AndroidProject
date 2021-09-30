package com.fxkj.huabei.utils;

import android.util.Log;

import com.fxkj.huabei.other.AppConfig;

public class LogUtil {


	private static final String TAG = "ou";

	/**
	 * 
	 * Description: 打印默认TAG的日志
	 * 
	 * @param msg
	 *            void
	 */
	public static void d(String msg) {
		if (AppConfig.isLogEnable()) {
			d(TAG, msg);
		}
	}

	public static void d(String tag, String  msg) {
		if (AppConfig.isLogEnable()) {
			Log.i(tag, msg);
		}
	}
}
