package com.yeyu.james.huiheart.UI.Fragment.Home;


import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DisplayContext;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.sunfusheng.marqueeview.MarqueeView;
import com.yeyu.james.huiheart.Contant.Contants;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.Activity.Main.BannerDetailActivity;
import com.yeyu.james.huiheart.UI.Activity.Main.MainActivity;
import com.yeyu.james.huiheart.UI.Activity.news.NewsActivity;
import com.yeyu.james.huiheart.UI.Activity.news.OrationActivity;
import com.yeyu.james.huiheart.UI.Activity.user.UserDetailActivity;
import com.yeyu.james.huiheart.adapter.OrationAdapter;
import com.yeyu.james.huiheart.api.HttpUrlPaths;
import com.yeyu.james.huiheart.entity.AdvisoryBean;
import com.yeyu.james.huiheart.entity.HomeBannerBean;
import com.yeyu.james.huiheart.entity.HomeBannerOne;
import com.yeyu.james.huiheart.entity.HomeBannerTwo;
import com.yeyu.james.huiheart.entity.HomeNewsBean;
import com.yeyu.james.huiheart.entity.MarqueeBean;
import com.yeyu.james.huiheart.utils.NetUtils;
import com.yeyu.james.huiheart.widget.CircleImageView;
import com.yeyu.james.huiheart.widget.SlideBanner;
import com.yeyu.james.huiheart.widget.dialog.CustomPrograss;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by James on 2016/12/8.
 */

