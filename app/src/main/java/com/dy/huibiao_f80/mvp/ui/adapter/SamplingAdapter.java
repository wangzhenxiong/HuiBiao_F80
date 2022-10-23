package com.dy.huibiao_f80.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

    @Override
    protected void convert(BaseViewHolder helper, Sampling item) {
        helper.setText(R.id.sampleserial,item.getSamplingNumber()+"")
                .setText(R.id.samplename,item.getSamplingName()+"")
                .setText(R.id.beunits,item.getUnitDetected())
                .setText(R.id.testunit,item.getTestingUnit())
                .setText(R.id.creationtime,item.getCreationTimeyymmddhhssmm())
                .setText(R.id.testtime,item.getTestingTimeyymmddhhssmm())
                .setText(R.id.testresult,item.getTestResult())
        ;
        helper.setChecked(R.id.checkbox,item.check);
        ((CheckBox) helper.getView(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setCheck(isChecked);
            }
        });
    }
}
