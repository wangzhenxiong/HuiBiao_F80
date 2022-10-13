package com.dy.huibiao_f80.opencvDetector;

import com.apkfuns.logutils.LogUtils;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
 * @data: 10/29/21 3:07 PM
 * Description:
 */
public class CardDetector {

    private final ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    private Mat mMat;
    private GetCardTask.CardType mCardType;
    private int mCardNumber;

    public CardDetector(Mat mat, GetCardTask.CardType cardType) {
        mMat = mat.clone();
        mCardType = cardType;
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
        //startSeachCards();
    }



    public List<DataSource> startSeachCards() {
        List<DataSource> list = new ArrayList<>();
        Future<List<DataSource>> submit1 = mScheduledThreadPoolExecutor.submit(new GetCardTask(mMat, mCardType));

        mScheduledThreadPoolExecutor.shutdown();
        try {
            List<DataSource> list1 = submit1.get();
            if (list1 != null) {
                list.addAll(list1);
                LogUtils.d(list1);
                return list;
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }


}
