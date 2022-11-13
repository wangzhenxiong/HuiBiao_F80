package com.dy.huibiao_f80.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dy.huibiao_f80.bean.base.BaseStandard_LawsMessage;

import java.util.List;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * Created by wangzhenxiong on 2019/3/14.
 */
public class Standard_LawsAdapter extends BaseQuickAdapter<BaseStandard_LawsMessage, BaseViewHolder> {
    private Context mContext;

    public Standard_LawsAdapter(int layoutResId, List<? extends BaseStandard_LawsMessage> data, Context context) {
        super(layoutResId, (List<BaseStandard_LawsMessage>) data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, BaseStandard_LawsMessage item) {
       /* if (item instanceof Standard_KJFW) {
            Standard_KJFW item1 = (Standard_KJFW) item;
            helper.setText(R.id.num, helper.getAdapterPosition() + 1 + "")
                    .setText(R.id.standname, item1.getStd_name())
                    .setText(R.id.stand_type, item1.getStd_type())
                    .setText(R.id.stand_state, item1.getStd_status())
                    .setOnClickListener(R.id.check_pdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ShowPDFActivity.class);
                            LogUtils.d(item1);
                            intent.putExtra("where",2+"");
                            intent.putExtra("type", "2");
                            intent.putExtra("data",item1.getUrl_path());
                            mContext.startActivity(intent);
                        }
                    });
        } else if (item instanceof Standard_YQWLW) {
            Standard_YQWLW item1 = (Standard_YQWLW) item;
            helper.setText(R.id.num, helper.getAdapterPosition() + 1 + "")
                    .setText(R.id.standname, item1.getStd_name())
                    .setText(R.id.stand_type, item1.getStd_type())
                    .setText(R.id.stand_state, item1.getStd_status())
                    .setOnClickListener(R.id.check_pdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ShowPDFActivity.class);
                            LogUtils.d(item1);
                            intent.putExtra("where",2+"");
                            intent.putExtra("type", "2");
                            intent.putExtra("data",item1.getUrl_path());
                            mContext.startActivity(intent);
                        }
                    });
        } else if (item instanceof Laws_KJFW) {
            Laws_KJFW item1 = (Laws_KJFW) item;
            helper.setText(R.id.num, helper.getAdapterPosition() + 1 + "")
                    .setText(R.id.standname, item1.getLaw_name())
                    .setText(R.id.stand_type, item1.getLaw_type())
                    .setText(R.id.stand_state, item1.getLaw_status())
                    .setOnClickListener(R.id.check_pdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ShowPDFActivity.class);
                            LogUtils.d(item1);
                            intent.putExtra("where",2+"");
                            intent.putExtra("type", "1");
                            intent.putExtra("data",item1.getUrl_path());
                            mContext.startActivity(intent);
                        }
                    });
        } else if (item instanceof Laws_YQWLW) {
            Laws_YQWLW item1 = (Laws_YQWLW) item;
            helper.setText(R.id.num, helper.getAdapterPosition() + 1 + "")
                    .setText(R.id.standname, item1.getLaw_name())
                    .setText(R.id.stand_type, item1.getLaw_type())
                    .setText(R.id.stand_state, item1.getLaw_status())
                    .setOnClickListener(R.id.check_pdf, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ShowPDFActivity.class);
                            LogUtils.d(item1);
                            intent.putExtra("where",2+"");
                            intent.putExtra("type", "1");
                            intent.putExtra("data",item1.getUrl_path());
                            mContext.startActivity(intent);
                        }
                    });
        }*/

    }

}
