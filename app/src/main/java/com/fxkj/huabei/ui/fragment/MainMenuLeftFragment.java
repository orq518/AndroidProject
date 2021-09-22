package com.fxkj.huabei.ui.fragment;

import android.util.Log;
import android.widget.TextView;

import com.fxkj.huabei.R;
import com.hjq.base.BaseFragment;

/**
 * Used 首页左侧侧边栏碎片界面
 */

public class MainMenuLeftFragment extends BaseFragment {
	private static final String TAG = "MainMenuLeftFragment";
	//
	private TextView tv_show;


	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG,"onResume");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_home_left_menu;
	}

	/**初始化控件*/
	@Override
	protected void initView(){
		Log.d(TAG,"initView");
		tv_show = (TextView) findViewById(R.id.tv_show);
	}

	@Override
	protected void initData() {

	}

	/**初始化默认数据【这个需要在activity中执行，原因是：在布局文件中通过<fragment>的方式引用Fragment，打开Activity的时候，Fragment的生命周期函数均执行了】
	 * 那么就存在一个问题，初始化fragment中的数据，可能会在activity数据初始化之前执行*/
	public void setDefaultDatas(){
		tv_show.setText(tv_show.getText() + "\n执行了一次setDefaultDatas()");
	}
}
