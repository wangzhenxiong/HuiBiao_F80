package com.dy.huibiao_f80;

import android.app.Application;
import android.os.Environment;

import com.apkfuns.logutils.LogUtils;
import com.dy.huibiao_f80.app.utils.ByteUtils;
import com.dy.huibiao_f80.app.utils.CRC8Util;
import com.dy.huibiao_f80.app.utils.DataUtils;
import com.dy.huibiao_f80.app.utils.FileUtils;
import com.dy.huibiao_f80.app.utils.SPUtils;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;


import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.dy.huibiao_f80.app.utils.CRC8Util.FindCRC;


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
 * <p> 全局常量
 * Created by wangzhenxiong on 2019/2/19.
 */
public class Constants {

    /**
     * 免疫荧光积分值统一除以2800
     */
    public static int myyg_parameter = 2800;

    //重启设备
    public static void shutdownDev() {
        try {
            Runtime.getRuntime().exec("su");
            Runtime.getRuntime().exec("reboot -p");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //重金属检测项目表名称
    public static String KEY_ZJSLINK = "KEY_ZJSLINK";
    public static String ZJSLINK = "";

    //胶体金检测项目表名称
    public static String KEY_JTJLINK = "KEY_JTJLINK";
    public static String JTJLINK = "";

    //分光光度检测项目表名称
    public static String KEY_FGITEMLINK = "KEY_FGITEMLINK";
    public static String FGITEMLINK = "";

    //样品表名称
    public static String KEY_FOOLINKNAME = "KEY_FOOLINKNAME";
    public static String FOOLINKNAME = "";

    // 小票上的审核人员
    public static String KEY_CHECKPEOPLE = "KEY_CHECKPEOPLE";
    public static String CHECKPEOPLE = "";

    public static int MINWIDTH = 250;
    public static String KEY_MINWIDTH = "KEY_MINWIDTH";
    public static int MINHEIGHT = 40;
    public static String KEY_MINHEIGHT = "KEY_MINHEIGHT";

    public static int LIMITANGLEA = 10;
    public static String KEY_LIMITANGLEA = "KEY_LIMITANGLEA";

    public static int LIMITANGLEB = 80;
    public static String KEY_LIMITANGLEB = "KEY_LIMITANGLEB";

    public static int LIMITCHANNL = 20;
    public static String KEY_LIMITCHANNL = "KEY_LIMITCHANNL";

    public static int MINWDIFFERENCEVALUE = 10;
    public static String KEY_MINWDIFFERENCEVALUE = "KEY_MINWDIFFERENCEVALUE";

    public static int MINHDIFFERENCEVALUE = 10;
    public static String KEY_MINHDIFFERENCEVALUE = "KEY_MINHDIFFERENCEVALUE";

    /**
     * X方向权重
     */
    public static int ALPHAXVALUE;
    public static String KEY_ALPHAXVALUE = "KEY_ALPHAXVALUE";
    /**
     * Y方向权重
     */
    public static int ALPHAYVALUE;
    public static String KEY_ALPHAYVALUE = "KEY_ALPHAYVALUE";
    /**
     * 腐蚀膨胀核大小
     */
    public static int DEKEENELVALUE;
    public static String KEY_DEKEENELVALUE = "KEY_DEKEENELVALUE";
    /**
     * 二值化阈值
     */
    public static int THRESHOLDVALUE;
    public static String KEY_THRESHOLDVALUE = "KEY_THRESHOLDVALUE";


    //合格证打印机BX2
    public static int MYVID_PRINT = 4070, MYPID_PRINT = 33054;
    /**
     * 实达打印机
     */
    public static int MYVID_START = 7107, MYPID_START = 3;
    /**
     * 快检服务最后一次公告更新时间key
     */
    public static final String LASTUPDATATIME_KJFWTASKMSG = "LASTUPDATATIME_KJFWTASKMSG";
    public static final String controBitmapNocardFileName = "ControBitmapNoCard";
    /**
     * 外接胶体金模块的检测区域参数<p>
     * {@link #drowrectheight} 所画的红色框的高<p>
     * {@link #drowrectwidth} 所画的红色框的宽<p>
     * {@link #cardSpacing} 两个通道的间距，三个卡条之间的距离是一样的<p>
     */

    public static int drowrectheight = 20, drowrectwidth = 100, cardSpacing = 60;
    public static final String KEY_DROWRECTHEIGHT = "KEY_DROWRECTHEIGHT", KEY_DROWRECTWIDTH = "KEY_DROWRECTWIDTH", KEY_CARDSPACING = "KEY_CARDSPACING";


    /**
     * {@link #KEY_CLEN}  盐酸克伦特罗<p>
     * {@link #KEY_RAC} 莱克多巴胺<p>
     * {@link #KEY_SAL} 沙丁胺醇 <p>
     */
    public static final String KEY_CLEN = "KEY_CLEN", KEY_RAC = "KEY_RAC", KEY_SAL = "KEY_SAL";

    /**
     * 合格证打印模板
     */
    public static int PRINTMOUDLE = 1;
    public static final String KEY_PRINTMOUDLE = "KEY_PRINTMOUDLE";
    /**
     * 蓝牙打印机地址
     */
    public static String BLEPRINTDEV_MAC;
    public static final String KEY_BLEPRINTDEV_MAC = "KEY_BLEPRINTDEV_MAC";

    /**
     * 镇江平台版本 基础数据最后的更新时间
     */
    public static String KEY_LASTUPDATATIME_ZHENGJIANG = "KEY_LASTUPDATATIME_ZHENGJIANG";

    /**
     * 合格证打印机
     */
    public static final int MVENDORID = 11575, MPRODUCTID = 33751;
    /*
     * 硬件升级Ymodel相关
     * */
    public static int sCountPro = 0;
    public static int sCurrentPro = 0;
    /**
     * 胶体金定量版本的四次方程的参数 Axxxx+Bxxx+Cxx+Dx+E=y
     */
    public static final String QUERYAKEY = "QUERYAKEY";
    public static final String QUERYBKEY = "QUERYBKEY";
    public static final String QUERYCKEY = "QUERYCKEY";
    public static final String QUERYDKEY = "QUERYDKEY";
    public static final String QUERYEKEY = "QUERYEKEY";
    /**
     * 广西快检车 仪器检测类型，默认是 1
     * 3种类型：1（农药残留快检设备）、2（兽药残留快检设备）、3（其它快检设备)
     */
    public static int DEVICE_TYPE_GUANGXIKJC = 1;
    public static String KEY_DEVICE_TYPE_GUANGXIKJC = "KEY_DEVICE_TYPE_GUANGXIKJC";

    /**
     * 真菌读数扫描状态
     * 0：就绪状态 1：扫描中  2：扫描完成
     */
    public static int zjds_teststate = 0;

    /**
     * 通道调整里面框的大小
     */
    public static int zjds_width = 26;
    public static int zjds_height = 90;

    /**
     * 真菌读数通道设置 调整数值
     */
    public static int adjustValue;
    public static String KEYADJUSTVALUE = "keyadjustValue";

    public static String PATH_USERBEAN = "/data/data/" + BuildConfig.APPLICATION_ID + "/platfromuser.json";
    public static String PATH_REGISTERBEAN = "/data/data/" + BuildConfig.APPLICATION_ID + "/platfromregister.json";
    /**
     * 平台相关 之前设计接入平台可选择，后面改为了不可选择
     */
    @Deprecated
    public static String KEY_PLATFORM_TAG = "KEY_PLATFORM_TAG";


    /**
     * 0 使用本地默认基础数据库<p>
     * 1 仪器物联网 <p>
     * 2 使用快检云平台 <p>
     * 3 安徽省级平台 <p>
     * 4 洪江市（浙江甲骨文超级码科技股份有限公司）<p>
     * 5 镇江 智慧农贸平台 <p>
     * 6 新疆农产品质量安全追溯信息平台 <p>
     * 7 广西农产品质量安全监管与追溯信息平台 <p>
     * 8 精讯云平台 <p>
     * 9 重庆市市场监督管理局食品安全快速检测信息系统 <p>
     * 10 广西快检车平台 <p>
     * 11 江苏越城平台 <p>
     * 12 安徽省食品安全快速检测信息共享平台 <p>
     * 13 中机中联工程有限公司 <p>
     * 14 三台县农业局 <p>
     * 15 山东农业局 改版接口（以前曹县费县） <p>
     * 16 杭州健鹭科技有限公司  <p>
     * 17 雨湖区智慧监管平台  <p>
     * 18 广东省食药监企业云平台版本  <p>
     * 19 云南航凌科技有限公司版本  <p>
     * 20 锐帆食品检测版本  <p>
     * 21 南京农口版本  <p>
     * 22 襄阳市竹叶山农产品批发市场版本  <p>
     * 23 南普检察院管理平台版本  <p>
     * 24 江苏瘦肉精监管系统版本  <p>
     * 25 浙江深圳计量局版本  <p>
     * 26 衢州市中检浙江质量管理平台版  <p>
     * 27 苏州市市场局食用农产品系统平台版本(苏州亿通科技)  <p>
     * 28 三水区食品抽检管理平台  <p>
     * 29 中仑智慧市场溯源平台  <p>
     * 30 新疆田园信息系统  <p>
     * 31 食品安全快速检测信息共享平台  <p>
     * 32 福建宏泰博思  <p>
     * 33 北京和为永泰版本  <p>
     * 34 赣州快检智慧监管平台  <p>
     * 35 农安云电子合格证平台  <p>
     * 36 佛山市高明区市场监管信息平台版本  <p>
     * 37 DBQT快检数据服务云平台  <p>
     * 38 嵊州校食安监管平台  <p>
     * 39 农安云电子合格证平台 （新接口） <p>
     * 40 山东菜市场  <p>
     * 41 福州宏泰分析技术有限年公司  <p>
     * 42 京喜拼拼  <p>
     * 43 慧管家  <p>
     * 44 甘肃农委  <p>
     */
    public static int PLATFORM_TAG;
    /**
     * 关闭干式农残防造假功能 默认是关闭
     */
    public static boolean OFFANTIFAKE = false;
    public static String KEY_OFFANTIFAKE = "KEY_OFFANTIFAKE";


    /**
     * 关闭干式农残防造假功能后，快速得出一个结果
     */
    public static boolean NC_QUICKGETDATA = false;
    public static String KEY_NC_QUICKGETDATA = "KEY_OFFANTIFAKE";


    public static final String KEY_TOKEN_YQWLW = "KEY_TOKEN_YQWLW";

    /**
     * 仪器物联网平台的token
     */
    public static String TOKEN_YQWLW;
    /**
     * 仪器物联网基础数据更新时间KEY
     */
    public static final String KEY_LASTUPDATA_TIME_SAMPLE33_YQWLW = "KEY_LASTUPDATA_TIME_SAMPLE33_YQWLW", KEY_LASTUPDATA_TIME_SAMPLE_YQWLW = "KEY_LASTUPDATA_TIME_SAMPLE_YQWLW", KEY_LASTUPDATA_TIME_STANDARD_YQWLW = "KEY_LASTUPDATA_TIME_STANDARD_YQWLW";
    /**
     * 仪器物联网基础数据更新时间
     */
    public static String LASTUPDATA_TIME_SAMPLE33_YQWLW, LASTUPDATA_TIME_SAMPLE_YQWLW, LASTUPDATA_TIME_STANDARD_YQWLW;


    /**
     * 快检云服务基础数据更新时间KEY
     */
    public static final String KEY_LASTUPDATA_TIME_FOOD_KJFW = "KEY_LASTUPDATA_TIME_FOOD_KJFW", KEY_LASTUPDATA_TIME_STANDARD_KJFW = "KEY_LASTUPDATA_TIME_STANDARD_KJFW", KEY_LASTUPDATA_TIME_ITEM_KJFW = "KEY_LASTUPDATA_TIME_ITEM_KJFW", KEY_LASTUPDATA_TIME_FOODITEM_KJFW = "KEY_LASTUPDATA_TIME_FOODITEM_KJFW", KEY_LASTUPDATA_TIME_LAWS_KJFW = "KEY_LASTUPDATA_TIME_LAWS_KJFW", KEY_LASTUPDATA_TIME_REGULATORY_KJFW = "KEY_LASTUPDATA_TIME_REGULATORY_KJFW", KEY_LASTUPDATA_TIME_BUSINESS_KJFW = "KEY_LASTUPDATA_TIME_BUSINESS_KJFW", KEY_LASTUPDATA_TIME_DEVICEITEM_KJFW = "KEY_LASTUPDATA_TIME_DEVICEITEM_KJFW";

    /**
     * 快检云服务基础数据更新时间
     */
    public static String LASTUPDATA_TIME_FOOD_KJFW, LASTUPDATA_TIME_STANDARD_KJFW, LASTUPDATA_TIME_ITEM_KJFW, LASTUPDATA_TIME_FOODITEM_KJFW, LASTUPDATA_TIME_LAWS_KJFW, LASTUPDATA_TIME_REGULATORY_KJFW, LASTUPDATA_TIME_BUSINESS_KJFW, LASTUPDATA_TIME_DEVICEITEM_KJFW;


    /**
     * 分光光度透光率限制值
     * 当透光率小于0.97时认为是放入比色皿
     */
    public static float FGLIMITVALUE_LOW = 0.97f;

    /**
     * 当大于1.01时认为通道异常  安徽省局的改成1.5f
     */
    public static float FGLIMITVALUE_HEIGHT() {
        if (PLATFORM_TAG == 12) {
            return 1.5f;
        }
        return 1.01f;
    }


    /**
     * @return 分光光度抑制率法的对照值限制 默认必须大于0.15 安徽省局修改为0.01
     */
    public static float FGCONTROVALUE_YIZHILVFA() {
        if (PLATFORM_TAG == 12) {
            return 0.01f;
        }
        return 0.15F;
    }


    /**
     * 仪器百度定位数据 经纬度
     */
    public static double LONTITUDE, LATITUDE;
    /**
     * 仪器百度定位数据  地址
     */
    public static String ADDR_WF = "";


    /**
     * 胶体金 外接 摄像头模块
     */
    public static int MYVID_P_OUT = 1047, MYPID_P_OUT = 20497;

    /**
     * 胶体金扫描模块
     */
    public static int MYVID_S = 1155, MYPID_S = 22336;


    /**
     * 胶体金摄像头模块
     */
    public static int MYVID_P = 1046, MYPID_P = 20497;

    /**
     * 胶体金扫描模块8通道 9000 (6260I) 真菌毒素
     */
    public static int MYVID = 1314, MYPID = 22338;

    /**
     * 重金属模块
     */
    public static int MYVID_ZJS = 1659, MYPID_ZJS = 8963;

    /**
     * 多联卡胶体金  导轨
     */
    public static int MYVID_MULTICARD = 1155, MYPID_MULTICARD = 22338;
    /**
     * 多联卡胶体金  摄像头
     */
    public static int MYVID_MULTICARD_CAM_OLD = 28953, MYPID_MULTICARD_CAM_OLD = 10277;
    public static int MYVID_MULTICARD_CAM = 7119, MYPID_MULTICARD_CAM = 2825;

    /**
     * 多联卡胶体金 旧的摄像头pid vid
     */
    public static int MYVID_MULTICARD_CAM1 = 7119, MYPID_MULTICARD_CAM1 = 2825;
    /**
     * uvc摄像头胶体金 开机第一次进入软件
     * 请检查摄像头成像是否正常，如果显示不正常会导致测试结果不准确
     */
    public static boolean ISADJUST_MULTICARD_CAM = false;

    public static int MYVID_MYYG = 1659, MYPID_MYYG = 8963;
    /**
     * 摄像头读取图片的分辨率大小 1280*720  640*480
     * 修改这里的同时还需要修改 src/main/cpp/ImageProc.h 里面的{#IMG_HEIGHT #IMG_WIDTH}
     */
    public static int IMG_WIDTH = 640, IMG_HEIGHT = 480;
    /**
     * 仪器列表一页显示行数
     */
    public static int page_num = 100;

    /**
     * 仪器列表一页显示行数
     */
    public static int page_num_unit = 15;


    /**
     * 保存当前用户的key
     */
    public static final String KEY_USERINFOR_JSON = "KEY_USERINFOR_JSON";

    /**
     * 当前登录用户 仪器本身
     */
    //public static User NOWUSER;

    /**
     * 免疫荧光流水号
     */
    public static int MYYG_SEARINUM;
    /**
     * 胶体金流水号key
     */
    public static String KEY_MYYG_SEARINUM = "key_myyg_searinum";

    /**
     * 胶体金流水号
     */
    public static int JTJ_SEARINUM;
    /**
     * 胶体金流水号key
     */
    public static String KEY_JTJ_SEARINUM = "key_jtj_searinum";

    /**
     * 江苏瘦肉精监管平台 样品编号
     */
    public static int JSSRJSAMPLENUMBER;
    /**
     * 江苏瘦肉精监管平台 样品编号key
     */
    public static String KEY_JSSRJSAMPLENUMBER = "KEY_JSSRJSAMPLENUMBER";


    /**
     * 重金属流水号
     */
    public static int ZJS_SEARINUM;
    public static String KEY_ZJS_SEARINUM = "key_zjs_searinum";


    /**
     * 分光光度流水号
     */
    public static int FGGD_SEARINUM;
    public static String KEY_FGGD_SEARINUM = "key_fggd_searinum";

    /**
     * 干式农残流水号
     */
    public static int GSNC_SEARINUM;
    public static String KEY_GSNC_SEARINUM = "key_gsnc_searinum";

    /**
     * 真菌毒素 胶体金多联卡
     */
    //public static UsbControl mControl;
    /**
     * 白平衡key
     */
    public static final String BLANCE_VALUEKEY = "Blance_Valuekey";

    /**
     * 串口相关 两种Android板子的串口名称是不一样的，具体看代码
     */
    public static final String DATA_SERIAPort = "/dev/ttyS4", NEW_DATA_SERIAPort = "/dev/ttyS0", PRINT_SERIAPort = "/dev/ttyS1", PRINT_SERIALBaudRate = "9600", DATA_SERIALBaudRate = "115200";


    /**
     * 请求打开文件浏览器时的 请求码
     * {@link #FOLDER_REQUESTCODE} 文件夹 <p>
     * {@link #FILE_REQUESTCODE} 文件 <p>
     */
    public static int FOLDER_REQUESTCODE = 1, FILE_REQUESTCODE = 2;


    /**
     * 干式农残对照值和对照值时间 key
     */
    public static String KEY_NC_CONTROLTIME = "KEY_NC_CONTROLTIME", KEY_NC_CONTROLVALUE = "KEY_NC_CONTROLVALUE";
    /**
     * {@link #NC_CONTROLTIME}干式农残对照值时间<p>
     * {@link #NC_CONTROL_VALUE}干式农残对照值<p>
     */
    public static long NC_CONTROLTIME;
    public static int NC_CONTROL_VALUE = 0;

    //分光光度 标准曲线法 对照值和对照值时间
    /*public static String KEY_FGGD_BIAOHUN_CONTROL_VALUE = "KEY_FGGD_BIAOHUN_CONTROL_VALUE";
    public static float FGGD_BIAOZHUN_CONTROL_VALUE=0;
    public static String KEY_FGGD_BIAOZHUN_CONTROLTIME = "KEY_FGGD_BIAOZHUN_CONTROLTIME";
    public static long FGGD_BIAOZHUN_CONTROLTIME;*/
    //分光光度 抑制率法 对照值和对照值时间
    /*public static String KEY_FGGD_YIZHILV_CONTROL_VALUE = "KEY_FGGD_YIZHILV_CONTROL_VALUE";
    public static float FGGD_YIZHILV_CONTROL_VALUE=0;
    public static String KEY_FGGD_YIZHILV_CONTROLTIME = "KEY_FGGD_YIZHILV_CONTROLTIME";
    public static long FGGD_YIZHILV_CONTROLTIME;*/


    /**
     * {@link #ISPRINTQRCODE}是否打印二维码<p>
     * {@link #KEY_MCBPRINTQRCODE}是否打印二维码<p>
     */
    public static boolean ISPRINTQRCODE;
    public static final String KEY_MCBPRINTQRCODE = "KEY_MCBPRINTQRCODE";

    /**
     * {@link #PRINTSELF}是否自动打印<p>
     * {@link #KEY_MCBPRINTSELF}是否自动打印<p>
     */
    public static boolean PRINTSELF;
    public static final String KEY_MCBPRINTSELF = "KEY_MCBPRINTSELF";

    /**
     * {@link #ISDIRECTION}小票是否镜面打印<p>
     * {@link #KEY_ISDIRECTION}小票是否镜面打印KEY<p>
     */
    public static boolean ISDIRECTION_RECEIPT;
    public static final String KEY_ISDIRECTION_RECEIPT = "KEY_ISDIRECTION_RECEIPT";

    /**
     * {@link #ISDIRECTION}是否镜面打印<p>
     * {@link #KEY_ISDIRECTION}是否镜面打印KEY<p>
     */
    public static boolean ISDIRECTION;
    public static final String KEY_ISDIRECTION = "KEY_ISDIRECTION";

    /**
     * {@link #PRINTCERTIFICATESELF}是否自动打印<p>
     * {@link #KEY_PRINTCERTIFICATESELF}是否自动打印KEY<p>
     */
    public static boolean PRINTCERTIFICATESELF;
    public static final String KEY_PRINTCERTIFICATESELF = "KEY_PRINTCERTIFICATESELF";

    /**
     * {@link #UPLOADSELF}是否自动上传<p>
     * {@link #KEY_MCBUPLOADSELF}是否自动上传KEY<p>
     */
    public static boolean UPLOADSELF;
    public static final String KEY_MCBUPLOADSELF = "KEY_MCBUPLOADSELF";

    /**
     * 隐藏式功能
     * {@link #FORCEUPLOAD}强制自动上传<p>
     * {@link #KEY_FORCEUPLOAD}强制自动上传<p>
     */
    public static boolean FORCEUPLOAD;
    public static final String KEY_FORCEUPLOAD = "KEY_FORCEUPLOAD";

    /**
     * {@link #DEVICENUM}仪器编号<p>
     * {@link #KEY_DEVICENUM}仪器编号KEY<p>
     */
    public static String DEVICENUM;
    public static final String KEY_DEVICENUM = "KEY_DEVICENUM";


    /**
     * {@link #ISREMBERUSERNAME}是否记住用户密码（登录页面）<p>
     * {@link #KEY_REMBERUSERNAME}<p>
     */
    public static boolean ISREMBERUSERNAME;
    public static String KEY_REMBERUSERNAME = "KEY_REMBERUSERNAME";


    public static String KEY_ZJDS_TEMP = "key_zjds_temp";


    /**
     * DY6310 重金属检测仪 <p>
     * 获取设置模块编号<p>
     *
     * @param s 模块编号
     * @return
     */
    public static byte[] SEETING_NUM_6310(String s) {
        //长度固定为20个字节 不足20的用0补全
        byte[] bytes = s.getBytes();
        if (bytes.length > 20) {
            ArmsUtils.snackbarText("超过指定数据长度");
            return null;
        }

        byte[] data = ByteUtils.byteMerger(bytes, new byte[20 - bytes.length]);
        int crcsun = CRC8Util.byteCheckSum(ByteUtils.byteMerger(new byte[]{0x40, 0x14, 0x00}, data));
        //crcsun&=0xff;//取低八位
        //byte b = (byte) ((crcsun >> 24) & 0xFF);
        byte[] bytes1 = ByteUtils.byteMerger(new byte[]{0x7e, 0x40, 0x14, 0x00}, data);
        byte[] bytes2 = ByteUtils.byteMerger(bytes1, new byte[]{(byte) crcsun});
        byte[] bytes3 = ByteUtils.byteMerger(bytes2, new byte[]{(byte) 0xaa});
        return bytes3;
    }

    /**
     * DY6310 重金属检测仪<p>
     * 给硬件同步时间 <p>
     *
     * @param remainingtime 时间 秒
     * @return
     */
    public static byte[] SYNCHRONISEDTIME(int remainingtime) {

        return new byte[]{0x7e, 0x42, 0x01, 0x00, (byte) remainingtime, (byte) CRC8Util.byteCheckSum(new byte[]{0x42, 0x01, 0x00, (byte) remainingtime}), (byte) 0xaa};
    }


    /**
     * DY6310 重金属检测仪 <p>
     * 开始测试 <p>
     */
    public static final byte[] STARTTEST_6310 = new byte[]{0x7E, 0x14, 0x00, 0x00, 0x01, (byte) 0xAA};

    public static final byte[] UPDATA_6310 = new byte[]{0x7E, (byte) 0x80, 0x00, 0x00, (byte) 0x80, (byte) 0xAA};


    /**
     * DY3500P <p>
     * 胶体金模块 <p>
     * {@link #INSTRUMENT_INFORMATION_REQUEST}仪器信息请求<p>
     * {@link #LITHIUM_BATTERY_VOLTAGE_REQUEST}锂电池电压数值请求<p>
     * {@link #COLLAURUM_ENT_SCANNING_REQUEST_P}进卡扫描<p>
     * {@link #COLLAURUM_ENT_SCANNING_REQUEST_S}进卡扫描<p>
     * {@link #COLLAURUM_OUT_REQUEST_P}出卡<p>
     * {@link #COLLAURUM_OUT_REQUEST_S}出卡<p>
     * {@link #COLLAURUM_ENT_REQUEST_P}进卡不扫描<p>
     * {@link #COLLAURUM_ENT_REQUEST_S}进卡不扫描<p>
     * {@link #COLLAURUM_STATE_REQUEST_P}卡状态请求<p>
     * {@link #COLLAURUM_STATE_REQUEST_S}卡状态请求<p>
     * {@link #COLLAURUM_DATA_REQUEST_P}数据请求<p>
     * {@link #COLLAURUM_DATA_REQUEST_S}数据请求<p>
     * {@link #COLLAURUM_CALIBRATION_REQUEST}校准请求<p>
     * {@link #COLLAURUM_NUMBER_ASK_P}读取模块编号请求<p>
     * {@link #COLLAURUM_NUMBER_ASK_S}读取模块编号请求<p>
     **/
    public static final byte[] LITHIUM_BATTERY_VOLTAGE_REQUEST = new byte[]{0x7e, 0x30, 0x00, 0x00, 0x30, (byte) 0xaa}, INSTRUMENT_INFORMATION_REQUEST = new byte[]{0x7e, 0x00, 0x02, 0x00, 0x00, 0x00, 0x02, (byte) 0xaa}, COLLAURUM_ENT_SCANNING_REQUEST_P = new byte[]{0x7E, 0x11, 0x01, 0x00, 0x01, (byte) FindCRC(new byte[]{0x11, 0x01, 0x00, 0x01}), 0x7E}, COLLAURUM_ENT_SCANNING_REQUEST_S = new byte[]{0x7E, 0x11, 0x00, 0x01, 0x01, 0x13, 0x7E}, COLLAURUM_OUT_REQUEST_P = new byte[]{0x7E, 0x11, 0x01, 0x00, 0x02, (byte) FindCRC(new byte[]{0x11, 0x01, 0x00, 0x02}), 0x7E}, COLLAURUM_OUT_REQUEST_S = new byte[]{0x7E, 0x11, 0x00, 0x01, 0x02, 0x14, 0x7E}, COLLAURUM_ENT_REQUEST_P = new byte[]{0x7E, 0x11, 0x01, 0x00, 0x03, (byte) FindCRC(new byte[]{0x11, 0x01, 0x00, 0x03}), 0x7E}, COLLAURUM_ENT_REQUEST_S = new byte[]{0x7E, 0x11, 0x00, 0x01, 0x03, 0x15, 0x7E}, COLLAURUM_STATE_REQUEST_P = new byte[]{0x7E, 0x13, 0x00, 0x00, (byte) FindCRC(new byte[]{0x13, 0x00, 0x00}), 0x7E}, COLLAURUM_STATE_REQUEST_S = new byte[]{0x7E, 0x13, 0x00, 0x00, 0x13, 0x7E}, COLLAURUM_DATA_REQUEST_P = new byte[]{0x7E, 0x15, 0x00, 0x00, (byte) 0xcb, 0x7E}, COLLAURUM_DATA_REQUEST_S = new byte[]{0x7E, 0x15, 0x00, 0x00, 0x15, 0x7E}, COLLAURUM_CALIBRATION_REQUEST = new byte[]{0x7e, 0x17, 0x00, 0x00, 0x17, 0x7e}, COLLAURUM_NUMBER_ASK_P = new byte[]{0x7e, 0x1b, 0x00, 0x00, (byte) FindCRC(new byte[]{0x1b, 0x00, 0x00}), 0x7e}, COLLAURUM_NUMBER_ASK_S = new byte[]{0x7e, 0x1b, 0x00, 0x00, 0x1b, 0x7e};


    /**
     * 设置模块编号
     *
     * @param i 模块编号
     * @return
     */
    public static byte[] COLLAURUM_NUMBER_SET_P(int i) {
        return new byte[]{0x7e, 0x19, 0x01, 0x00, (byte) i, (byte) FindCRC(new byte[]{0x19, 0x01, 0x00, (byte) i}), 0x7e};
    }

    /**
     * 设置模块编号
     *
     * @param i 模块编号
     * @return
     */
    public static byte[] COLLAURUM_NUMBER_SET_S(int i) {
        return new byte[]{0x7e, 0x19, 0x00, 0x01, (byte) i, (byte) (0x19 + 0x00 + 0x01 + i), 0x7e};
    }

    /**
     * 移动导轨到相应通道进行拍照
     *
     * @param i 通道编号
     * @return
     */
    public static byte[] MOVE(int i) {
        return new byte[]{0x7e, 0x12, 0x01, 0x00, (byte) i, (byte) FindCRC(new byte[]{0x12, 0x01, 0x00, (byte) i}), (byte) 0xaa};
    }

    /**
     * DY6270(6260i） 胶体金读卡仪<P>
     * 获取电机运行步数<P>
     *
     * @return 发送给硬件的byte数组
     */
    public static byte[] getStep() {
        byte[] bytes = {0x7e, 0x16, 0x00, 0x00, (byte) FindCRC(new byte[]{0x16, 0x00, 0x00}), (byte) 0xaa};
        return bytes;
    }

    /**
     * DY6270(6260i） 胶体金读卡仪<P>
     * 获取电机运行步数<P>
     *
     * @param list
     * @return 发送给硬件的byte数组
     */
    public static byte[] setStep(List<Integer> list) {
        byte[] bytes_data = new byte[]{};
        for (int i = 0; i < list.size(); i++) {
            byte[] bytes1 = ByteUtils.byteMerger(new byte[]{(byte) (i + 1)}, ByteUtils.intToBytes1(list.get(i)));
            bytes_data = ByteUtils.byteMerger(bytes_data, bytes1);
        }

        byte[] bytes_cmd = new byte[]{0x14};
        byte[] bytes_length = ByteUtils.intToBytes2_LH(bytes_data.length, 2);
        byte[] bytes_CMD_LEN_DATA = ByteUtils.byteMerger(ByteUtils.byteMerger(bytes_cmd, bytes_length), bytes_data);
        byte[] bytes_crcvalue = new byte[]{(byte) FindCRC(bytes_CMD_LEN_DATA)};

        byte[] bytes_head = new byte[]{0x7e};
        byte[] bytes_end = new byte[]{(byte) 0xaa};
        byte[] bytes = ByteUtils.byteMerger(
                ByteUtils.byteMerger(
                        ByteUtils.byteMerger(
                                ByteUtils.byteMerger(
                                        ByteUtils.byteMerger(
                                                bytes_head, bytes_cmd), bytes_length), bytes_data), bytes_crcvalue), bytes_end);
        LogUtils.d(bytes);
        return bytes;
    }

    /**
     * DY6270(6260i） 胶体金读卡仪<P>
     * 获取电机运行步数<P>
     *
     * @return 发送给硬件的byte数组
     */
    public static byte[] saveSeting() {
        byte[] bytes = {0x7e, 0x18, 0x01, 0x00, 0x01, (byte) FindCRC(new byte[]{0x18, 0x01, 0x00, 0x01}), (byte) 0xaa};
        return bytes;
    }

    /**
     * 获取摄像头偏移参数
     */
    public static byte[] COLLAURUM_GET_ARGMENT = {0x7E, 0x20, 0x00, 0x00, 0x79, 0x7E};

    /**
     * 设置摄像头偏移参数
     *
     * @param horizontal_d 横向偏移量
     * @param vertical_d   纵向偏移量
     * @return 发送给硬件的byte数组
     */
    public static byte[] COLLAURUM_SET_ARGMENT(byte horizontal_d, byte vertical_d) {
        return new byte[]{0x7E, 0x1d, 0x02, 0x00, (byte) horizontal_d, (byte) vertical_d, (byte) FindCRC(new byte[]{0x1d, 0x02, 0x00, (byte) horizontal_d, (byte) vertical_d}), 0x7E};
    }


    /**
     * V9000 真菌毒素<p>
     * {@link #STARTSCANNING_JTJSCANNER} 开始扫描<p>
     * {@link #CALIBRATION_JTJSCANNER} 白平衡校准<p>
     */
    public static final byte[] STARTSCANNING_JTJSCANNER = new byte[]{0x1b, 0x1c, 0x00}, CALIBRATION_JTJSCANNER = new byte[]{0x1b, 0x1c, 0x03};


    /**
     * V9000 真菌毒素<P>
     * 设置温度命令<P>
     *
     * @param i 需要设置的温度
     * @return 发送给硬件的byte数组
     */
    public static byte[] getTemByte(int i) {
        byte[] bytes = {0x7e, 0x18, 0x01, 0x00, (byte) i, (byte) FindCRC(new byte[]{0x18, 0x01, 0x00, (byte) i}), (byte) 0xaa};
        return bytes;
    }

    /**
     * V9000 真菌毒素
     * 获取温度命令
     */
    public static byte[] zjds_asktemp_s = {0x7e, 0x1a, 0x00, 0x00, 0x7a, (byte) 0xaa};

    /**
     * V9000 真菌毒素
     * 当前所选用的方案的id的key
     */
    public static final String KEY_PROGRAM = "KEY_PROGRAM";

    /**
     * 分光光度模块 <P>
     * {@link #SPECTRAL_AD_CALIBRATION_REQUEST_DY1000} DY1000AD值校准请求<P>
     * {@link #SPECTRAL_AD_CALIBRATION_REQUEST_1 } AD值校准请求<P>
     * {@link #SPECTRAL_AD_CALIBRATION_REQUEST_2} AD值校准请求<P>
     * {@link #SPECTRAL_DATA_REQUEST_ZXYTJ} 数据请求<P>
     * {@link #SPECTRAL_DATA_REQUEST_DY1000} DY1000数据请求<P>
     **/
    public static final byte[] SPECTRAL_AD_CALIBRATION_REQUEST_DY1000 = new byte[]{0x7e, 0x11, 0x01, 0x00, 0x32, 0x44, 0x7e}, SPECTRAL_AD_CALIBRATION_REQUEST_1 = new byte[]{0x7e, 0x11, 0x02, 0x00, 0x01, 0x32, 0x46, (byte) 0xaa}, SPECTRAL_AD_CALIBRATION_REQUEST_2 = new byte[]{0x7e, 0x11, 0x02, 0x00, 0x02, 0x32, 0x47, (byte) 0xaa}, SPECTRAL_DATA_REQUEST_ZXYTJ = new byte[]{0x7e, 0x15, 0x00, 0x00, 0x15, (byte) 0xaa}, SPECTRAL_DATA_REQUEST_DY1000 = new byte[]{0x7e, 0x15, 0x00, 0x00, 0x15, 0x7e};


    /**
     * 干式农残模块<P>
     * {@link #DRYFARMINGINCOMPLETE_DATA_REQUEST}数据请求<P>
     * {@link #DRYFARMINGINCOMPLETE_TEMPERATURE_REQUEST}温度查询请求<P>
     **/
    public static final byte[] DRYFARMINGINCOMPLETE_DATA_REQUEST = new byte[]{0x7e, 0x20, 0x00, 0x00, 0x20, (byte) 0xaa}, DRYFARMINGINCOMPLETE_TEMPERATURE_REQUEST = new byte[]{0x7e, 0x26, 0x00, 0x00, 0x26, (byte) 0xaa};


    /**
     * 温度设置指令
     *
     * @param i
     * @return
     */
    public static byte[] DRYFARMINGINCOMPLETE_TEMPERATURE_BOUND_REQUEST(int i) {
        byte[] bytes = {0x7e, 0x28, 0x02, 0x00, (byte) (i - 1), (byte) i, (byte) (42 + 2 * i - 1), 0x7e};
        return bytes;
    }


    /**
     * 打印机初始化
     */
    public static byte[] CMD_RESET = {0x1b, 0x40};
    /**
     * 字体放大
     */
    public static byte[] CMD_SETDOUBLESIZE = {0x1c, 0x21, 0x0c};
    /**
     * 居中打印
     */
    public static byte[] CMD_CENTER = {0x1b, 0x61, 0x01};
    /**
     * 二维码
     */
    public static byte[] QRCODESTART = {0x1d, 0x5a, 0x02, 0x1b, 0x5a, 0x00, 0x4c, 0x04};
    /**
     * 行间距
     */
    public static byte[] CMD_LINEDISTANCE = {0x1B, 0x31, 0x5};
    /**
     * 设置颠倒打印
     */
    public static byte[] CMD_REVERSE = {0x1B, 0x7b, 0x02};
    /**
     * 取消颠倒打印
     */
    public static byte[] CMD_NOREVERSE = {0x1B, 0x7b, 0x00};
    /**
     * 添加下划线
     */
    //public static byte[] CMD_UNDERSCORE = {0x1B, 0x2d, 0x01};
    /**
     * 取消下划线
     */
    //public static byte[] CMD_CANCLEUNDERSCORE = {0x1B, 0x2d, 0x00};


    /**
     * 获取重金属检测的流水号
     *
     * @return 流水号
     */
    public static synchronized String getZJSSearinum() {
        //为0时需要初始化
        if (ZJS_SEARINUM == 0) {
            ZJS_SEARINUM = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_ZJS_SEARINUM, 0);
        }
        ZJS_SEARINUM++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_ZJS_SEARINUM, ZJS_SEARINUM);
        return getString(ZJS_SEARINUM);
    }

