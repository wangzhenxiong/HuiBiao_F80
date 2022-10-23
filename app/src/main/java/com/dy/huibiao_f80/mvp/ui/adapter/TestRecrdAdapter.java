package com.dy.huibiao_f80.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
     helper.setChecked(R.id.checkbox,item.checkd);
     helper.setText(R.id.samplename,item.getSamplename())
             .setText(R.id.testmoudle,item.getTest_Moudle())
             .setText(R.id.testprojectname,item.getTest_project())
             .setText(R.id.gallery,item.getGallery()+"")
             .setText(R.id.testresult,item.getTestresult()+item.getCov_unit())
             .setText(R.id.jujdger,item.getDecisionoutcome())
             .setText(R.id.testtime,item.getdfTestingtimeyy_mm_dd_hh_mm_ss());
        ((CheckBox) helper.getView(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setCheckd(isChecked);
            }
        });
    }
}
