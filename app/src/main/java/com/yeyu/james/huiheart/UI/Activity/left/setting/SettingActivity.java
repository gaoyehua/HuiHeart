package com.yeyu.james.huiheart.UI.Activity.left.setting;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yeyu.james.huiheart.Contant.Contants;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.Activity.BaseActivity;
import com.yeyu.james.huiheart.UI.Activity.login.LoginActivity;
import com.yeyu.james.huiheart.UI.Activity.password.ChangePasswordActivity;
import com.yeyu.james.huiheart.entity.MyUser;
import com.yeyu.james.huiheart.utils.DataCleanManager;

/**
 * 设置界面
 * Created by James on 2016/12/8.
 */

public class SettingActivity extends BaseActivity implements
        View.OnClickListener {

    private ImageView mIvBack;
    private TextView mCurrentCache;
    private LinearLayout mClearCache,
            mSettingOpinion,
            mSettingShare,
            mVersionCheck,
            mSettingByMe,
            mUpdatePassword;
    private WebView mWebView;

    private CheckBox mCheckboxIsShareHomepage;
    private String mTotalCacheSize;
    private TextView mCurVerName;
    private MyUser mMyUser;
    @Override
    protected int getResultId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initListener() {
        //设置点击事件
        mIvBack.setOnClickListener(this);
        // mCurrentCache.setOnClickListener(this);
        mSettingOpinion.setOnClickListener(this);
        mSettingShare.setOnClickListener(this);
        mVersionCheck.setOnClickListener(this);
        mClearCache.setOnClickListener(this);
        mSettingByMe.setOnClickListener(this);
        mCheckboxIsShareHomepage.setOnClickListener(this);
        mUpdatePassword.setOnClickListener(this);
    }

    @Override
    public void initView() {
        //初始化控件
        mMyUser = MyUser.getCurrentUser(MyUser.class);
        mIvBack = (ImageView) findViewById(R.id.img_back);
        mCurrentCache = (TextView) findViewById(R.id.current_cache);
        mSettingOpinion = (LinearLayout) findViewById(R.id.setting_opinion);
        mSettingShare = (LinearLayout) findViewById(R.id.setting_share);
        mVersionCheck = (LinearLayout) findViewById(R.id.version_check);
        mClearCache = (LinearLayout) findViewById(R.id.clear_cache);
        mSettingByMe = (LinearLayout) findViewById(R.id.setting_byme);
        mCheckboxIsShareHomepage = (CheckBox) findViewById(R.id.checkbox_isshare_homepage);
        mCurVerName = (TextView) findViewById(R.id.version_name);
        mUpdatePassword = (LinearLayout) findViewById(R.id.goupdate_password);
    }

    @Override
    public void initData() {
        super.initData();
        try{
            mTotalCacheSize = DataCleanManager.getTotalCacheSize(this);
            if(mTotalCacheSize!=null){
                mCurrentCache.setText(mTotalCacheSize);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            String versionName =getVersionName();
            mCurVerName.setText(versionName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    获取当前版本号
     */
    private String getVersionName() throws Exception{
        //获取包的实例
        PackageManager packageManager =getPackageManager();
        //得到包的信息,0代表版本号
        PackageInfo packageInfo =packageManager.getPackageInfo(getPackageName(),0);
        String version =packageInfo.versionName;
        return version;
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent();
        switch (view.getId()){

            case R.id.img_back:
                finish();
                break;

            case R.id.goupdate_password:
                if(mMyUser ==null){
                    intent.setClass(this, LoginActivity.class);
                }else {
                    intent.setClass(this, ChangePasswordActivity.class);
                    intent.putExtra(Contants.USER_NAME,mMyUser.getUsername());
                    intent.putExtra(Contants.OBJECT_ID,mMyUser.getObjectId());
                }
                startActivity(intent);
                break;

            case R.id.clear_cache:
                DataCleanManager.clearAllCache(this);//清楚所有应用缓存
                new MaterialDialog.Builder(this)
                        .title(getResources().getString(R.string.clear_tip))
                        .content(getResources().getString(R.string.clear_success))
                        .positiveText(getResources().getString(R.string.ok))
                        .onPositive((dialog, which) -> mCurrentCache.setText("0KB")).show();
                break;

            case R.id.setting_opinion:
                if(mMyUser ==null){
                    intent.setClass(this,LoginActivity.class);
                }else {
                    intent.setClass(this,FeedActivity.class);
                    intent.putExtra(Contants.USER_NAME,mMyUser.getUsername());
                }
                startActivity(intent);
                break;

            case R.id.setting_share:

                break;

            case R.id.setting_byme:
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                mWebView.loadUrl("https://github.com/gaoyehua");
                break;

            default:
                break;
        }
    }
}
