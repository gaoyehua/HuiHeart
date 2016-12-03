package com.yeyu.james.huiheart.UI.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.utils.TranslucentUtils;

/**
 * Created by James on 2016/12/3.
 * 基本活動构建，基类
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG =BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getResultId());

        TranslucentUtils.setColor(this, getResources().getColor(R.color.home_bg_title), 1);

        initView();
        initListener();
        initData();

    }

    protected abstract int getResultId();

    public void initData(){

    }

    /*
    初始化Toolbar
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    protected abstract void initListener();
    public abstract void  initView();




}
