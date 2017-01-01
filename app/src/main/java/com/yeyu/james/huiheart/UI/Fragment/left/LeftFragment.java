package com.yeyu.james.huiheart.UI.Fragment.left;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yeyu.james.huiheart.Contant.Contants;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.Activity.Main.MainActivity;
import com.yeyu.james.huiheart.UI.Activity.left.ContactActivity;
import com.yeyu.james.huiheart.UI.Activity.left.FollowActivity;
import com.yeyu.james.huiheart.UI.Activity.left.GiftActivity;
import com.yeyu.james.huiheart.UI.Activity.left.MoneyActivity;
import com.yeyu.james.huiheart.UI.Activity.login.LoginActivity;
import com.yeyu.james.huiheart.UI.Activity.left.MessageActivity;
import com.yeyu.james.huiheart.UI.Activity.left.ReserveActivity;
import com.yeyu.james.huiheart.UI.Activity.user.UserActivity;
import com.yeyu.james.huiheart.UI.Activity.left.WorryActivity;
import com.yeyu.james.huiheart.UI.Activity.left.setting.SettingActivity;
import com.yeyu.james.huiheart.entity.MyUser;
import com.yeyu.james.huiheart.event.LoginOut;
import com.yeyu.james.huiheart.utils.PreferencesUtils;
import com.yeyu.james.huiheart.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by James on 2016/12/8.
 */

public class LeftFragment extends Fragment implements
        View.OnClickListener {

    //控件初始化
    RelativeLayout mReLogin;
    LinearLayout mLiReserve;
    LinearLayout mLiFollow;

    LinearLayout mLiContact;

    LinearLayout mLiMoney;

    LinearLayout mLiGift;

    LinearLayout mLiWorry;

    RelativeLayout mReSetting;

    private TextView mTvName, mBtLogin;
    private ImageView mIvTour;

    @Override
    public void onResume() {
        super.onResume();

        boolean isLogin = PreferencesUtils.getBoolean(getContext(), Contants.IS_LOGIN);
        if(isLogin){
            String name=PreferencesUtils.getString(getContext(),Contants.USER_NAME);
            //initname(name)
        }else {
            //loginoutUI();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_left,container,false);
        EventBus.getDefault().register(this);
        initView(view);
        return view;
    }

    /*
    初始化布局文件
     */
    private void initView(View view){
        mReLogin = (RelativeLayout) view.findViewById(R.id.rl_login);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvName.setClickable(false);
        mBtLogin = (TextView) view.findViewById(R.id.tv_login);
        mBtLogin.setOnClickListener(this);
        mIvTour = (ImageView) view.findViewById(R.id.img_avatar);
        mIvTour.setClickable(false);
        mReLogin.setOnClickListener(this);
        mLiReserve = (LinearLayout) view.findViewById(R.id.my_message);
        mLiReserve.setOnClickListener(this);
        mLiFollow = (LinearLayout) view.findViewById(R.id.my_follow);
        mLiFollow.setOnClickListener(this);
        mLiContact = (LinearLayout) view.findViewById(R.id.my_contact);
        mLiContact.setOnClickListener(this);
        mLiMoney = (LinearLayout) view.findViewById(R.id.my_money);
        mLiMoney.setOnClickListener(this);
        mLiGift = (LinearLayout) view.findViewById(R.id.my_gift);
        mLiGift.setOnClickListener(this);
        mLiWorry = (LinearLayout) view.findViewById(R.id.my_worry);
        mLiWorry.setOnClickListener(this);
        mReSetting = (RelativeLayout) view.findViewById(R.id.re_setting);
        mReSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent();
        boolean isToLogin=false;
        switch (view.getId()){
            case R.id.rl_login:
                intent.setClass(getActivity(), LoginActivity.class);
                break;
            case R.id.my_message:
                intent.setClass(getContext(), MessageActivity.class);
                break;
            case R.id.my_contact:
                intent.setClass(getContext(), ContactActivity.class);
                break;
            case R.id.my_gift:
                intent.setClass(getContext(),GiftActivity.class);
                break;
            case R.id.my_follow:
                intent.setClass(getContext(),FollowActivity.class);
                break;
            case R.id.my_money:
                intent.setClass(getContext(),MoneyActivity.class);
                break;
            case R.id.my_reserve:
                intent.setClass(getContext(),ReserveActivity.class);
                break;
            case R.id.my_worry:
                intent.setClass(getContext(),WorryActivity.class);
                break;
            case R.id.re_setting:
                intent.setClass(getContext(),SettingActivity.class);
                break;
            case R.id.tv_login:
                //退出登录
                if(mBtLogin.getText().equals(getContext().getResources()
                        .getString(R.string.login_out))){
                    loginOutUI();
                    MyUser.logOut();
                    PreferencesUtils.putString(getActivity(),
                            Contants.USER_NAME, "");
                    PreferencesUtils.putString(getContext(),
                            Contants.USER_PASSWORD, "");
                    mTvName.setText(getActivity().getResources().getString(R.string.login_in));
                    EventBus.getDefault().post(new LoginOut(true));
                    isToLogin = true;
                } else if (mBtLogin.getText().equals(getContext().getResources()
                        .getString(R.string.login_in))) {
                    intent.setClass(getActivity(), LoginActivity.class);
                }

                break;
            case R.id.img_avatar:
            case R.id.tv_name:
                if (mBtLogin.getText().equals(getContext().getResources()
                        .getString(R.string.login_in))) {
                    intent.setClass(getActivity(), LoginActivity.class);
                } else if (mBtLogin.getText().equals(getContext().getResources()
                        .getString(R.string.login_out))) {
                    intent.setClass(getActivity(), UserActivity.class);
                }
                break;
        }
        ((MainActivity) getActivity())
                .getDragLayout()
                .closeDrawer(GravityCompat.START);
        if (!isToLogin) {
            startActivity(intent);
            //  getActivity().finish();
        }

    }

    private void loginOutUI(){
        PreferencesUtils.putBoolean(getContext(), Contants.IS_LOGIN, false);
        mReLogin.setClickable(true);
        if (mTvName != null) {
            mTvName.setText("");
        }
        if (mBtLogin != null) {
            String loginOut = getContext().getResources()
                    .getString(R.string.login_in);
            mBtLogin.setText(loginOut);
        }
        if (mIvTour != null) {
            Glide.with(getContext()).load(R.drawable.login_out)
                    .asBitmap().transform(new GlideCircleTransform(getContext()))
                    .into(mIvTour);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
