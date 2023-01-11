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

       helper.addOnClickListener(R.id.checkbox);
        boolean checkd = item.checkd;
        if (checkd){
            helper.setChecked(R.id.checkbox, true);
        } else {
            helper.setChecked(R.id.checkbox, false);
        }


        String decisionoutcome = item1.getDecisionoutcome();
        if (decisionoutcome.isEmpty()){
            int remainingtime = item.getRemainingtime();
            if (item.getDowhat()==1){
                remainingtime--;
                decisionoutcome= remainingtime<=0?"":remainingtime +"";
            }else {

                decisionoutcome= remainingtime<=0?"":remainingtime +"";
            }
        }
        helper.setText(R.id.gallery_num,item.getGalleryNum()+""+(item.getDowhat()==2?"(对照)":""))
                .setText(R.id.samplename,item1.getSamplename())
                .setText(R.id.sampleserial,item1.getSamplenum())
                .setText(R.id.dishu,item1.getDilutionratio()+"")
                .setText(R.id.testresult,item1.getTestresult()+"")
                .setText(R.id.jujdger, decisionoutcome +"");

        double abs_ = 0;
        double abs_now = 0;
        BaseProjectMessage baseProjectMessage = item.getmProjectMessage();
        if (null!=baseProjectMessage){
            ProjectFGGD baseProjectMessage1 = (ProjectFGGD) baseProjectMessage;
            int wavelength = 0;
            wavelength = baseProjectMessage1.getWavelength();
            switch (wavelength) {
                case 0:
                    abs_ = item.getAbsorbance1()-item.getAbsorbance1_start();
                    abs_now=item.getAbsorbance1();
                    break;
                case 1:
                    abs_ =  item.getAbsorbance2()-item.getAbsorbance2_start();
                    abs_now=item.getAbsorbance2();
                    break;
                case 2:
                    abs_ =  item.getAbsorbance3()-item.getAbsorbance2_start();
                    abs_now=item.getAbsorbance3();
                    break;
                case 3:
                    abs_ =  item.getAbsorbance4()-item.getAbsorbance4_start();
                    abs_now=item.getAbsorbance4();
                    break;
            }

           /* int method = baseProjectMessage1.getMethod();
            if (method!=0){
               abs_= abs_now;
            }*/

        }

        if (abs_<0){
            abs_=0;
        }
        String four = NumberUtils.fourString(abs_);
        if (item.getState()==1){
            helper.setText(R.id.abs_value,four+"");
        }


       /* helper.setOnCheckedChangeListener(R.id.checkbox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.d(item);
                item.setCheckd(isChecked);
            }
        }) ;*/

    }
}
