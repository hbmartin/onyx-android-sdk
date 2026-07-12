// 
// 

package com.onyx.android.sdk.utils;

import java.security.Key;
import javax.crypto.Cipher;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptUtils
{
    public static final String AES = "AES";
    public static final String DEFAULT_CODING = "utf-8";
    public static final String ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";
    public static final int AES_SECRET_KEY_LEN = 128;
    public static final String key = "8chLv9pat4WlE5IzyUwF9lOEtRYaPo9d1v0EYQnIak8iJDHLdIkJQW994iQZzLIoMhErq190IU8NEYACVnkrxtr6uOXTqkWuiClOC5ACbIWB0zUk5hALBaGWEvXDhudK";
    
    private static SecretKeySpec a(final String key) throws Exception {
        return new SecretKeySpec(MessageDigest.getInstance("MD5").digest(key.getBytes("utf-8")), "AES");
    }
    
    public static String ensureAesSecretKeyLen(final String secretKey) {
        int i = secretKey.length();
        String str = secretKey;
        while (i != 128) {
            String s;
            if (i > 128) {
                s = str.substring(0, 128);
            }
            else {
                s = str + secretKey;
            }
            final int length = s.length();
            str = s;
            i = length;
        }
        return str;
    }
    
    public static String decrypt(final String key, final String encrypted) {
        try {
            final Cipher instance = Cipher.getInstance("AES");
            instance.init(2, a(key));
            final byte[] doFinal = instance.doFinal(toByte(encrypted));
            try {
                return new String(doFinal);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        catch (final Exception ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
    
    public static String encrypt(final String key, final String content) {
        try {
            final Cipher instance;
            final Cipher cipher = instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
            instance.init(1, a(key));
            final byte[] bytes = content.getBytes("utf-8");
            final byte[] buf = new byte[instance.getOutputSize(bytes.length)];
            cipher.doFinal(buf, cipher.update(bytes, 0, bytes.length, buf, 0));
            try {
                return parseByte2HexStr(buf);
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        catch (final Exception ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
    
    public static byte[] toByte(final String hexString) {
        final int n;
        final byte[] array = new byte[n = hexString.length() / 2];
        for (int i = 0; i < n; ++i) {
            final byte[] array2 = array;
            final int n2 = i;
            final int beginIndex = i * 2;
            array2[n2] = Integer.valueOf(hexString.substring(beginIndex, beginIndex + 2), 16).byteValue();
        }
        return array;
    }
    
    public static String parseByte2HexStr(final byte[] buf) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; ++i) {
            String s;
            if ((s = Integer.toHexString(buf[i] & 0xFF)).length() == 1) {
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
