package com.dy.huibiao_f80.app.utils;

import android.app.Activity;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.Constants;
import com.dy.huibiao_f80.MyAppLocation;
import com.dy.huibiao_f80.R;
import com.dy.huibiao_f80.greendao.DBHelper;
import com.dy.huibiao_f80.greendao.ProjectFGGD;
import com.dy.huibiao_f80.greendao.ProjectJTJ;
import com.dy.huibiao_f80.greendao.daos.ProjectFGGDDao;
import com.dy.huibiao_f80.greendao.daos.ProjectJTJDao;
import com.dy.huibiao_f80.mvp.ui.widget.OutMoudle;
import com.dy.huibiao_f80.mvp.ui.widget.filePicker.LFilePicker;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
 * @data: 10/29/22 10:31 AM
 * Description:
 */
public class JxlUtils {
    /**
     * 打开目录选择器
     */
    public static void openFilechose_Folder(Activity activity, int code) {
        //打开资源管理器
        //给定默认路径，插入U盘和没插入U盘两个路径
        String path = null;
        if (FileUtils.createOrExistsDir(new File("/mnt/usb_storage/dayuan"))) {
            path = "/mnt/usb_storage/dayuan";
        } else {
            path = "/mnt/internal_sd/dayuan";
        }
        new LFilePicker()
                .withActivity(activity)
                .withRequestCode(code)
                .withMutilyMode(false)
                .withStartPath(path)
                .withBackIcon(0)
                .withChooseMode(false)
                .withTitle(activity.getString(R.string.pleacechosefile))
                .start();


    }

    /**
     * 打开文件选择器
     */
    public static void openFilechose_File(Activity context, int code) {
        //https://github.com/leonHua/LFilePicker
        //打开资源管理器
        String path = null;
        if (FileUtils.createOrExistsDir(new File("/mnt/usb_storage/dayuan"))) {
            path = "/mnt/usb_storage/dayuan";
        } else {
            path = "/mnt/internal_sd/dayuan";
        }
        new LFilePicker()
                .withActivity(context)
                .withRequestCode(code)
                .withFileFilter(new String[]{"xls"})
                .withMutilyMode(false)
                .withStartPath(path)
                .withChooseMode(true)
                .withBackIcon(0)
                .withTitle(context.getString(R.string.pleacechosefile))
                .start();
    }