    /**
     * 获取胶体金检测的流水号
     *
     * @return 流水号
     */
    public static synchronized String getJtjSearinum() {
        //为0时需要初始化
        if (JTJ_SEARINUM == 0) {
            JTJ_SEARINUM = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_JTJ_SEARINUM, 0);
        }
        JTJ_SEARINUM++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_JTJ_SEARINUM, JTJ_SEARINUM);
        return getString(JTJ_SEARINUM);
    }

    /**
     * 获取免疫荧光检测的流水号
     *
     * @return 流水号
     */
    public static synchronized String getMyygSearinum() {
        //为0时需要初始化
        if (MYYG_SEARINUM == 0) {
            MYYG_SEARINUM = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_MYYG_SEARINUM, 0);
        }
        MYYG_SEARINUM++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_MYYG_SEARINUM, MYYG_SEARINUM);
        return getString(MYYG_SEARINUM);
    }

    /**
     * 江苏瘦肉精平台版本的获取胶体金流水号
     *
     * @return 流水号
     */
    public static synchronized String getJSSRJSampleNumber() {
        //为0时需要初始化
        if (JSSRJSAMPLENUMBER == 0) {
            JSSRJSAMPLENUMBER = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_JSSRJSAMPLENUMBER, 0);
        }
        JSSRJSAMPLENUMBER++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_JSSRJSAMPLENUMBER, JSSRJSAMPLENUMBER);
        return DataUtils.getNowtimeYYYYMMDD() + getString(JSSRJSAMPLENUMBER);
    }

    /**
     * 获取分光光度检测的流水号
     *
     * @return 流水号
     */
    public static synchronized String getFGGDSearinum() {
        //为0时需要初始化
        if (FGGD_SEARINUM == 0) {
            FGGD_SEARINUM = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_FGGD_SEARINUM, 0);
        }
        FGGD_SEARINUM++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_FGGD_SEARINUM, FGGD_SEARINUM);
        return getString(FGGD_SEARINUM);
    }

    /**
     * 获取干式农残检测的流水号
     *
     * @return 流水号
     */
    public static synchronized String getGSNCSearinum() {
        //为0时需要初始化
        if (GSNC_SEARINUM == 0) {
            GSNC_SEARINUM = (int) SPUtils.get(MyAppLocation.myAppLocation, KEY_GSNC_SEARINUM, 0);
        }
        GSNC_SEARINUM++;
        SPUtils.put(MyAppLocation.myAppLocation, KEY_GSNC_SEARINUM, GSNC_SEARINUM);
        return getString(GSNC_SEARINUM);

    }

    /**
     * 将int 转换为5位数的流水号，不足5位用0补齐
     *
     * @param i 流水号（int）
     * @return 流水号（string）
     */
    private static String getString(int i) {
        String nums = "";
        if (0 <= i && i < 10) {
            nums = "0000" + i;
        } else if (10 <= i && i < 100) {
            nums = "000" + i;
        } else if (100 <= i && i < 1000) {
            nums = "00" + i;
        } else if (1000 <= i && i < 10000) {
            nums = "0" + i;
        } else if (10000 <= i && i < 100000) {
            nums = "" + i;
        }
        return nums;
    }

    /**
     * 初始化软件启动需要的全局变量
     *
     * @param application
     */
    public static void init(Application application) {


        Constants.JTJLINK = (String) SPUtils.get(application, Constants.KEY_JTJLINK, "");
        Constants.FGITEMLINK = (String) SPUtils.get(application, Constants.KEY_FGITEMLINK, "");
        Constants.FOOLINKNAME = (String) SPUtils.get(application, Constants.KEY_FOOLINKNAME, "");
        Constants.ZJSLINK = (String) SPUtils.get(application, Constants.KEY_ZJSLINK, "");

        Constants.CHECKPEOPLE = (String) SPUtils.get(application, Constants.KEY_CHECKPEOPLE, "");
        Constants.MINWIDTH = (int) SPUtils.get(application, Constants.KEY_MINWIDTH, 250);
        Constants.MINHEIGHT = (int) SPUtils.get(application, Constants.KEY_MINHEIGHT, 40);
        Constants.LIMITANGLEA = (int) SPUtils.get(application, Constants.KEY_LIMITANGLEA, 10);
        Constants.LIMITANGLEB = (int) SPUtils.get(application, Constants.KEY_LIMITANGLEB, 80);
        Constants.LIMITCHANNL = (int) SPUtils.get(application, Constants.KEY_LIMITCHANNL, 20);
        Constants.MINWDIFFERENCEVALUE = (int) SPUtils.get(application, Constants.KEY_MINWDIFFERENCEVALUE, 10);
        Constants.MINHDIFFERENCEVALUE = (int) SPUtils.get(application, Constants.KEY_MINHDIFFERENCEVALUE, 10);

        //初始化一些保存本地的参数
        //X方向权重
        Constants.ALPHAXVALUE = (int) SPUtils.get(application, Constants.KEY_ALPHAXVALUE, 1);
        //Y方向权重
        Constants.ALPHAYVALUE = (int) SPUtils.get(application, Constants.KEY_ALPHAYVALUE, 1);
        // 腐蚀膨胀核大小
        Constants.DEKEENELVALUE = (int) SPUtils.get(application, Constants.KEY_DEKEENELVALUE, 5);
        //二值化阈值
        Constants.THRESHOLDVALUE = (int) SPUtils.get(application, Constants.KEY_THRESHOLDVALUE, 20);

        //定制平台编号
        Constants.PLATFORM_TAG = (int) SPUtils.get(application, Constants.KEY_PLATFORM_TAG, -1);
        if (Constants.PLATFORM_TAG == -1) {
            Constants.PLATFORM_TAG = BuildConfig.DEFAULT_PLATFORM_TAG;
        }

        //关闭干式农残防造假功能
        Constants.OFFANTIFAKE = (boolean) SPUtils.get(application, Constants.KEY_OFFANTIFAKE, false);
        //合格证打印模板
        Constants.PRINTMOUDLE = (int) SPUtils.get(application, Constants.KEY_PRINTMOUDLE, 1);
        //合格证打印机蓝牙地址
        Constants.BLEPRINTDEV_MAC = (String) SPUtils.get(application, Constants.KEY_BLEPRINTDEV_MAC, "");
        //是否打印二维码
        Constants.ISPRINTQRCODE = (boolean) SPUtils.get(application, Constants.KEY_MCBPRINTQRCODE, false);
        //是否自动打印
        Constants.PRINTSELF = (boolean) SPUtils.get(application, Constants.KEY_MCBPRINTSELF, false);
        //是否镜面打印
        Constants.ISDIRECTION = (boolean) SPUtils.get(application, Constants.KEY_ISDIRECTION, false);
        if (BuildConfig.FLAVOR.contains("TL330") || BuildConfig.FLAVOR.contains("TL680")) {
            //小票是否镜面打印
            Constants.ISDIRECTION_RECEIPT = (boolean) SPUtils.get(application, Constants.KEY_ISDIRECTION_RECEIPT, true);
        } else {
            //小票是否镜面打印
            Constants.ISDIRECTION_RECEIPT = (boolean) SPUtils.get(application, Constants.KEY_ISDIRECTION_RECEIPT, false);
        }

        //是否自动打印
        Constants.PRINTCERTIFICATESELF = (boolean) SPUtils.get(application, Constants.KEY_PRINTCERTIFICATESELF, false);

        //是否自动上传
        Constants.UPLOADSELF = (boolean) SPUtils.get(application, Constants.KEY_MCBUPLOADSELF, false);
        //是否强制自动上传
        Constants.FORCEUPLOAD = (boolean) SPUtils.get(application, Constants.KEY_FORCEUPLOAD, false);
        if (Constants.PLATFORM_TAG == 34 || Constants.PLATFORM_TAG == 39) {
            //是否强制自动上传
            Constants.FORCEUPLOAD = (boolean) SPUtils.get(application, Constants.KEY_FORCEUPLOAD, true);
            Constants.UPLOADSELF = (boolean) SPUtils.get(application, Constants.KEY_MCBUPLOADSELF, true);
        }

        //仪器编号
        Constants.DEVICENUM = (String) SPUtils.get(application, Constants.KEY_DEVICENUM, "");
        //是否记住用户（登录界面）
        Constants.ISREMBERUSERNAME = (boolean) SPUtils.get(application, Constants.KEY_REMBERUSERNAME, false);
        //干式农残对照值和对照值时间
        Constants.NC_CONTROLTIME = (long) SPUtils.get(application, Constants.KEY_NC_CONTROLTIME, 0L);
        Constants.NC_CONTROL_VALUE = (int) SPUtils.get(application, Constants.KEY_NC_CONTROLVALUE, 0);

        Constants.drowrectheight = (int) SPUtils.get(application, Constants.KEY_DROWRECTHEIGHT, 20);
        Constants.drowrectwidth = (int) SPUtils.get(application, Constants.KEY_DROWRECTWIDTH, 100);
        Constants.cardSpacing = (int) SPUtils.get(application, Constants.KEY_CARDSPACING, 60);
        //分光光度 标准曲线法 对照值和对照值时间
        // Constants.FGGD_BIAOZHUN_CONTROL_VALUE= (float) SPUtils.get(application,Constants.KEY_FGGD_BIAOHUN_CONTROL_VALUE,0f);
        // Constants.FGGD_BIAOZHUN_CONTROLTIME= (long) SPUtils.get(application,Constants.KEY_FGGD_BIAOZHUN_CONTROLTIME,0l);
        //分光光度 抑制率法 对照值和对照值时间
        //Constants.FGGD_YIZHILV_CONTROL_VALUE= (float) SPUtils.get(application,Constants.KEY_FGGD_YIZHILV_CONTROL_VALUE,0f);
        //Constants.FGGD_YIZHILV_CONTROLTIME= (long) SPUtils.get(application,Constants.KEY_FGGD_YIZHILV_CONTROLTIME,0l);

        if (Constants.PLATFORM_TAG == 1) {
            //仪器物联网用户token （仪器编码进行MD5加密  或者是项目的MD5值）
            Constants.TOKEN_YQWLW = (String) SPUtils.get(application, Constants.KEY_TOKEN_YQWLW, "");
            Constants.LASTUPDATA_TIME_SAMPLE_YQWLW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_SAMPLE_YQWLW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_SAMPLE33_YQWLW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_SAMPLE33_YQWLW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_STANDARD_YQWLW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_STANDARD_YQWLW, "2000-01-16 23:23:23");
        }

        if (Constants.PLATFORM_TAG == 2) {
            //快检云服务
            Constants.LASTUPDATA_TIME_FOOD_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_FOOD_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_STANDARD_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_STANDARD_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_ITEM_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_ITEM_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_FOODITEM_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_FOODITEM_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_LAWS_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_LAWS_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_REGULATORY_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_REGULATORY_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_BUSINESS_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_BUSINESS_KJFW, "2000-01-16 23:23:23");
            Constants.LASTUPDATA_TIME_DEVICEITEM_KJFW = (String) SPUtils.get(application, Constants.KEY_LASTUPDATA_TIME_DEVICEITEM_KJFW, "2000-01-16 23:23:23");
        }
        if (Constants.PLATFORM_TAG == 10) {
            Constants.DEVICE_TYPE_GUANGXIKJC = (int) SPUtils.get(application, Constants.KEY_DEVICE_TYPE_GUANGXIKJC, 1);

        }
        //标准法规库的文件目录  如果遇到生产没有放置或者机器上没有该目录后面的代码会报错（必须保证有这个目录）
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileUtils.createOrExistsDir(path + "/dayuan");
    }

    public static <T> T CheckUser(Class<T> object) {
        boolean exists = FileUtils.isFileExists(Constants.PATH_USERBEAN);
        if (exists) {
            File user = FileUtils.getFileByPath(Constants.PATH_USERBEAN);
            String filetostring = null;
            try {
                filetostring = FileUtils.filetostring(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Gson().fromJson(filetostring, object);

        }
        return null;
    }

    public static <T> T CheckRegister(Class<T> object) {
        boolean exists = FileUtils.isFileExists(Constants.PATH_REGISTERBEAN);
        if (exists) {
            File user = FileUtils.getFileByPath(Constants.PATH_REGISTERBEAN);
            String filetostring = null;
            try {
                filetostring = FileUtils.filetostring(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Gson().fromJson(filetostring, object);

        }
        return null;
    }

    public static byte MYYGHCMD_HEAD = 0X7E;
    public static byte MYYGHCMD_END = (byte) 0XAA;
    public static int READIDCARDSTARTADRESS = 20;

    public static boolean showTask() {
        return Constants.PLATFORM_TAG == 0 || Constants.PLATFORM_TAG == 41 || Constants.PLATFORM_TAG == 43 || Constants.PLATFORM_TAG == 44;
    }

    public enum MYYGCMD {
        /**
         * 开始检测请求,响应
         */
        CMD_STARTTEST(0x50),
        CMD_STARTTEST_R(0x51),
        /**
         * 检测数据请求,响应
         */
        CMD_GETTESTDATA(0x52),
        CMD_GETTESTDATA_R(0x53),
        /**
         * 设置模块编号请求,响应
         */
        CMD_SETMOUDLENUNMBER(0x19),
        CMD_SETMOUDLENUNMBER_R(0x1A),
        /**
         * 读取模块编号请求,响应
         */
        CMD_READMOUDLENUNMBER(0x1B),
        CMD_READMOUDLENUNMBER_R(0x1C),
        /**
         * 设置发射灯DAC强度请求,响应
         */
        CMD_SETDAC(0x30),
        CMD_SETDAC_R(0x31),

        /**
         * 获取发射灯DAC强度请求,响应
         */
        CMD_GETDAC(0x32),
        CMD_GETDAC_R(0x33),
        /**
         * 写ID卡请求,响应
         */
        CMD_WRITEID(0x64),
        CMD_WRITEID_R(0x65),
        /**
         * 读ID卡请求,响应
         */
        CMD_READID(0x66),
        CMD_READID_R(0x67),
        /**
         * ID卡状态请求,响应
         */
        CMD_GETIDCARDSTATE(0x68),
        CMD_GETIDCARDSTATE_R(0x69);


        private byte mByte;

        MYYGCMD(int i) {
            this.mByte = (byte) i;
        }


    }

    public static byte[] getOrderBytes_MYYG(MYYGCMD myygcmd, Object... parameter) {
        byte[] bytes = ByteUtils.byteMerger(new byte[]{MYYGHCMD_HEAD}, new byte[]{myygcmd.mByte});

        byte[] data = new byte[]{};
        if (null != parameter) {
            for (int i = 0; i < parameter.length; i++) {
                Object o = parameter[i];
                if (o instanceof Integer) {
                    int pa = (int) o;
                    data = ByteUtils.byteMerger(data, new byte[]{(byte) pa});
                } else if (o instanceof byte[]) {
                    data = ByteUtils.byteMerger(data, (byte[]) o);
                }
            }
        }


        byte[] len = ByteUtils.intToBytes2_LH(data.length, 2);
        bytes = ByteUtils.byteMerger(bytes, len);
        bytes = ByteUtils.byteMerger(bytes, data);
        int crc = FindCRC(ByteUtils.subBytes(bytes, 1, bytes.length - 1));
        bytes = ByteUtils.byteMerger(bytes, new byte[]{(byte) crc});
        bytes = ByteUtils.byteMerger(bytes, new byte[]{(byte) MYYGHCMD_END});
        return bytes;
    }
}
