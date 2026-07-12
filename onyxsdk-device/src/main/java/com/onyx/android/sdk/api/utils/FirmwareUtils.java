package com.onyx.android.sdk.api.utils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/utils/FirmwareUtils.class */
public class FirmwareUtils {
    private static String getBuildEntryFromFingerprint(String fingerprint) {
        String[] strArrSplit = fingerprint.split("/");
        if (strArrSplit.length < 2) {
            return null;
        }
        String str = strArrSplit[strArrSplit.length - 2];
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        return str;
    }

    public static int getBuildIdFromFingerprint(String fingerprint) {
        String buildEntryFromFingerprint = getBuildEntryFromFingerprint(fingerprint);
        if (StringUtils.isNullOrEmpty(buildEntryFromFingerprint)) {
            return -1;
        }
        String[] strArrSplit = buildEntryFromFingerprint.split(":");
        if (strArrSplit.length < 2) {
            return -1;
        }
        return Integer.valueOf(strArrSplit[0]).intValue();
    }

    public static String getBuildTypeFromFingerprint(String fingerprint) {
        String buildEntryFromFingerprint = getBuildEntryFromFingerprint(fingerprint);
        if (StringUtils.isNullOrEmpty(buildEntryFromFingerprint)) {
            return null;
        }
        String[] strArrSplit = buildEntryFromFingerprint.split(":");
        if (strArrSplit.length < 2) {
            return null;
        }
        return strArrSplit[1];
    }

    public static String getBuildDateFromBuildDisplayId(String buildDisplayId) {
        String[] strArrSplit = buildDisplayId.split(" ");
        if (strArrSplit.length < 3) {
            return null;
        }
        String str = strArrSplit[strArrSplit.length - 2];
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String getBuildVersionFromBuildFingerprint(String fingerprint) {
        String[] strArrSplit = fingerprint.split("/");
        if (strArrSplit.length < 3) {
            return null;
        }
        String str = strArrSplit[(strArrSplit.length - 1) - 2];
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        return str;
    }

    public static String getSimpleVersionFromBuildFingerprint(String fingerprint) {
        return getSimpleVersionFromBuildId(getBuildVersionFromBuildFingerprint(fingerprint));
    }

    public static String getSimpleVersionFromBuildId(String buildId) {
        if (StringUtils.isNullOrEmpty(buildId)) {
            return "";
        }
        String[] strArrSplit = buildId.split("_");
        if (strArrSplit.length < 3) {
            return "";
        }
        String str = strArrSplit[2];
        return StringUtils.isNullOrEmpty(str) ? "" : str;
    }
}
