package com.base.app.utils.main;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * describe: 3des 加密
 * creator: keding.zhou
 * date: 2018/4/8 16:23
 */
public class Encrypt3DesUtils {
    public static String appKey = "xUHehxz123sgVIwTnc1jtpWn";

    /**
     * 加密 3des
     */
    public static String encrypt(String dataStr) {
        try {
            byte[] key = getKeyByte(appKey);
            byte[] data = (dataStr + "").getBytes();
            SecretKey deskey;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] bOut = cipher.doFinal(data);
            return Base64.encodeToString(bOut, Base64.DEFAULT);
        } catch (Exception e) {
            Log.i("zkd", "[Encrypt3DesUtils][encrypt]==> Error : " + e.toString());
            return "";
        }
    }

    /**
     * 解密 3des
     */
    public static String decrypt(String dataString) {
        try {
            byte[] key = getKeyByte(appKey);
            byte[] data = Base64.decode(dataString, Base64.DEFAULT);
            SecretKey deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            byte[] bOut = cipher.doFinal(data);
            return new String(bOut, "UTF-8");
        } catch (Exception e) {
            Log.i("zkd", "[Encrypt3DesUtils][decrypt]==> Error : " + e.toString());
            return "";
        }
    }

    private static byte[] getKeyByte(String key) {
        if (key == null) {
            return new byte[0];
        }
        int lenght = key.length();
        if (lenght >= 24) {
            return key.substring(0, 24).getBytes();
        } else {
            StringBuilder keyBuilder = new StringBuilder(key);
            for (int i = 0; i < (24 - lenght); i++) {
                keyBuilder.append("0");
            }
            key = keyBuilder.toString();
            return key.getBytes();
        }
    }

    //md5加密
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
            //return  Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