    public static Observable<List<String>> inToXlsJtj(List<String> paths) {
        List<ProjectJTJ> testItems = null;
        ProjectJTJDao dao = DBHelper.getProjectJTJDao();
        testItems = dao.queryBuilder().where(ProjectJTJDao.Properties.Creator.eq(0)).list();

        List<ProjectJTJ> finalTestItems = testItems;

        return Observable.just(paths).map(new Function<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> paths) throws Exception {
                List<String> booleanList = new ArrayList<>();
                for (int k = 0; k < paths.size(); k++) {
                    String s = paths.get(k);
                    Workbook book = Workbook.getWorkbook(new File(s));
                    Sheet sheet = book.getSheet(0);
                    int rows = sheet.getRows(); //行
                    int columns = sheet.getColumns();//列
                    String text = s + "非胶体金检测项目";
                    String result2 = sheet.getCell(2, 0).getContents();
                    String result3 = sheet.getCell(3, 0).getContents();
                    String result4 = sheet.getCell(4, 0).getContents();
                    LogUtils.d(result2 + result3 + result4 + columns);
                    //曲线名称,曲线序号,是否默认曲线
                    if (!"曲线名称".equals(result2) || !"曲线序号".equals(result3) || !"是否默认曲线".equals(result4) ) {
                        booleanList.add(text);
                        continue;
                    }
                    for (int i = 1; i < rows; i++) {
                        ProjectJTJ nc = new ProjectJTJ();
                        for (int j = 0; j < columns; j++) {
                            Cell cell = sheet.getCell(j, i);
                            String result = cell.getContents();
                            if (result != null && !"".equals(result.trim()) && !"null".equals(result.trim())) {
                                switch (j) {

                                    case 0:
                                       // nc.setId(Long.parseLong(result));
                                        nc.setId(null);
                                        break;
                                    case 1:
                                        nc.setProjectName(result);
                                        break;
                                    case 2:
                                        nc.setCurveName(result);
                                        break;
                                    case 3:
                                        nc.setCurveOrder(Integer.valueOf(result));
                                        break;
                                    case 4:
                                        nc.setIsdefault(Boolean.valueOf(result));
                                        break;
                                    case 5:
                                        nc.setStandardName(result);
                                        break;
                                    case 6:
                                        nc.setTestMethod(Integer.valueOf(result));

                                        break;
                                    case 7:
                                        nc.setC(Double.valueOf(result));

                                        break;
                                    case 8:
                                        nc.settA(Double.valueOf(result));

                                        break;
                                    case 9:
                                        nc.settB(Double.valueOf(result));

                                        break;
                                    case 10:
                                        nc.setC_tA(Double.valueOf(result));

                                        break;
                                    case 11:
                                        nc.setC_tB(Double.valueOf(result));

                                        break;
                                    case 12:
                                        nc.setTips(result);

                                        break;
                                    case 13:
                                        nc.setDetectionLimit(result);

                                        break;
                                    case 14:
                                        nc.setCreator(Integer.valueOf(result));

                                        break;


                                }
                            }
                        }
                        nc.setFinishState(true);
                        dao.insert(nc);
                    }
                    String[] split = s.split("/");
                    String zjslink = split[split.length - 1];
                    Constants.JTJLINK = zjslink;
                    SPUtils.put(MyAppLocation.myAppLocation, Constants.KEY_JTJLINK, zjslink);
                    booleanList.add(s + "导入成功!");


                }
                    for (int i = 0; i < finalTestItems.size(); i++) {
                        dao.delete(finalTestItems.get(i));
                    }

                return booleanList;
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<List<String>> inToXlsFggd(List<String> path) {
        List<ProjectFGGD> testItems = null;
        ProjectFGGDDao dao = DBHelper.getProjectFGGDDao();
            testItems = dao.queryBuilder().where(ProjectFGGDDao.Properties.Creator.eq(0)).list();
        //String[] objects = paths.toArray(new String[0]);

        List<ProjectFGGD> finalTestItems = testItems;

        return Observable.just(path).map(new Function<List<String>, List<String>>() {
            @Override
            public List<String> apply(List<String> paths) throws Exception {
                List<String> booleanList = new ArrayList<>();
                for (int k = 0; k < paths.size(); k++) {  //遍历路径集合
                    String s = paths.get(k);
                    Workbook book = Workbook.getWorkbook(new File(s));
                    Sheet sheet = book.getSheet(0);
                    int rows = sheet.getRows();
                    int columns = sheet.getColumns();
                    String result1 = sheet.getCell(2, 0).getContents();
                    String result2 = sheet.getCell(3, 0).getContents();
                    String result3 = sheet.getCell(4, 0).getContents();
                    LogUtils.d(result3 + result2 + result1 + columns);
                    //曲线名称,曲线序号,是否默认曲线
                    if (!"是否默认曲线".equals(result3) || !"曲线序号".equals(result2) || !"曲线名称".equals(result1) ) {
                        String text = s + "非分光检测项目";
                        booleanList.add(text);
                        continue;
                    }
                    for (int i = 1; i < rows; i++) {
                        ProjectFGGD nc = new ProjectFGGD();
                        for (int j = 0; j < columns; j++) {
                            Cell cell = sheet.getCell(j, i);
                            String result = cell.getContents();
                            if (result != null && !"".equals(result.trim()) && !"null".equals(result.trim())) {
                                switch (j) {
                                    case 0:
                                        nc.setId(null);
                                        break;
                                    case 1:
                                        nc.setProjectName(result);
                                        break;
                                    case 2:
                                        nc.setCurveName(result);
                                        break;
                                    case 3:
                                        nc.setCurveOrder(Integer.valueOf(result));
                                        break;
                                    case 4:
                                        nc.setIsdefault(Boolean.valueOf(result));
                                        break;
                                    case 5:
                                        nc.setStandardName(result);
                                        break;
                                    case 6:
                                        nc.setMethod(Integer.valueOf(result));
                                        break;
                                    case 7:
                                        nc.setWaveLength(Integer.valueOf(result));
                                        break;
                                    case 8:
                                        nc.setWarmTime(Integer.valueOf(result));
                                        break;
                                    case 9:
                                        nc.setTestTime(Integer.valueOf(result));
                                        break;
                                    case 10:
                                        nc.setResultUnit(result);
                                        break;
                                    case 11:
                                        nc.setA0(Double.valueOf(result));
                                        break;
                                    case 12:
                                        nc.setB0(Double.valueOf(result));
                                        break;
                                    case 13:
                                        nc.setC0(Double.valueOf(result));
                                        break;
                                    case 14:
                                        nc.setD0(Double.valueOf(result));
                                        break;
                                    case 15:
                                        nc.setFrom0(Double.valueOf(result));
                                        break;
                                    case 16:
                                        nc.setTo0(Double.valueOf(result));
                                        break;
                                    case 17:
                                        nc.setA1(Double.valueOf(result));
                                        break;
                                    case 18:
                                        nc.setB1(Double.valueOf(result));
                                        break;
                                    case 19:
                                        nc.setC1(Double.valueOf(result));
                                        break;
                                    case 20:
                                        nc.setD1(Double.valueOf(result));
                                        break;
                                    case 21:
                                        nc.setFrom1(Double.valueOf(result));
                                        break;
                                    case 22:
                                        nc.setTo1(Double.valueOf(result));
                                        break;
                                    case 23:
                                        nc.setA(Double.valueOf(result));
                                        break;
                                    case 24:
                                        nc.setB(Double.valueOf(result));
                                        break;
                                    case 25:
                                        nc.setC(Double.valueOf(result));
                                        break;
                                    case 26:
                                        nc.setD(Double.valueOf(result));
                                        break;
                                    case 27:
                                        nc.setUser_yin(Boolean.valueOf(result));
                                        break;
                                    case 28:
                                        nc.setYin_a(Double.valueOf(result));
                                        break;
                                    case 29:
                                        nc.setYin_a_symbol(result);
                                        break;
                                    case 30:
                                        nc.setYin_b(Double.valueOf(result));
                                        break;
                                    case 31:
                                        nc.setYin_b_symbol(result);
                                        break;
                                    case 32:
                                        nc.setUser_yang(Boolean.valueOf(result));
                                        break;
                                    case 33:
                                        nc.setYang_a(Double.valueOf(result));
                                        break;
                                    case 34:
                                        nc.setYang_a_symbol(result);
                                        break;
                                    case 35:
                                        nc.setYang_b(Double.valueOf(result));
                                        break;
                                    case 36:
                                        nc.setYang_b_symbol(result);
                                        break;
                                    case 37:
                                        nc.setUser_keyi(Boolean.valueOf(result));
                                        break;
                                    case 38:
                                        nc.setKeyi_a(Double.valueOf(result));
                                        break;
                                    case 39:
                                        nc.setKeyi_a_symbol(result);
                                        break;
                                    case 40:
                                        nc.setKeyi_b(Double.valueOf(result));
                                        break;
                                    case 41:
                                        nc.setKeyi_b_symbol(result);
                                        break;
                                    case 42:
                                        nc.setTips(result);
                                        break;
                                    case 43:
                                        nc.setDetectionLimit(result);
                                        break;
                                    case 44:
                                        nc.setCreator(Integer.valueOf(result));
                                        break;


                                }
                            }
                        }
                        nc.setFinishState(true);
                        dao.insert(nc);
                    }
                    String[] split = s.split("/");
                    String zjslink = split[split.length - 1];
                    Constants.FGITEMLINK= zjslink;
                    SPUtils.put(MyAppLocation.myAppLocation,Constants.KEY_FGITEMLINK,zjslink);
                    booleanList.add(s + "导入成功!");
                }
                    for (int i = 0; i < finalTestItems.size(); i++) {
                        dao.delete(finalTestItems.get(i));
                    }

                return booleanList;
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * @param path
     * @param filename
     * @param sheetname
     * @param list
     */

    public static Observable<Boolean> outTo_xls_1Sheet(String path, String filename, String sheetname, List<OutMoudle> list) {

        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                WritableWorkbook book = null;
                WritableSheet sheet = null;

                try {
                    boolean b = FileUtils.createOrExistsFile(path + "/" + filename);
                    book = Workbook.createWorkbook(new File(path + "/" + filename));
                    sheet = book.createSheet(sheetname, 0);


                    for (int i = 0; i < list.size(); i++) {

                        String[] split = list.get(i).getKey().toString().split(",");
                        for (int i1 = 0; i1 < split.length; i1++) {

                            if ("null".equals(split[i1].trim()) || "".equals(split[i1].trim()) || split[i1] == null) {
                                Label label = new Label(i1, i, "");
                                sheet.addCell(label);
                            } else {
                                Label label = new Label(i1, i, split[i1] + "");
                                sheet.addCell(label);
                            }
                        }
                    }
                    book.write();
                    ArmsUtils.snackbarText("导出成功");
                    emitter.onNext(true);
                    emitter.onComplete();
                } catch (IOException e) {
                    emitter.onNext(false);
                    emitter.onComplete();
                    e.printStackTrace();
                    ArmsUtils.snackbarText("jxl组件创建表格时发生异常");

                } finally {
                    if (book != null) {
                        try {
                            //关闭文件
                            book.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }
}
