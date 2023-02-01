package com.dy.huibiao_f80.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.Sampling;

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
public class SamplingAdapter extends BaseQuickAdapter<Sampling, BaseViewHolder> {
    public SamplingAdapter(int layoutResId, @Nullable List<Sampling> data) {
        super(layoutResId, data);
    }
    public boolean showCheckBox=true;

    @Override
    protected void convert(BaseViewHolder helper, Sampling item) {
        helper.addOnClickListener(R.id.checkbox);
        if (helper.getPosition()%2==0) {
            helper.setBackgroundColor(R.id.parent_layout, Color.parseColor("#ffffff"));
        }else {
            helper.setBackgroundColor(R.id.parent_layout, Color.parseColor("#fafafa"));

        }

        String testResult = item.getTestResult();
        helper.setText(R.id.sampleserial,item.getSamplingNumber()+"")
                .setText(R.id.samplename,item.getSamplingName()+"")
                .setText(R.id.beunits,item.getUnitDetected())
                .setText(R.id.testunit,item.getTestingUnit())
                .setText(R.id.creationtime,item.getCreationTimeyymmddhhssmm())
                .setText(R.id.testtime,item.getTestingTimeyymmddhhssmm())
                .setText(R.id.testresult, testResult);
        helper.setChecked(R.id.checkbox,item.check);
        if (showCheckBox){
            helper.setGone(R.id.checkbox,true);
        }else {
            helper.setGone(R.id.checkbox,false);
        }
       if (testResult.isEmpty()){
           if (helper.getPosition()%2==0) {
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#ffffff"));
           }else {
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#fafafa"));
           }

       } else {
           if (testResult.equals("合格")) {
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#e7ffe6"));
               helper.setTextColor(R.id.testresult,Color.parseColor("#76d173"));
           }else if (testResult.equals("不合格")){
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#ffeded"));
               helper.setTextColor(R.id.testresult,Color.parseColor("#fd6767"));
           }else if (testResult.equals("可疑")){
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#fff6e4"));
               helper.setTextColor(R.id.testresult,Color.parseColor("#ffa800"));
           }else {
               helper.setBackgroundColor(R.id.testresult,Color.parseColor("#f1f5ff"));
               helper.setTextColor(R.id.testresult,Color.parseColor("#5885fb"));
           }
       }

       /* helper.setOnCheckedChangeListener(R.id.checkbox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setCheck(isChecked);
            }
        });*/

    }
}
