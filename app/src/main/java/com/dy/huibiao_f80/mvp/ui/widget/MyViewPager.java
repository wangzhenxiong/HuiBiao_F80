package com.dy.huibiao_f80.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

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
 * @data: 10/16/22 11:26 AM
 * Description:
 */
public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mChildrenCount = getChildCount();
        //遍历
        int  height=0;
        for (int i = 0; i < mChildrenCount; i++) {
            LayoutParams childLayoutParams =(LayoutParams) getChildAt(i).getLayoutParams();//获取LayoutParams
            //MeasureSpec三种模式 MeasureSpec.UNSPECIFIED：不对View大小做限制，如：ListView，ScrollView
            //MeasureSpec.EXACTLY：确切的大小，如：100dp或者march_parent
            //MeasureSpec.AT_MOST：大小不可超过某数值，如：wrap_content
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(childLayoutParams.width,MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(childLayoutParams.height,MeasureSpec.EXACTLY));
            //取最大值
            if (height<MeasureSpec.makeMeasureSpec(getChildAt(i).getLayoutParams().height,MeasureSpec.EXACTLY)){
                height=MeasureSpec.makeMeasureSpec(getChildAt(i).getLayoutParams().height,MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, height);
    }

}
