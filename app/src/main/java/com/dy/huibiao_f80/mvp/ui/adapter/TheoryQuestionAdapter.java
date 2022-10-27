package com.dy.huibiao_f80.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.api.back.BeginTheoryExam_Back;

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
 * @data: 10/24/22 10:06 AM
 * Description:
 */
public class TheoryQuestionAdapter extends BaseQuickAdapter<BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean, BaseViewHolder> {
    public TheoryQuestionAdapter(int layoutResId, @Nullable List<BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeginTheoryExam_Back.EntityBean.TheoryQuestionRadioListBean item) {
        helper.setText(R.id.number, helper.getPosition() + 1 + "");
        if (item.isCheck()) {
            helper.setBackgroundRes(R.id.parent, R.drawable.blue_cri_icn);
        } else {
            //helper.setBackgroundRes(R.id.parent,R.drawable.write_cri_icn);
            String studentAnswer = item.getStudentAnswer();
            if (studentAnswer.isEmpty()) {
                helper.setBackgroundRes(R.id.parent,R.drawable.whit_cri_icn);
            } else {
                helper.setBackgroundRes(R.id.parent,R.drawable.gray_cri_icn);
            }
        }

       /* String studentAnswer = item.getStudentAnswer();
        if (studentAnswer.isEmpty()) {
            helper.setTextColor(R.id.number, Color.parseColor("#000000"));
        } else {
            helper.setTextColor(R.id.number, Color.parseColor("#7FEA04"));
        }*/

    }
}
