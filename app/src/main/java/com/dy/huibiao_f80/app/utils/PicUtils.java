package com.dy.huibiao_f80.app.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import com.apkfuns.logutils.LogUtils;

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
 * Created by wangzhenxiong on 2019/3/11.
 */
public class PicUtils {
    /**
     * @param arr
     * @param width
     * @param height
     * @return 返回原始数据对应的绿色色值数组
     */
    public static int[] getGreenFromByte(byte[] arr, int width, int height) {
        int[] date = new int[height];
        for (int i = 0; i < height; i++) {
            int temp = 0;
            for (int j = width / 2 - 4; j < width / 2 + 4; j++) {
                int temp16 = (i * width + j) * 2;

                int rgb = (arr[temp16] & 0x0FF) + (arr[temp16 + 1] & 0x0FF) * 256;
                temp = temp + byteToInt((byte) (((rgb >> 5) & 0x3F) << 2));
            }
            date[height - i - 1] = temp * 10;

        }

        return date;
    }


    /**
     * @param arr 原始数据
     * @return 返回点和面积的二位数组
     */
    public static double[][] getLineArea(double[] arr) {

        double[][] result = new double[2][2];
        ArrayList<Double> list = new ArrayList<>();
        int point = 15;
        for (int i = 0; i < arr.length - point; i++) {
            double area = 0;
            double avg = (arr[i + point - 1] - arr[i]) / (point - 2);
            double tem = 0;
            for (int j = 0; j <= point - 2; j++) {
                tem = arr[i] + avg * j;
                area = area + tem - arr[i + j];
            }
            list.add(area);
        }

        for (int i = 0; i < list.size() / 2; i++) {
            if (list.get(i) > result[0][1]) {
                result[0][1] = list.get(i);
                result[0][0] = i;
            }
            if (list.get(list.size() / 2 + i) > result[1][1]) {
                result[1][1] = list.get(list.size() / 2 + i);
                result[1][0] = list.size() / 2 + i;
            }

        }


        return result;
    }
    // 将纯RGB数据数组转化成int像素数组
    public static int[] convertByteToColor(byte[] data) {
        int size = data.length;
        if (size == 0) {
            return null;
        }

        int arg = 0;
        if (size % 3 != 0) {
            arg = 1;
        }

        // 一般RGB字节数组的长度应该是3的倍数，
        // 不排除有特殊情况，多余的RGB数据用黑色0XFF000000填充
        int[] color = new int[size / 3 + arg];
        int red, green, blue;
        int colorLen = color.length;
        if (arg == 0) {
            for (int i = 0; i < colorLen; ++i) {
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);

                // 获取RGB分量值通过按位或生成int的像素值
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }
        } else {
            for (int i = 0; i < colorLen - 1; ++i) {
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }

            color[colorLen - 1] = 0xFF000000;
        }

