package com.dy.huibiao_f80.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
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
public class FGGDTestResultAdapter extends BaseQuickAdapter<GalleryBean, BaseViewHolder> {
    public FGGDTestResultAdapter(int layoutResId, @Nullable List<GalleryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GalleryBean item) {
        TestRecord item1 = (TestRecord) item;


        helper.setChecked(R.id.checkbox,item.checkd);

        String decisionoutcome = item1.getDecisionoutcome();
        if (decisionoutcome.isEmpty()){
            decisionoutcome=item.getRemainingtime()+"";
        }
        helper.setText(R.id.gallery_num,item.getGalleryNum()+"")
                .setText(R.id.samplename,item1.getSamplename())
                .setText(R.id.sampleserial,item1.getSamplenum())
                .setText(R.id.dishu,item1.getDilutionratio()+"")
                .setText(R.id.testresult,item1.getTestresult()+"")
                .setText(R.id.jujdger, decisionoutcome +"")
        ;

        double abs_ = 0;
        BaseProjectMessage baseProjectMessage = item.getmProjectMessage();
        if (null!=baseProjectMessage){
            ProjectFGGD baseProjectMessage1 = (ProjectFGGD) baseProjectMessage;
            int wavelength = 0;
            wavelength = baseProjectMessage1.getWavelength();
            switch (wavelength) {
                case 0:
                    abs_ = item.getAbsorbance1()-item.getAbsorbance1_start();
                    break;
                case 1:
                    abs_ =  item.getAbsorbance2()-item.getAbsorbance2_start();
                    break;
                case 2:
                    abs_ =  item.getAbsorbance3()-item.getAbsorbance2_start();
                    break;
                case 3:
                    abs_ =  item.getAbsorbance4()-item.getAbsorbance4_start();
                    break;
            }

        }
        if (abs_<0){
            abs_=0;
        }
        double four = NumberUtils.four(abs_);
        helper.setText(R.id.abs_value,four+"");

    }
}