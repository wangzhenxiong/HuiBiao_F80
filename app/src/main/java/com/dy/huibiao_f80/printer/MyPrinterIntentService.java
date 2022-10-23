package com.dy.huibiao_f80.printer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.dy.huibiao_f80.greendao.TestRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyPrinterIntentService extends IntentService {

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DEAILPRINT = "com.wangzx.dy.sample.printer.action.DEAILPRINT";
    private static final String ACTION_MULTIPLE = "com.wangzx.dy.sample.printer.action.MULTIPLE";
    private static final String ACTION_SINGLE = "com.wangzx.dy.sample.printer.action.SINGLE";
    private static final String ACTION_SINGLEQRCODE = "com.wangzx.dy.sample.printer.action.SINGLEQRCODE";
    private static final String ACTION_PRINTCERTIFICATE = "com.wangzx.dy.sample.printer.action.ACTION_PRINTCERTIFICATE";

    private static final String EXTRA_PARAM1 = "com.wangzx.dy.sample.printer.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.wangzx.dy.sample.printer.extra.PARAM2";

    public MyPrinterIntentService() {
        super("MyPrinterIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionToDeailPrint(Context context, List<String> param1) {
        numbers++;
        Intent intent = new Intent(context, MyPrinterIntentService.class);
        intent.setAction(ACTION_DEAILPRINT);
        intent.putStringArrayListExtra(EXTRA_PARAM1, (ArrayList<String>) param1);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPrintMultiple(Context context, List<TestRecord> param1) {
        numbers++;
        Intent intent = new Intent(context, MyPrinterIntentService.class);
        intent.setAction(ACTION_MULTIPLE);
        intent.putParcelableArrayListExtra(EXTRA_PARAM1, (ArrayList<? extends Parcelable>) param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPrintSingle(Context context, List<TestRecord> param1) {
        numbers++;
        Intent intent = new Intent(context, MyPrinterIntentService.class);
        intent.setAction(ACTION_SINGLE);
        intent.putParcelableArrayListExtra(EXTRA_PARAM1, (ArrayList<? extends Parcelable>) param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPrintSingleQRcode(Context context, List<TestRecord> param1) {
        numbers++;
        Intent intent = new Intent(context, MyPrinterIntentService.class);
        intent.setAction(ACTION_SINGLEQRCODE);
        intent.putParcelableArrayListExtra(EXTRA_PARAM1, (ArrayList<? extends Parcelable>) param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * 打印的批次数目
     */
    static int numbers;

    public static void startActionToPrintCertificate(Context context, List<TestRecord> param1) {
        Intent intent = new Intent(context, MyPrinterIntentService.class);
        intent.setAction(ACTION_PRINTCERTIFICATE);
        intent.putParcelableArrayListExtra(EXTRA_PARAM1, (ArrayList<? extends Parcelable>) param1);
        //intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DEAILPRINT.equals(action)) {
                ArrayList<String> param1 = intent.getStringArrayListExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionDeailList(param1);
            } else if (ACTION_MULTIPLE.equals(action)) {
                final List<TestRecord> param1 = intent.getParcelableArrayListExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionMultiple(param1);
            } else if (ACTION_SINGLE.equals(action)) {
                final List<TestRecord> param1 = intent.getParcelableArrayListExtra(EXTRA_PARAM1);
                //final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSingle(param1);
            }
        }
    }
















    /**
     * 传进一批数据 需要再进行分析看是单条打印还是合并打印
     *
     * @param param1 检测数据的数据库id集合
     */
    private void handleActionDeailList(List<String> param1) {
        numbers--;
        new PrintHelper(param1).run();
        //LogUtils.d(param1 + "--" + numbers);


    }

    /**
     * 多条合并打印
     *
     * @param param1 数据集合
     */
    private void handleActionMultiple(List<TestRecord> param1) {
        numbers--;
        new PrintTask_Multiple(param1, numbers).run();
        //LogUtils.d(param1 + "--" + numbers);


    }

    /**
     * 单条打印
     *
     * @param param1 数据集合
     */
    private void handleActionSingle(List<TestRecord> param1) {
        // throw new UnsupportedOperationException("Not yet implemented");
        numbers--;
        new PrintTask_Single(param1, numbers).run();
        //LogUtils.d(param1 + "--" + numbers);
    }


}