package com.dy.huibiao_f80.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.TestRecord;

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
public class TestRecrdAdapter extends BaseQuickAdapter<TestRecord, BaseViewHolder> {
    public TestRecrdAdapter(int layoutResId, @Nullable List<TestRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestRecord item) {
        if (helper.getPosition() % 2 == 0) {
            helper.setBackgroundColor(R.id.parent_layout, Color.parseColor("#ffffff"));
        } else {
            helper.setBackgroundColor(R.id.parent_layout, Color.parseColor("#fafafa"));

        }
        if (item.getTest_method().equals(MyAppLocation.myAppLocation.getString(R.string.mothod1))||item.getTest_method().equals(MyAppLocation.myAppLocation.getString(R.string.mothod2))){
            if (item.getControlvalue().equals("-1.0")||item.getControlvalue().equals("-2.0")||item.getControlvalue().equals("0.0")) {
                helper.setText(R.id.controvalue, "无效")
                        .setText(R.id.testresult, "");
            } else {
                helper.setText(R.id.controvalue, item.getControlvalue())
                        .setText(R.id.testresult, item.getTestresult() + item.getCov_unit());

            }
        } else {
            helper.setText(R.id.controvalue, "")
                    .setText(R.id.testresult, item.getTestresult() + item.getCov_unit());
        }

        helper.addOnClickListener(R.id.checkbox);
        helper.setChecked(R.id.checkbox, item.checkd);
        String testResult = item.getDecisionoutcome();
        helper.setText(R.id.samplename, item.getSamplename())
                //.setText(R.id.samplenumber,item.getSamplenum())
                .setText(R.id.testmoudle, item.getTest_Moudle())
                .setText(R.id.testprojectname, item.getTest_project())
                .setText(R.id.gallery, item.getGallery() + "")
                .setText(R.id.jujdger, testResult)
                .setText(R.id.testtime, item.getdfTestingtimeyy_mm_dd_hh_mm_ss());
        if (testResult.isEmpty()) {
            if (helper.getPosition() % 2 == 0) {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#ffffff"));
            } else {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#fafafa"));
            }

        } else {
            if (testResult.equals("合格")) {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#e7ffe6"));
                helper.setTextColor(R.id.jujdger, Color.parseColor("#76d173"));
            } else if (testResult.equals("不合格")) {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#ffeded"));
                helper.setTextColor(R.id.jujdger, Color.parseColor("#fd6767"));
            } else if (testResult.equals("可疑")) {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#fff6e4"));
                helper.setTextColor(R.id.jujdger, Color.parseColor("#ffa800"));
            } else {
                helper.setBackgroundColor(R.id.jujdger, Color.parseColor("#f1f5ff"));
                helper.setTextColor(R.id.jujdger, Color.parseColor("#5885fb"));
            }
        }
     /*helper.setOnCheckedChangeListener(R.id.checkbox, new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             item.setCheckd(isChecked);
         }
     });*/

    }
}