public class HomeFragment extends Fragment implements
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    ScrollView mScrollView;


    CircleImageView mAvatorImg;
    TextView mCustomerServiceTv;
    SlideBanner mFlyBanner;
    ImageView mImgTall;
    RelativeLayout mMyReTalk;
    ImageView mImgContact;
    RelativeLayout mMyReCall;
    ImageView mImgFree;
    RelativeLayout mMyReFree;
    ImageView mImgExam;
    RelativeLayout mMyReExam;
    ImageView mMarqueeImg;
    MarqueeView mMarqueeView;
    TextView mRecommentMasterTv;


    ImageView mImgOne;
    TextView mTvNameOne;
    TextView mTvLevelOne;
    TextView mTvDesOne;
    TextView mTvNameSecond;
    TextView mTvLevelTwo;
    TextView mTvDesTwo;
    ImageView mImgTwo;
    ImageView mImgThree;
    TextView mTvNameThree;
    TextView mTvDesThree;
    ImageView mImgFour;
    TextView mTvNameFour;
    TextView mTvDesFour;
    ImageView mRecommentImgOne;
    TextView mTvTitleRecomment;
    ImageView mImageView;
    TextView mTvDesTrecomment;
    ImageView mHotImg;
    RecyclerView mHotRecyclerView;
    RelativeLayout mReLookMore;
    private OrationAdapter mHomeNewAdapter;
    SwipeRefreshLayout mRefreshLayout;

    LinearLayout mLlOne, mLlTwo, mLlThree, mLlFour;

    List<HomeBannerBean.ResultsBean> mHomeBanner = new ArrayList<>();
    List<MarqueeBean.ResultsBean> mResultBean =new ArrayList<>();
    List<AdvisoryBean.ResultsBean> mAdvisoryBean = new ArrayList<>();
    List<HomeNewsBean.ResultsBean> mHomeNewsData = new ArrayList<>();
    /**
      初始化布局控件
     */
    private void initView(View view) {
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mAvatorImg = (CircleImageView) view.findViewById(R.id.avator_img);
        mCustomerServiceTv = (TextView) view.findViewById(R.id.customer_service_tv);
        mFlyBanner = (SlideBanner) view.findViewById(R.id.fly_banner);
        mImgTall = (ImageView) view.findViewById(R.id.img_tall);
        mMyReTalk = (RelativeLayout) view.findViewById(R.id.my_re_talk);
        mImgContact = (ImageView) view.findViewById(R.id.img_contact);
        mMyReCall = (RelativeLayout) view.findViewById(R.id.my_re_call);
        mImgFree = (ImageView) view.findViewById(R.id.img_free);
        mMyReFree = (RelativeLayout) view.findViewById(R.id.my_re_free);
        mImgExam = (ImageView) view.findViewById(R.id.img_exam);
        mMyReExam = (RelativeLayout) view.findViewById(R.id.my_re_exam);
        mMarqueeImg = (ImageView) view.findViewById(R.id.marquee_img);
        mMarqueeView = (MarqueeView) view.findViewById(R.id.marquee_view);
        mRecommentMasterTv = (TextView) view.findViewById(R.id.recomment_master_tv);
        mImgOne = (ImageView) view.findViewById(R.id.img_one);
        mTvNameOne = (TextView) view.findViewById(R.id.tv_name_one);
        mTvLevelOne = (TextView) view.findViewById(R.id.tv_level_one);
        mTvDesOne = (TextView) view.findViewById(R.id.tv_des_one);
        mTvNameSecond = (TextView) view.findViewById(R.id.tv_name_second);
        mTvLevelTwo = (TextView) view.findViewById(R.id.tv_level_two);
        mTvDesTwo = (TextView) view.findViewById(R.id.tv_des_two);
        mImgTwo = (ImageView) view.findViewById(R.id.img_two);
        mImgThree = (ImageView) view.findViewById(R.id.img_three);
        mTvNameThree = (TextView) view.findViewById(R.id.tv_name_three);
        mTvDesThree = (TextView) view.findViewById(R.id.tv_des_three);
        mTvNameFour = (TextView) view.findViewById(R.id.tv_name_four);
        mTvDesFour = (TextView) view.findViewById(R.id.tv_des_four);
        mTvTitleRecomment = (TextView) view.findViewById(R.id.tv_title_recomment);
        mTvDesTrecomment = (TextView) view.findViewById(R.id.tv_des_trecomment);
        mImgFour = (ImageView) view.findViewById(R.id.img_four);
        mRecommentImgOne = (ImageView) view.findViewById(R.id.recomment_img_one);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        mHotImg = (ImageView) view.findViewById(R.id.hot_img);
        mHotRecyclerView = (RecyclerView) view.findViewById(R.id.hot_recycler_view);
        mReLookMore = (RelativeLayout) view.findViewById(R.id.re_look_more);
        mHotRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mHotRecyclerView.setLayoutManager(layoutManager);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        mRefreshLayout.setOnRefreshListener(this);

        mLlOne = (LinearLayout) view.findViewById(R.id.ll_adversory_one);
        mLlTwo = (LinearLayout) view.findViewById(R.id.ll_adversory_two);

        mLlThree = (LinearLayout) view.findViewById(R.id.ll_adversory_three);

        mLlFour = (LinearLayout) view.findViewById(R.id.ll_adversory_four);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        EventBus.getDefault().register(this);

        initView(view);
        initListener();
        boolean online = NetUtils.isOnline(getActivity());
        // 判断是否有网络，提醒用户网络状态
        if(online){
            CustomPrograss.show(getActivity(),getResources()
                    .getString(R.string.loading),false,null);
            //进入界面，显示轮播图
            mScrollView.smoothScrollTo(0,0);
            initBanner();
            initMarqueeData();
            initAdvisoryData();
            initRecommentOne();
            initRecommentTwo();
            initData();
        }else {

        }
        return view;
    }

    /*
    请求关于心理信息资讯数据
     */
    private void initData(){
        OkGo.post(HttpUrlPaths.SCAN_MORE)
                .params("userid", "54442")
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
                        mHomeNewsData = bean.getResults();
                        CustomPrograss.disMiss();
                        mHomeNewAdapter = new OrationAdapter(getContext(), mHomeNewsData);
                        mHotRecyclerView.setAdapter(mHomeNewAdapter);
                        mHomeNewAdapter.setOnItemClickListener((view, position) -> {
                            HomeNewsBean.ResultsBean resultsBean = mHomeNewsData.get(position);
                            Intent intent = new Intent(getActivity(), NewsActivity.class);
                            intent.putExtra("id", resultsBean.getId());
                            intent.putExtra("userid", "54442");
                            startActivity(intent);
                        });
                    }
                }, throwable -> {

                });
    }
    /*
    请求每日星座测试数据
     */
    private void initRecommentTwo(){
        OkGo.post(HttpUrlPaths.HOME_RECOMMENT_TWO_BANNER)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<HomeBannerTwo>() {
                    }.getType();
                    HomeBannerTwo homeBannerTwo = new Gson().fromJson(s, type);
                    if (homeBannerTwo.getErrorStr().equals("success")
                            && homeBannerTwo.getErrorCode() == 0) {
                        Glide.with(getContext())
                                .load(homeBannerTwo.getResults().getImage())
                                .asBitmap()
                                .into(mImageView);

                        mTvDesTrecomment.setText(homeBannerTwo.getResults().getTitle());
                    }
                }, throwable -> {

                });
    }
    /*
    请求活动讲座信息数据
     */
    private void initRecommentOne(){
        OkGo.post(HttpUrlPaths.HOME_RECOMMENT_ONE_BANNER)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(() -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type = new TypeToken<HomeBannerOne>() {
                    }.getType();
                    HomeBannerOne homeBannerOne = new Gson().fromJson(s, type);
                    if (homeBannerOne.getErrorStr().equals("success")
                            && homeBannerOne.getErrorCode() == 0) {
                        Glide.with(getContext())
                                .load(homeBannerOne.getResults().getBanner())
                                .asBitmap()
                                .into(mRecommentImgOne);
                    }
                }, throwable -> {

                });

    }
    /*
    请求推荐咨询师数据
     */
    private void initAdvisoryData(){
        OkGo.post(HttpUrlPaths.HOME_RECOMMENT)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(() ->{
                    //显示dailog
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    Type type =new TypeToken<AdvisoryBean>(){

                    }.getType();
                    AdvisoryBean bean =new Gson().fromJson(s,type);
                    if(bean !=null&&
                            bean.getErrorStr().equals("success")&&
                            bean.getErrorCode()==0){
                        mAdvisoryBean =bean.getResults();
                    }
                    if(mAdvisoryBean !=null &&mAdvisoryBean.size()>0){
                        initAdvisory(mAdvisoryBean);
                    }
                },throwable -> {
                    //执行异常代码
                });

    }
    private void initAdvisory(List<AdvisoryBean.ResultsBean> bean){
        AdvisoryBean.ResultsBean
                one =new AdvisoryBean.ResultsBean(),
                two =new AdvisoryBean.ResultsBean(),
                three =new AdvisoryBean.ResultsBean(),
                four =new AdvisoryBean.ResultsBean();
        for(int i=0; i<bean.size();i++){
            switch (i){
                case 0:
                    one =bean.get(i);
                    break;
                case 1:
                    two =bean.get(i);
                    break;
                case 2:
                    three =bean.get(i);
                    break;
                case 3:
                    four =bean.get(i);
                    break;
                default:
                    break;
            }
        }

        //图片加载机制
        Glide.with(getContext()).load(one.getAvatar()).asBitmap().into(mImgOne);
        mTvNameOne.setText(one.getNickname());
        mTvLevelOne.setText(one.getQualification());
        mTvDesOne.setText(one.getTitle());

        Glide.with(getContext()).load(two.getAvatar()).asBitmap().into(mImgTwo);
        mTvNameSecond.setText(two.getNickname());
        mTvLevelTwo.setText(two.getQualification());
        mTvDesTwo.setText(two.getTitle());

        Glide.with(getContext()).load(three.getAvatar()).asBitmap().into(mImgThree);
        mTvNameThree.setText(three.getNickname());
        mTvDesThree.setText(three.getQualification());

        Glide.with(getContext()).load(four.getAvatar()).asBitmap().into(mImgFour);
        mTvNameFour.setText(four.getNickname());
        mTvDesFour.setText(four.getQualification());
        //页面跳转
        ClickListener(mLlOne,one);
        ClickListener(mLlTwo,two);
        ClickListener(mLlThree,three);
        ClickListener(mLlFour,four);

    }
    private void ClickListener(LinearLayout mLl,AdvisoryBean.ResultsBean finals){
        mLl.setOnClickListener(view -> {
            String userid =finals.getUserid();
            Intent intent=new Intent(getActivity(), UserDetailActivity.class);
            intent.putExtra(Contants.USER_ID,userid);
            intent.putExtra(Contants.C_USER_ID,userid);
            startActivity(intent);
        });
    }

    /*
    请求跑马灯数据
     */
    private void initMarqueeData(){
        OkGo.post(HttpUrlPaths.HOME_MARQUEE)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(()->{
                    //显示dailog
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Type type =new TypeToken<MarqueeBean>(){

                    }.getType();
                    MarqueeBean bean =new Gson().fromJson(s,type);
                    if(bean.getErrorCode()==0 &&
                            bean.getErrorStr().equals("success")){
                        mResultBean =bean.getResults();
                    }
                    if(mResultBean != null && mResultBean.size()>0){
                        List<String> information =new ArrayList<>();
                        for(MarqueeBean.ResultsBean resultsBean : mResultBean){
                            information.add(resultsBean.getLabel());
                        }
                        mMarqueeView.startWithList(information);
                    }
                },throwable -> {
                    //执行错误异常
                });

    }
    /*
    请求首页轮播图的数据
     */
    private void initBanner(){
        OkGo.post(HttpUrlPaths.HOME_BANNER)
                .params("type","consultant")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(()->{

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( s ->{
                    Type type =new TypeToken<HomeBannerBean>(){

                    }.getType();
                    HomeBannerBean bean =new Gson().fromJson(s,type);
                    if(bean.getErrorStr().equals("success")
                            && bean.getErrorCode() ==0
                            && bean.getResultCount()>0 ){
                        mHomeBanner= bean.getResults();
                        List<String> imgBanner =new ArrayList<>();
                        for(HomeBannerBean.ResultsBean resultsBean : mHomeBanner){
                            imgBanner.add(resultsBean.getPic());
                        }
                        mFlyBanner.setImagesUrl(imgBanner);
                        mFlyBanner.setOnItemClickListener(position -> {
                            //根据写入条目位置进行跳转
                            HomeBannerBean.ResultsBean resultsBean;
                            resultsBean=mHomeBanner.get(position);

                            Intent intent =new Intent(getContext(), BannerDetailActivity.class);
                            intent.putExtra("result",resultsBean);
                            startActivity(intent);
                        });
                    }
                }, throwable -> {

                });

    }

    /*
    设置事件监听
     */
    private void initListener(){
        mAvatorImg.setOnClickListener(this);
        mReLookMore.setOnClickListener(this);
        mCustomerServiceTv.setOnClickListener(this);
    }

    /*
    发送消息
     */
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHomeBanner = (List<HomeBannerBean.ResultsBean>) msg.obj;
            List<String> imgBanner = new ArrayList<>();
            for (HomeBannerBean.ResultsBean resultsBean : mHomeBanner) {
                imgBanner.add(resultsBean.getPic());
            }
            mFlyBanner.setImagesUrl(imgBanner);
            mFlyBanner.setOnItemClickListener(position -> {
                mHomeBanner.get(position);  //  根据这个来进行跳转
            });

        }
    };


    @Override
    public void onRefresh() {
        new android.os.Handler().postDelayed(() -> {
            refreshData();
            mRefreshLayout.setRefreshing(false);
        }, 3000);
    }

    private void refreshData(){
        mHomeBanner.clear();//清除数据
        initBanner();//再次请求
        mHomeNewsData.clear();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.re_look_more:
                Intent intent = new Intent(getActivity(), OrationActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.avator_img:
                ((MainActivity) getActivity())
                        .getDragLayout()
                        .openDrawer(GravityCompat.START);
                break;

            case R.id.customer_service_tv:
                new MaterialDialog.Builder(getActivity())
                        .title(getActivity().getResources().getString(R.string.notification))
                        .content(getActivity().getResources().getString(R.string.notification_content))
                        .positiveText(getActivity().getResources().getString(R.string.ok))
                        .onPositive((dialog, which) -> {

                        }).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
