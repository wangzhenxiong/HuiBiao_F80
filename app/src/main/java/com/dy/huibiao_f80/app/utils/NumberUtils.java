package com.dy.huibiao_f80.app.utils;

import java.text.DecimalFormat;

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
 * Created by 王振雄 on 2017/6/13.
 */

public class NumberUtils {
    /**
     * @param d
     * @param num 保留小数点位数
     * @return
     */
    public static double formart(double d, int num) {
        switch (num) {
            case 1:
                return one(d);
            case 2:
                return two(d);
            case 3:
                return three(d);
            case 4:
                return four(d);
            case 5:
                return five(d);
            case 6:
                return six(d);

        }
          return six(d);
    }

    public static double one(double d) {
        String format = new DecimalFormat("##0.0").format(d);
        return stringToDouble(format);
    }

    public static double two(double d) {
        String format = new DecimalFormat("##0.00").format(d);
        return stringToDouble(format);
    }

    public static double three(double d) {
        String format = new DecimalFormat("##0.000").format(d);
        return stringToDouble(format);
    }

    public static double four(double d) {
        String format = new DecimalFormat("##0.0000").format(d);
        return stringToDouble(format);
    }
    public static double five(double d) {
        String format = new DecimalFormat("##0.0000").format(d);
        return stringToDouble(format);
    }

    public static double six(double d) {
        String format = new DecimalFormat("##0.0000").format(d);
        return stringToDouble(format);
    }

    private static double stringToDouble(String s) {
        return Double.valueOf(s);
    }


    public static String oneString(double d) {
        String format = new DecimalFormat("#,##0.0").format(d);
        return format;
    }

    public static String twoString(double d) {
        String format = new DecimalFormat("#,##0.00").format(d);
        return format;
    }

    public static String threeString(double d) {
        String format = new DecimalFormat("#,##0.000").format(d);
        return format;
    }

    public static String fourString(double d) {
        String format = new DecimalFormat("#,##0.0000").format(d);
        return format;
    }
    public static String fiveString(double d) {
        String format = new DecimalFormat("#,##0.00000").format(d);
        return format;
    }

    public static String sixString(double d) {
        String format = new DecimalFormat("#,##0.000000").format(d);
        return format;
    }
}
