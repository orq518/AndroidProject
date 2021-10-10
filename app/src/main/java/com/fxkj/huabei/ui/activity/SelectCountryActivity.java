package com.fxkj.huabei.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxkj.huabei.R;
import com.fxkj.huabei.ui.app.AppActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class SelectCountryActivity extends AppActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_search_country)
    EditText etSearchCountry;
    @BindView(R.id.rv_country_list)
    RecyclerView rvCountryList;
    RecyclerAdapter recyclerAdapter;
    List<String> country = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_country_layout;
    }

    @Override
    protected void initView() {
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvCountryList.setLayoutManager(linearLayoutManager);
        // 用于描述item的适配器
        country.add("USA");
        country.add("CHINA");
        country.add("ENGLI");
        country.add("AUST");
        recyclerAdapter = new RecyclerAdapter(country);
        rvCountryList.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mActivity, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 对编辑框添加文本改变监听，搜索的具体功能在这里实现
         * 很简单，文本该变的时候进行搜索。关键方法是重写的onTextChanged（）方法。
         */
        etSearchCountry.addTextChangedListener(new TextWatcher() {

            /**
             *
             * 编辑框内容改变的时候会执行该方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
                if (recyclerAdapter != null) {
                    recyclerAdapter.onFliter(String.valueOf(s));
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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

    @OnClick(R.id.iv_close)
    public void onClick() {
        finish();
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {
        private List<String> countryList = new ArrayList<>();
        private List<String> fliterList = new ArrayList<>();
        private OnItemClickListener mOnItemClickListener = null;

        public RecyclerAdapter(List<String> data) {
            this.countryList.addAll(data);
            this.fliterList.addAll(data);
        }

        public void onFliter(String s) {
            fliterList.clear();
            if (!TextUtils.isEmpty(s)) {
                for (int i = 0; i < countryList.size(); i++) {
                    String item = countryList.get(i);
                    if (item.toUpperCase().contains(s.toUpperCase())) {
                        fliterList.add(item);
                    }
                }
            } else {
                this.fliterList.addAll(countryList);
            }
            this.notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(v, (int) v.getTag());
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_country_item_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            itemView.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.countryName.setText(fliterList.get(position));
            //将position保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return fliterList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView countryName;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.countryName = itemView.findViewById(R.id.tv_country);
            }

        }

    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}