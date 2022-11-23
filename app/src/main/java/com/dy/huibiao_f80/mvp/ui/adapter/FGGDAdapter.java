package com.dy.huibiao_f80.mvp.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.app.utils.NumberUtils;
import com.dy.huibiao_f80.bean.GalleryBean;
import com.dy.huibiao_f80.bean.base.BaseProjectMessage;
import com.dy.huibiao_f80.greendao.ProjectFGGD;

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
public class FGGDAdapter extends BaseQuickAdapter<GalleryBean, BaseViewHolder> {
    public FGGDAdapter(int layoutResId, @Nullable List<GalleryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GalleryBean item) {
        double absorbance1 = item.getAbsorbance1();
        if (absorbance1<0){
            absorbance1=0;
        }
        String four = NumberUtils.fourString(absorbance1);
        helper.setText(R.id.gallery_num,item.getGalleryNum()+"")
                .setText(R.id.abs_value,"ABS:"+ four)
                .setText(R.id.contro_state,item.getDowhat()==1?"(样品)":"(对照)");

        double luminousness = 0;
        BaseProjectMessage baseProjectMessage = item.getmProjectMessage();
        if (null!=baseProjectMessage){
            ProjectFGGD baseProjectMessage1 = (ProjectFGGD) baseProjectMessage;
            int wavelength = 0;
            wavelength = baseProjectMessage1.getWavelength();
            switch (wavelength) {
                case 0:
                    luminousness = item.getLuminousness1();
                    break;
                case 1:
                    luminousness = item.getLuminousness2();
                    break;
                case 2:
                    luminousness = item.getLuminousness3();
                    break;
                case 3:
                    luminousness = item.getLuminousness4();
                    break;
            }
            if (luminousness != 0) {
                if (luminousness < Constants.FGLIMITVALUE_LOW) {
                    //LogUtils.d("1");
                    //helper.setVisible(R.id.error_message, false);
                    helper.setBackgroundColor(R.id.abs_value, Color.argb(86, 2, 202, 250));
                } else if (luminousness > Constants.FGLIMITVALUE_HEIGHT()) {
                    //LogUtils.d("2");
                    //helper.setVisible(R.id.error_message, true).setText(R.id.error_message, value);
                    helper.setBackgroundColor(R.id.abs_value, Color.argb(50, 255, 0, 0));
                } else {
                    //LogUtils.d("3");
                   // helper.setVisible(R.id.error_message, false);
                    helper.setBackgroundColor(R.id.abs_value, Color.argb(0, 0, 0, 0));
                }
            }   
        }

    }
}
