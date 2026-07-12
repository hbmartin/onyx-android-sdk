package com.onyx.android.sdk.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String md5(String string) {
        try {
            return computeMD5(string.getBytes(StringUtils.UTF8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh,UTF-8 should be supported?", e);
        }
    }

    public static String computeMD5(byte[] input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input, 0, input.length);
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArrDigest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    stringBuffer.append('0');
                }
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
