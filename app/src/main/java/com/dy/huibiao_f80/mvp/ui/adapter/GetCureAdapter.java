package com.dy.huibiao_f80.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.GalleryBean;

import java.util.List;

/**
 * 　 ┏┓　  ┏┓+ +
 * 　┏┛┻━━ ━┛┻┓ + +
 * 　┃　　　　 ┃
 * 　┃　　　　 ┃  ++ + + +
 * 　┃████━████+
 * 　┃　　　　 ┃ +
 * 　┃　　┻　  ┃
 * 　┃　　　　 ┃ + +
 * 　┗━┓　  ┏━┛
 * 　  ┃　　┃
 * 　  ┃　　┃　　 + + +
 * 　  ┃　　┃
 * 　  ┃　　┃ + 神兽保佑,代码无bug
 * 　  ┃　　┃
 * 　  ┃　　┃　　+
 * 　  ┃　 　┗━━━┓ + +
 * 　　┃ 　　　　 ┣┓
 * 　　┃ 　　　 ┏┛
 * 　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　 ┃┫┫ ┃┫┫
 * 　　 ┗┻┛ ┗┻┛+ + + +
 *
 * @author: wangzhenxiong
 * @data: 10/17/22 4:38 PM
 * Description:
 */
public class GetCureAdapter extends BaseQuickAdapter<GalleryBean, BaseViewHolder> {
    public GetCureAdapter(int layoutResId, @Nullable List<GalleryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GalleryBean item) {
        double absorbance1 = item.getAbsorbance1();
        if (absorbance1<0){
            absorbance1=0;
        }
        double four = NumberUtils.four(absorbance1);
        helper.setText(R.id.abs,"ABS:"+ four)
                .setText(R.id.gallerynum,item.getGalleryNum()+"")
                .setText(R.id.mic,"预设浓度值："+item.getMic()+"（点击修改）");

        helper.addOnClickListener(R.id.checkbox);
        helper.addOnClickListener(R.id.mic);
        helper.setChecked(R.id.checkbox,item.isCheckd());
    }
}
