package com.dy.huibiao_f80.app.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 编码加密工具类
 *
 * LogUtils.d(CodecUtils.aesEncryp("DY-350020190402001", "DYXX@)!(QPMZA2-5", true));
 */
public class CodecUtils {
    public static final String KEY_AES  ="codecKey=DYXX@)!(QPMZA2-5";

    /** Base64 编码 */
    private static final Base64 B64 = new Base64();
    /** 安全的随机数源 */
    private static final SecureRandom RANDOM = new SecureRandom();
    /** AES加密算法 */
    private static final String AES_ALGORITHM = "AES";
    /**
     * AES加密
     * 
     * @param str
     *            需要加密的明文
     * @param key
     *            密钥
     * @param urlSafety
     *            密文是否需要Url安全
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String aesEncryp(String str, String key, boolean urlSafety) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.ENCRYPT_MODE, aesKey(key), RANDOM);
                byte[] bytes = c.doFinal(str.getBytes());// 加密
                if (urlSafety) {
                    String result=new String(B64.encode(bytes));
                    result = result.replace('+', '-');
                    result = result.replace('/', '_');
                    result = result.replaceAll("=", "");
                    return result;
                } else {
                    return new String(B64.encode(bytes));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * AES解密
     * 
     * @param str
     *            需要解密的密文(base64编码字符串)
     * @param key
     *            密钥
     * @return 解密后的明文
     */
    public static String aesDecrypt(String str, String key) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, aesKey(key), RANDOM);
                return new String(c.doFinal((byte[]) B64.decode(str)));// 解密
            }catch (Exception e) {
            }
        }
        return null;
    }

    /** AES密钥 */
    private static SecretKeySpec aesKey(String key) {
        byte[] bs = key.getBytes();
        if (bs.length != 16) {
            bs = Arrays.copyOf(bs, 16);// 处理数组长度为16
        }
        return new SecretKeySpec(bs, AES_ALGORITHM);
    }

}