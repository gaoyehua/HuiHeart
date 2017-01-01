package com.yeyu.james.huiheart.UI.Activity.left.setting;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeyu.james.huiheart.Contant.Contants;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.base.BaseActivity;
import com.yeyu.james.huiheart.entity.FeedBean;
import com.yeyu.james.huiheart.utils.RegularUtils;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by James on 2016/12/13.
 */

public class FeedActivity extends BaseActivity implements
        View.OnClickListener{

    private ImageView mImgback;
    private TextView mSubmitbtn;
    private EditText mEditfeedbackcontent;
    private EditText mEditfeedbackphone;
    private String mUserName;

    String mFeedContent,mFeedPhone;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_btn:
                submit();
                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected int getResultId() {
        return R.layout.activity_feed;
    }

    @Override
    protected void initListener() {
        mSubmitbtn.setOnClickListener(this);
        mImgback.setOnClickListener(this);
    }

    @Override
    public void initView() {

        mUserName = getIntent().getStringExtra(Contants.USER_NAME);
        this.mEditfeedbackphone = (EditText) findViewById(R.id.edit_feedback_phone);
        this.mEditfeedbackcontent = (EditText) findViewById(R.id.edit_feedback_content);
        this.mSubmitbtn = (TextView) findViewById(R.id.submit_btn);
        this.mImgback = (ImageView) findViewById(R.id.img_back);
    }
    /*
    提交反馈数据
     */
    private void submit(){
        boolean isTure =check();
        if(isTure){
            FeedBean bean =new FeedBean();
            bean.setContent(mFeedContent);
            bean.setPhone(mFeedPhone);
            bean.setUsername(mUserName);
            bean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e ==null){

                        Toast.makeText(FeedActivity.this,
                                getResources().getString(R.string.submit_success),
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d(TAG,e.toString());
                    }
                }
            });
        }
    }
    /*
    验证输入
     */
    private boolean check(){
        //验证返回是否为空
        mFeedContent =mEditfeedbackcontent.getText().toString();

        if(TextUtils.isEmpty(mFeedContent)){
            Toast.makeText(this, getResources().getString(R.string.request_not_empty), Toast.LENGTH_SHORT).show();
            return false;
        }
        //验证手机号码是否为空
        mFeedPhone =mEditfeedbackphone.getText().toString();

        if (TextUtils.isEmpty(mFeedPhone)){
            Toast.makeText(this,getResources().getString(R.string.phone_num_is_not_empty),Toast.LENGTH_SHORT).show();
            return false;
        }
        //验证手机号码格式
        if(!RegularUtils.isMobileExact(mFeedPhone)){
            Toast.makeText(this, getResources().getString(R.string.check_your_phone_format), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
