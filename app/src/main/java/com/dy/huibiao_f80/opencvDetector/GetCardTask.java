package com.dy.huibiao_f80.opencvDetector;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.OpenCvUtils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
 * @data: 10/29/21 2:47 PM
 * Description:
 */
public class GetCardTask implements Callable<List<DataSource>> {

    @Override
    public List<DataSource> call() throws Exception {
        return getCard();

    }

    private List<DataSource> getCard() {
        int start = 0;
        int stop = 245;


        if (mAlphaxvalue == 1) {
            start = 10;
        } else if (mAlphaxvalue == 2) {
            start = 20;
        } else if (mAlphaxvalue == 3) {
            start = 30;
        } else if (mAlphaxvalue == 4) {
            start = 40;
        } else if (mAlphaxvalue == 5) {
            start = 50;
        } else if (mAlphaxvalue == 6) {
            start = 60;
        } else {
            return null;
        }

        List<DataSource> list = new ArrayList<>();
        Mat clone = mMat.clone();
        //后面的处理参数需要动态设置 sobel  threshold   dilateAndErode（腐蚀膨胀可以不用，作用不大）
        for (int i = start; i < stop; i += 6) {
            Mat sobel = OpenCvUtils.sobel(clone, mAlphaxvalue, mAlphaxvalue);
            Mat threshold = OpenCvUtils.threshold(sobel, i);
            Mat erode = OpenCvUtils.dilateAndErode(threshold, mDekeenelvalue);
            ArrayList<MatOfPoint> es = OpenCvUtils.findContours(erode);
            DataSource source = null;

            LogUtils.d("Alphavalue: " + mAlphaxvalue + "   threshold: " + i);
            source = OpenCvUtils.processingKeyRect(es, mCardType, mMat.width(), mMat.height());
            LogUtils.d(source);
            if (source == null) {
                continue;
            }

            list.add(source);
            return list;
        }
        mAlphaxvalue = mAlphaxvalue + 1;
        return getCard();


    }


    public enum CardType {
        ONE, THREE
    }

    private Mat mMat;
    private CardType mCardType;
    private int mAlphaxvalue = 1;
    private int mDekeenelvalue = 5;

    public GetCardTask(Mat mat, CardType cardtype) {
        mMat = mat;
        mCardType = cardtype;
    }


}
