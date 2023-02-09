package com.dy.huibiao_f80.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginAnalyseExam_Back;

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
public class ExamAnalyseAdapter extends BaseQuickAdapter<BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean, BaseViewHolder> {
    public ExamAnalyseAdapter(int layoutResId, @Nullable List<BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeginAnalyseExam_Back.EntityBean.AnalysePaperListBean item) {
        helper.setText(R.id.titlenum,"第"+(helper.getPosition()+1)+"题("+item.getScore()+"分)");
        if (item.check){
            helper.setBackgroundColor(R.id.titlenum, Color.parseColor("#476bfc"));
            helper.setTextColor(R.id.titlenum, Color.parseColor("#ffffff"));
        }else {
            helper.setBackgroundColor(R.id.titlenum, Color.parseColor("#ffffff"));
            helper.setTextColor(R.id.titlenum, Color.parseColor("#c6c6c6"));
        }
    }
}
