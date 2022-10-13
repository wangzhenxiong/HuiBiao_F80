package com.dy.huibiao_f80.app.utils;

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
 * Created by wangzx on 2017/7/28.
 */

public class MyFFT {
    public MyFFT() {
    }

    /*/
    n shuju changdu
     x 数组实部
    y 数组虚部
    ifft  -1 逆变换  其他为正变换

     */
    public void fft(int n,double[] x, double[] y, int ifft) {
        int j;
        int k;
        int jk;
        double[] sin = new double[n];
        double[] cos = new double[n];
        for (int i = 0; i < n; i++) {
            cos[i] = Math.cos(2* Math.PI*i/n);
            sin[i] = Math.sin(2* Math.PI*i/n);
        }
        if(ifft==-1)
        {
            for (int i = 0; i < n; i++) {
                y[i] = -y[i];
            }
        }
        double[] a = new double[n];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
          a[i]=0;
          b[i]=0;
        }
        for (j = 0; j < n; j++) {
            for (k = 0; k < n; k++) {
                jk = (j * k) % (n);
                a[j] = a[j] + x[k] * cos[jk] - y[k] * sin[jk];
                b[j] = b[j] + y[k] * cos[jk] + x[k] * sin[jk];
            }
        }
     //   LogUtils.d(a);
        if (ifft == -1) {
            for (j = 0; j < n; j++) {
                x[j] = a[j] / n;
                y[j] = -b[j] / n;
            }
        } else {
            for (j = 0; j < n; j++) {
                x[j] = a[j];
                y[j] = b[j];
            }
        }
           /* int i, j, k, n1, n2, a;
            double c, s, t1, t2;*/

        // Bit-reverse
           /* j = 0;
            n2 = n / 2;
            for (i = 1; i < n - 1; i++) {
                n1 = n2;
                while (j >= n1) {
                    j = j - n1;
                    n1 = n1 / 2;
                }
                j = j + n1;

                if (i < j) {
                    t1 = x[i];
                    x[i] = x[j];
                    x[j] = t1;
                    t1 = y[i];
                    y[i] = y[j];
                    y[j] = t1;
                }
            }*/

           /* // FFT
            n1 = 0;
            n2 = 1;

            for (i = 0; i < m; i++) {
                n1 = n2;
                n2 = n2 + n2;
                a = 0;

                for (j = 0; j < n1; j++) {
                    c = cos[a];
                    s = sin[a];
                    a += 1 << (m - i - 1);

                    for (k = j; k < n; k = k + n2) {
                        t1 = c * x[k + n1] - s * y[k + n1];
                        t2 = s * x[k + n1] + c * y[k + n1];
                        x[k + n1] = x[k] - t1;
                        y[k + n1] = y[k] - t2;
                        x[k] = x[k] + t1;
                        y[k] = y[k] + t2;
                    }
                }
            }*/
    }
}