        return color;
    }
    // 将纯RGB数据数组转化成int像素数组
    public static int[] convertByteToColor(List<Byte> data) {
        int size = data.size();
        if (size == 0) {
            return null;
        }

        int arg = 0;
        if (size % 3 != 0) {
            arg = 1;
        }

        // 一般RGB字节数组的长度应该是3的倍数，
        // 不排除有特殊情况，多余的RGB数据用黑色0XFF000000填充
        int[] color = new int[size / 3 + arg];
        int red, green, blue;
        int colorLen = color.length;
        if (arg == 0) {
            for (int i = 0; i < colorLen; ++i) {
                red = convertByteToInt(data.get(i * 3));
                green = convertByteToInt(data.get(i * 3 + 1));
                blue = convertByteToInt(data.get(i * 3 + 2));

                // 获取RGB分量值通过按位或生成int的像素值
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }
        } else {
            for (int i = 0; i < colorLen - 1; ++i) {
                red = convertByteToInt(data.get(i * 3));
                green = convertByteToInt(data.get(i * 3 + 1));
                blue = convertByteToInt(data.get(i * 3 + 2));
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }

            color[colorLen - 1] = 0xFF000000;
        }

        return color;
    }
    // 将一个byte数转成int
    // 实现这个函数的目的是为了将byte数当成无符号的变量去转化成int
    public static int convertByteToInt(byte data) {

        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }


    /**
     * @param arr    原始数据
     * @param width  图片款低
     * @param height 图片高度
     * @return 生成的图片
     * @Describe 传入摄像头生成的原始数据, 返回对应的Bitmap
     */
    public static Bitmap byteToBitMap(byte[] arr, int width, int height) {
        int[] colors = convertByteToColor(arr);    //取RGB值转换为int数组
        if (colors == null) {
            return null;
        }

        Bitmap bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        return bmp;

        //bmp = handleImageEffect(bmp, 5.3f, 5.5f, 1.1f); //亮度，饱和度调节
    }
    public static Bitmap byteToBitMap(List<Byte> arr, int width, int height) {
        int[] colors = convertByteToColor(arr);    //取RGB值转换为int数组
        if (colors == null) {
            return null;
        }

        Bitmap bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        return bmp;

        //bmp = handleImageEffect(bmp, 5.3f, 5.5f, 1.1f); //亮度，饱和度调节
    }
    /**
     * @param bm         图像 （不可修改）
     * @param hue        色相
     * @param saturation 饱和度
     * @param lum        亮度
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {

        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, 0.1f); // R
        hueMatrix.setRotate(1, hue); // G
        hueMatrix.setRotate(2, 0.1f); // B

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        //融合
        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }
    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @param x
     * @param y
     * @param ww
     * @param hh
     */
    private static Bitmap cropBitmap(Bitmap bitmap, int x, int y, int ww, int hh) {

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x,y,ww,hh, null, false);

        return bitmap1;
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    private static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ww
     * @param hh
     * @param origin 比例  @return 新的bitmap
     */
    private static Bitmap scaleBitmap(Bitmap origin, int ww, int hh) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ww/width,hh/height);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    /**
     * @param data 原始byte类型的图片数据
     * @return 可以被Bitmap构造的 int数组
     */
    @Nullable
    private static int[] byteToColor(byte[] data) {
        int size = data.length;
        if (size == 0) {
            return null;
        }

        int arg = 0;
        if (size % 3 != 0) {
            arg = 1;
        }

        int[] color = new int[size / 3 + arg];
        int red, green, blue;

        if (arg == 0) {
            for (int i = 0; i < color.length; ++i) {
                red = byteToInt(data[i * 3]);
                green = byteToInt(data[i * 3 + 1]);
                blue = byteToInt(data[i * 3 + 2]);

                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }
        } else {
            for (int i = 0; i < color.length - 1; ++i) {
                red = byteToInt(data[i * 3]);
                green = byteToInt(data[i * 3 + 1]);
                blue = byteToInt(data[i * 3 + 2]);
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }

            color[color.length - 1] = 0xFF000000;
        }

        return color;
    }

    private static int byteToInt(byte data) {

        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }

    public interface DateCallBack {
        void CallBack(int channel, byte[] arr, int width, int height, int count, int errCount);

        void ErrCallBack(int channel, String errInfo);
    }
    /**
     * @方法描述 Bitmap转RGB
     */
    public static List<float[]> bitmap2RGB(Bitmap bitmap) {
        int width = bitmap.getWidth(); //300
        int height = bitmap.getHeight(); //80

        LogUtils.d(width);
        LogUtils.d(height);
        List<float[]> list=new ArrayList<>();
        float[] list_r= new float[width-10];
        float[] list_g=new float[width-10];
        float[] list_b=new float[width-10];
        //bitmap.getPixels();                  // j=80 i=300
        for (int i = 0; i < width-10; i++) { // x                       //258个面 的G值 的平均值
            float[] r =new float[10*height] ;
            float[] g =new float[10*height] ;
            float[] b =new float[10*height] ;
            int m = 0;
            for (int j = 0; j < height; j++) {  // y  //循环20次 竖线
                for (int k = i; k <i+10 ; k++) {   //循环20次 横线
                    int pixel = bitmap.getPixel(k,j); //xy
                    r[m]= Color.red(pixel);
                    g[m]=Color.green(pixel);
                    b[m]=Color.blue(pixel);
                    m++;
                    // LogUtils.d(m);
                }
            }

            list_r[i]=(getAverage(r));
            list_g[i]=(getAverage(g));
            list_b[i]=(getAverage(b));
        }
        list.add(list_r);
        list.add(list_g);
        list.add(list_b);
        return list ;
    }
    public static float getAverage(float[] array){
        float sum = 0;
        float num = 0;
        float biggest =0;
        float smallest = 100000;
        for(int i=1;i<array.length;i++){
            if(array[i]>biggest){
                biggest = array[i];
            }
            if(array[i]<smallest){
                smallest = array[i];
            }
        }
        for(int j=0;j<array.length;j++){

            sum += (double)array[j];
            num++;

        }
        // LogUtils.d(sum+"<-->"+num);
        //return (sum-biggest-smallest)/(num-2);
        return (sum-biggest-smallest)/(num-2);
        //return (sum-biggest-smallest);
    }




}
