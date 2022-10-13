package com.dy.huibiao_f80.app.utils;

import com.dy.huibiao_f80.android_serialport_api.SerialControl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
 * Created by wangzhenxiong on 2019-07-04.
 */
public class CharUtils {
     //29 30
    private static List<String> getstrings(String s,int len1,int len2) {
        List<String> list = new ArrayList<>();
        int len = 0;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            // LogUtils.d(c);
            byte[] bytes = new byte[0];
            try {
                bytes = c.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            len = len + bytes.length;
            if (len == len1 || len == len2) {
                String substring = s.substring(index, i);
                index = i;
                list.add(substring);
                len = 0;
            }
            if (i == s.length() - 1) {
                String substring = s.substring(index, s.length());
                index = i;
                list.add(substring);
            }
        }
        return list;
    }

    private static List<String> getstrings(String s) {
        List<String> list = new ArrayList<>();
        int len = 0;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            // LogUtils.d(c);
            byte[] bytes = new byte[0];
            try {
                bytes = c.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            len = len + bytes.length;
            if (len == 33 || len == 34) {
                String substring = s.substring(index, i);
                index = i;
                list.add(substring);
                len = 0;
            }
            if (i == s.length() - 1) {
                String substring = s.substring(index, s.length());
                index = i;
                list.add(substring);
            }
        }
        return list;
    }

    private static List<String> getstrings_title(String s) {
        List<String> list = new ArrayList<>();
        int len = 0;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            // LogUtils.d(c);
            byte[] bytes = new byte[0];
            try {
                bytes = c.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            len = len + bytes.length;
            if (len == 17 || len == 18) {
                String substring = s.substring(index, i);
                index = i;
                list.add(substring);
                len = 0;
            }
            if (i == s.length() - 1) {
                String substring = s.substring(index, s.length());
                index = i;
                list.add(substring);
            }
        }
        return list;
    }
    public static void splitLineTitle(SerialControl control, String data){
        List<String> testpeoples = CharUtils.getstrings_title(data);
        for (int i = testpeoples.size() - 1; i >= 0; i--) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
    public static void splitLineTitle180(SerialControl control, String data){
        List<String> testpeoples = CharUtils.getstrings_title(data);
        for (int i = 0; i < testpeoples.size(); i++) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
    public static void splitLine(SerialControl control, String data){
        List<String> testpeoples = CharUtils.getstrings(data);
        for (int i = testpeoples.size() - 1; i >= 0; i--) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
    public static void splitLine180(SerialControl control, String data){
        List<String> testpeoples = CharUtils.getstrings(data);
        for (int i = 0; i < testpeoples.size(); i++) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
    public static void splitLine(SerialControl control,String data,int len1,int len2){
        List<String> testpeoples = CharUtils.getstrings(data,len1,len2);
        for (int i = testpeoples.size() - 1; i >= 0; i--) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
    public static void splitLine180(SerialControl control,String data,int len1,int len2){
        List<String> testpeoples = CharUtils.getstrings(data,len1,len2);
        for (int i = 0; i < testpeoples.size(); i++) {
            control.sendPortData(control, testpeoples.get(i) + "\r");
        }
    }
}
