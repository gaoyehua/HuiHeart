package com.yeyu.james.huiheart.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.yeyu.james.huiheart.R;
import com.yeyu.james.huiheart.UI.base.BaseViewHolder;
import com.yeyu.james.huiheart.UI.base.SimpleAdapter;
import com.yeyu.james.huiheart.entity.AdvisoryFragmentBean;
import com.yeyu.james.huiheart.widget.GlideCircleTransform;

import java.util.List;


/**
 * Created by Administrator on 2016/10/24.
 */

public class AdvisoryAdapter extends SimpleAdapter<AdvisoryFragmentBean.ResultsBean> {
    public AdvisoryAdapter(Context context, List<AdvisoryFragmentBean.ResultsBean> datas) {
        super(context, R.layout.advice_item, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, AdvisoryFragmentBean.ResultsBean item) {
        Glide.with(context).load(item.getAvatar())
                .asBitmap()
                .transform(new GlideCircleTransform(context))
                .into(viewHoder.getImageView(R.id.item_avatar));
        viewHoder.getTextView(R.id.item_nickname).setText(item.getNickname());
        viewHoder.getTextView(R.id.item_qualification).setText(item.getQualification());
        viewHoder.getTextView(R.id.item_goodat).setText(item.getCatgs());
        viewHoder.getTextView(R.id.item_slogan).setText(item.getSlogan());
        viewHoder.getTextView(R.id.item_likenums).setText(item.getLikeCnt());
        viewHoder.getTextView(R.id.itme_distance).setText(item.getCity());
        int price = Integer.parseInt(item.getAudioCallPrice());
        int currentPrice = price * 5;
        viewHoder.getTextView(R.id.item_money).setText(currentPrice + "");
    }
}
