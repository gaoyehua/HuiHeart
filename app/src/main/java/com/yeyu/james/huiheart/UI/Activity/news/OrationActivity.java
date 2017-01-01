package com.yeyu.james.huiheart.UI.Activity.news;

import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.base.BaseActivity;
import com.yeyu.james.huiheart.adapter.OrationAdapter;
import com.yeyu.james.huiheart.api.HttpUrlPaths;
import com.yeyu.james.huiheart.entity.HomeNewsBean;
import com.yeyu.james.huiheart.widget.FullyLinearLayoutManager;
import com.yeyu.james.huiheart.widget.itemAnimator.SlideInOutLeftItemAnimator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by James on 2016/12/19.
 */

public class OrationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIcBack;
    private RecyclerView mOrationRecyclerView;
    private OrationAdapter mOrationAdapter;
    private List<HomeNewsBean.ResultsBean> mOrationDatas = new ArrayList<>();
    private int page = 1;
    private MaterialRefreshLayout mMaterialRefreshLayout;
    private boolean isLoadMore =true;

    @Override
    protected int getResultId() {
        return R.layout.activity_oration;
    }

    @Override
    protected void initListener() {
        mIcBack.setOnClickListener(this);
    }

    /*
    加载数据
     */
    @Override
    public void initData() {
        super.initData();
        OkGo.post(HttpUrlPaths.SCAN_MORE)
                .params("userid","54442")
                .params("page",page+"")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(()->{

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( s->{
                    Type type=new TypeToken<HomeNewsBean>(){

                    }.getType();
                    HomeNewsBean bean =new Gson().fromJson(s,type);
                    if(bean.getErrorCode() == 0
                            && bean.getErrorStr().equals("sucess")
                            && bean.getResults().size() > 0){
                        mOrationDatas = bean.getResults();
                        mOrationAdapter.addData(mOrationDatas);
                    }
                },throwable -> {
                    //错误代码
                });

    }

    @Override
    public void initView() {
        mIcBack =(ImageView) findViewById(R.id.ic_back);
        mOrationRecyclerView =(RecyclerView) findViewById(R.id.oration_recycler_view);

        SlideInOutLeftItemAnimator animation =new SlideInOutLeftItemAnimator(mOrationRecyclerView);
        mOrationRecyclerView.setItemAnimator(animation);
        mOrationRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mOrationAdapter =new OrationAdapter(this,mOrationDatas);
        mOrationRecyclerView.setAdapter(mOrationAdapter);
        mOrationAdapter.setOnItemClickListener((view, position) -> {
            int index = mOrationDatas.size()-position-1;
            HomeNewsBean.ResultsBean resultsBean = mOrationDatas.get(index);
            Intent intent =new Intent(OrationActivity.this,NewsActivity.class);
            intent.putExtra("id",resultsBean.getId());
            intent.putExtra("userid","54442");
            startActivity(intent);
        });
        //刷新处理
        mMaterialRefreshLayout =(MaterialRefreshLayout) findViewById(R.id.refresh);
        mMaterialRefreshLayout.setLoadMore(isLoadMore);
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //在子线程中处理加载数据
                new Handler().postDelayed(()->{

                    //初始化加载刷新
                    initRefresh(materialRefreshLayout);
                },2000);
            }

            //上拉加载更多
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);

                new Handler().postDelayed(()->{
                    //加载更多数据
                    loadMoreData();
                },2000);
            }
        });

    }
    /*
    加载更多数据
     */
    private void loadMoreData(){
        OkGo.post(HttpUrlPaths.SCAN_MORE)
                .params("userid","54442")
                .params("page",++page)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(()->{

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type =new TypeToken<HomeNewsBean>() {
                    }.getType();
                    HomeNewsBean bean =new Gson().fromJson(s,type);
                    if(bean.getErrorCode() ==0
                            &&bean.getErrorStr().equals("sucess")
                            &&bean.getResults().size() >0){
                        List<HomeNewsBean.ResultsBean> resultsData = bean.getResults();
                        mOrationDatas.addAll(resultsData);
                        mOrationAdapter.addData(mOrationAdapter.getDatas().size(),mOrationDatas);
                        mOrationAdapter.notifyDataSetChanged();
                        mMaterialRefreshLayout.finishRefreshLoadMore();
                    }

                },throwable -> {
                    //错误处理
                });
    }
    /*
    刷新
     */
    private void initRefresh(MaterialRefreshLayout materialRefreshLayout){
        page =1;
        OkGo.post(HttpUrlPaths.SCAN_MORE)
                .params("userid", "54442")
                .params("page", page + "")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<HomeNewsBean>() {
                    }.getType();
                    HomeNewsBean bean = new Gson().fromJson(s, type);
                    if (bean.getErrorCode() == 0
                            && bean.getErrorStr().equals("success")
                            && bean.getResults().size() > 0) {
                        mOrationDatas.clear();
                        mOrationDatas = bean.getResults();
                        mOrationAdapter.addData(mOrationDatas);
                        materialRefreshLayout.finishRefresh();

                    }
                }, throwable -> {
                    //错误处理
                });
    }


    @Override
    public void onClick(View view) {
        if((view.getId()) == R.id.ic_back){
            finish();
        }
    }
}
