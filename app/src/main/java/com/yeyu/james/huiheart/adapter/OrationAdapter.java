package com.yeyu.james.huiheart.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.base.BaseViewHolder;
import com.yeyu.james.huiheart.UI.base.SimpleAdapter;
import com.yeyu.james.huiheart.entity.HomeNewsBean;

import java.util.List;


/**
 * Created by Administrator on 2016/10/21.
 *
 */

public class OrationAdapter extends SimpleAdapter<HomeNewsBean.ResultsBean> {


    public OrationAdapter(Context context, List<HomeNewsBean.ResultsBean> datas) {
        super(context, R.layout.home_news_item_new, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, HomeNewsBean.ResultsBean item) {
        viewHoder.getTextView(R.id.tv_title).setText(item.getTitle());
        //viewHoder.getTextView(R.id.tv_subtitle).setText(item.getCatg());
      //  viewHoder.getTextView(R.id.tv_count_like).setText(item.getLikeCnt());
        viewHoder.getTextView(R.id.tv_count_see).setText(item.getViewCnt());
        Glide.with(context)
                .load(item.getIcon()).asBitmap().into((ImageView) viewHoder.getView(R.id.iv_pic));

    }


}
