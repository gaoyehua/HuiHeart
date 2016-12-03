package com.yeyu.james.huiheart.UI.Activity.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.yeyu.james.huiheart.Contant.Contants;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.Activity.BaseActivity;
import com.yeyu.james.huiheart.UI.Activity.Main.MainActivity;
import com.yeyu.james.huiheart.UI.Activity.guide.GuideActivity;
import com.yeyu.james.huiheart.entity.MyUser;
import com.yeyu.james.huiheart.event.LoginEvent;
import com.yeyu.james.huiheart.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import okhttp3.Call;
import okhttp3.Response;

//引导页
public class SplashActivity extends BaseActivity {

    //http://icon.xinliji.me/start_img_1080.jpg
    private String splash_img_url = "http://icon.xinliji.me/start_img_1080.jpg";

    private RelativeLayout mReBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected int getResultId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void initView() {

        /*
        全屏显示
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final View target =findViewById(R.id.activity_splash);

        //请求数据
        OkGo.post(splash_img_url)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        target.setBackgroundDrawable(new BitmapDrawable(bitmap));
                    }
                });

        requestBitmap();

        /*
        执行动画
         */
        ObjectAnimator animator =ObjectAnimator.ofFloat(target,"alpha",0.0f,1.0f);
        animator.setDuration(2000);//动画执行时间
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                SharedPreferences sp =getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean(Contants.IS_FIRST_RUNNING,true);

                final String userName = PreferencesUtils.getString(SplashActivity.this,
                        Contants.USER_NAME);
                String userPassword =PreferencesUtils.getString(SplashActivity.this,
                        Contants.USER_PASSWORD);

                BmobUser.loginByAccount(userName, userPassword, new LogInListener<MyUser>() {

                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if(myUser !=null){
                            Log.i("smile","用户登录成功！");
                            EventBus.getDefault().post(new LoginEvent(myUser));
                            PreferencesUtils.putBoolean(SplashActivity.this,
                                    Contants.IS_LOGIN,true);
                        }
                    }
                });

                Intent intent =new Intent();
                if(isFirst){
                    sp.edit().putBoolean(Contants.IS_FIRST_RUNNING,false).commit();
                    intent.setClass(SplashActivity.this, GuideActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();

            }
        });
    }

    private Bitmap requestBitmap() {

        Bitmap splashBitmap = null ;
        return splashBitmap;

    }
}
